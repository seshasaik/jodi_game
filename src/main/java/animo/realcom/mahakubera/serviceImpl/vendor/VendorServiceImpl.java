package animo.realcom.mahakubera.serviceImpl.vendor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import animo.realcom.mahakubera.config.AbstractPropertiesReader;
import animo.realcom.mahakubera.config.AppConstantsReader;
import animo.realcom.mahakubera.entity.AdminWallet;
import animo.realcom.mahakubera.entity.JodiCompanies;
import animo.realcom.mahakubera.entity.JodiTicket;
import animo.realcom.mahakubera.entity.JodiTicket.JodiTicketBuilder;
import animo.realcom.mahakubera.entity.TransactionCodes;
import animo.realcom.mahakubera.entity.Transactions;
import animo.realcom.mahakubera.entity.VendorRegistration;
import animo.realcom.mahakubera.entity.Wallet;
import animo.realcom.mahakubera.exception.VendorNotFoundException;
import animo.realcom.mahakubera.modal.request.JodiGame;
import animo.realcom.mahakubera.repository.vendor.VendorRegistrationRepository;
import animo.realcom.mahakubera.service.JodiCompanyService;
import animo.realcom.mahakubera.service.TransactionsService;
import animo.realcom.mahakubera.service.WalletService;
import animo.realcom.mahakubera.service.admin.AdminService;
import animo.realcom.mahakubera.service.jodiGame.JodiGameService;
import animo.realcom.mahakubera.service.vendor.VendorService;
import animo.realcom.mahakubera.util.AppConstants;
import animo.realcom.mahakubera.util.ApplicationUtil;
import animo.realcom.mahakubera.util.ExceptionMessageConstants;

@Service
public class VendorServiceImpl implements VendorService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	VendorRegistrationRepository vendorRegistrationRepository;

	@Autowired
	AdminService adminService;

	@Autowired
	WalletService walletService;

	@Autowired
	TransactionsService transactionsService;

	@Autowired
	JodiCompanyService jodiCompanyService;

	@Autowired
	AbstractPropertiesReader appConstantsReader;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	JodiGameService jodiGameService;

	public VendorServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public JodiGame saveJodi(List<JodiGame> jodiGames) throws Exception {

		String cancelminutes = ApplicationUtil.instantToZoneTimeMinutes(ApplicationUtil.getUTCTime());
		int convertmin = Integer.parseInt(cancelminutes);

		String freezetime = getFreezeTime();
		String[] data = freezetime.split(",");
		VendorRegistration vendor;
		String barid = jodiGames.get(0).getBarId();
		if (ArrayUtils.contains(data, convertmin)) {
			logger.debug("Invalid operation due to the current moment is under freeze moment");
			throw new Exception("sorry! something went wrong");
		} else {

			// final String[] advanceTimeArray = (String[])
			// jodiGame.getAdvanceTimes().toArray();

			String Email_Id = ApplicationUtil.getAuthenticationDetails().getEmailId();
			vendor = vendorRegistrationRepository.findByVendorEmail(Email_Id);
			Long id = vendor.getId();
			String vendorrole = appConstantsReader.getValue(AppConstants.ROLE_VENDOR);

			String currenthours = ApplicationUtil
					.instantToZoneTimeAndDate24hoursonlyformat(ApplicationUtil.getUTCTime());
			String duration = adminService.fetchingDuration(currenthours);
			final String jodiGameId = ApplicationUtil.getgameid(duration);
			String starttime = ApplicationUtil.getstarttime(duration);
			String drawtime = ApplicationUtil.getdrawtime(duration);
			Instant instant = ApplicationUtil.getUTCTime();
			String datetime = ApplicationUtil.utcToAsiaTimeZone(instant);

			// Prepare company wise Map of data
//			ObjectMapper mapper = new ObjectMapper();
//			TypeReference<List<JodiGame>> mapType = new TypeReference<List<JodiGame>>() {
//			};
//			List<JodiGame> jsonToJodiGameList = mapper.readValue(gameData, mapType);

			// Map<String, JodiGame> map = new HashMap<String, JodiGame>();

//			String mainBarId = barid;
			// Defines the Threads
//			ExecutorService executor = Executors.newFixedThreadPool(6);
//			List<Future<Double>> futureArrayList = new ArrayList<Future<Double>>();
//			List<Double> jodiAmountList = new ArrayList<Double>();

			List<String> jodiCompanyIds = jodiGames.stream().map(jodiGame -> jodiGame.getCompany())
					.collect(Collectors.toList());
			List<JodiCompanies> jodiCompanies = jodiCompanyService.getJodiCompanyByCompanyCode(jodiCompanyIds);

			jodiGames = jodiGames.stream().map(jodiGame -> {

				if (jodiGame.getAdvanceTimes().isEmpty()) {
					updateJodiGameDto(jodiGame, id, vendorrole, jodiGameId, datetime, starttime, drawtime);
					List<JodiGame> jodigameSingleList = new ArrayList<>();
					jodigameSingleList.add(jodiGame);
					return jodigameSingleList;
				} else {
					return jodiGame.getAdvanceTimes().stream().map(advanceDrawTime -> {
						String advanceDuration = adminService.fetchingDuration(advanceDrawTime.split(":")[0]);
						String advanceStartTime = ApplicationUtil.getstarttime(advanceDuration);
						updateJodiGameDto(jodiGame, id, vendorrole, jodiGameId, datetime, advanceStartTime,
								advanceDrawTime);
						return jodiGame;
					}).collect(Collectors.toList());
				}
			}).flatMap(List::stream).map(jodiGame -> {
				JodiCompanies jodiCompany = jodiCompanies.stream()
						.filter(p -> p.getCompanyCode().equalsIgnoreCase(jodiGame.getCompany())
								|| p.getCompanyCode().concat(p.getCloseCompanyCode()).equals(jodiGame.getCompany()))
						.findAny().get();
				jodiGame.setTotalQuantity(Integer
						.toString(jodiGame.getTicketDetails().entrySet().stream().mapToInt(e -> e.getValue()).sum()));
				// total for the company
				double total = ApplicationUtil.stringToDouble(jodiGame.getTotalQuantity())
						* ApplicationUtil.stringToDouble(jodiCompany.getCompanyTicketPrice());
				total = Math.round(total * 100.0) / 100.0;

				jodiGame.setTicketRate(jodiCompany.getCompanyTicketPrice());
				jodiGame.setWinningAmount(jodiCompany.getWinningPrice());
				jodiGame.setCompany(jodiGame.getCompany());
				jodiGame.setTotalAmount(ApplicationUtil.doubleToString(total));
				return jodiGame;
			}).collect(Collectors.toList());

			List<JodiTicket> jodiTickets = jodiGames.stream().map(jodiGameDto -> {
				JodiTicketBuilder jodiTicketBuider = JodiTicket.builder();
				jodiTicketBuider.regId(String.valueOf(jodiGameDto.getRegId()));
				jodiTicketBuider.company(jodiGameDto.getCompany());
				jodiTicketBuider.gameId(jodiGameDto.getGameId());

				jodiTicketBuider.issueTime(jodiGameDto.getStartTime()).ticketId(jodiGameDto.getBarId());
				try {
					jodiTicketBuider.puttingNumber(objectMapper.writeValueAsString(jodiGameDto.getTicketDetails()));
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				jodiTicketBuider.dateTime(LocalDateTime.now());
				jodiTicketBuider.amount(jodiGameDto.getTotalAmount()).drawTime(jodiGameDto.getDrawTime())
						.roles(jodiGameDto.getRole());
				jodiTicketBuider.ticketStatus("1").winningAmount(jodiGameDto.getWinningAmount())
						.quantity(jodiGameDto.getTotalQuantity());
				jodiTicketBuider.jokerQuantity(jodiGameDto.getJokerQuantity())
						.jokerHitCount(jodiGameDto.getJokerHitCount());

				return jodiTicketBuider.build();
			}).collect(Collectors.toList());
			jodiTickets = jodiGameService.saveJodiTicket(jodiTickets);
			logger.info(jodiTickets.toString());

			/*
			 * if (!jodiGame.getAdvanceTimes().isEmpty()) { for (String advanceDrawTime :
			 * jodiGame.getAdvanceTimes()) { String advanceDuration =
			 * adminService.fetchingDuration(advanceDrawTime.split(":")[0]); String
			 * advanceStartTime = ApplicationUtil.getstarttime(advanceDuration); for
			 * (JodiGame jodiGame : jsonToJodiGameList) {
			 * 
			 * SaveTicketCallable callable = new SaveTicketCallable(jodiGame,
			 * vendorServices, userServices, barid, jokerQuantity, jokerHitCount); try {
			 * double jodiAmount = callable.call(); jodiAmountList.add(jodiAmount); } catch
			 * (Exception e) { e.printStackTrace(); }
			 * 
			 * } } } else { for (JodiGame jodiGame : jsonToJodiGameList) {
			 * jodiGame.setRegId(id); jodiGame.setRole(vendorrole);
			 * jodiGame.setGameId(jodiGameId); jodiGame.setDateTime(datetime);
			 * jodiGame.setStartTime(starttime); jodiGame.setDrawTime(drawtime);
			 * Future<Double> future = executor.submit(new SaveTicketCallable(jodiGame,
			 * vendorServices, userServices, barid, jokerQuantity, jokerHitCount));
			 * futureArrayList.add(future); } }
			 */

//			jodiGames.stream().mapToDouble(mapper)

//			if (!advanceTimes.isEmpty()) {
//				for (Double amount : jodiAmountList) {
//					joditotalAmount = joditotalAmount + amount;
//				}
//			} else {
//				for (Future<Double> future : futureArrayList) {
//					joditotalAmount = joditotalAmount + future.get();
//				}
//			}
//
//			executor.shutdown();

			/*
			 * if (!advanceTimes.isEmpty()) { joditotalAmount = joditotalAmount *
			 * advanceTimeArray.length; }
			 */
			double joditotalAmount = jodiGames.stream()
					.mapToDouble(j -> ApplicationUtil.stringToDouble(j.getTotalAmount())).sum();
			// save transaction tables and admin wallet
			double totalAmount = Math.round(joditotalAmount * 100D) / 100D;
			System.out.println(totalAmount);

//			List<Wallet> m = userServices.fetchingWalletMoney(id, vendorrole);
//
//			String amount = "0";
//			for (Wallet aa : m) {
//				amount = aa.getAmount();
//			}
//			
			Wallet wallet = walletService.getWallet(id.toString(), vendorrole);
			String amount = wallet.getAmount();

			double vendorProfit = (totalAmount * 5) / 100;
			vendorProfit = Math.round(vendorProfit * 100.0) / 100.0;

			String transtotalamount = Double.toString(totalAmount);
			double gameamount = Double.parseDouble(transtotalamount);
			gameamount = Math.round(gameamount * 100.0) / 100.0;
			double useramount = Double.parseDouble(amount);
			useramount = Math.round(useramount * 100.0) / 100.0;

			// check subvendor or normal vendor
			VendorRegistration vendorRegistration = getVendorRegistration(id);
			Long vendorid = Objects.nonNull(vendorRegistration.getSubVendorStatus())
					&& vendorRegistration.getSubVendorStatus().equals("1")
							? Long.parseLong(vendorRegistration.getApprovedVendor())
							: 0L;
			if (vendorid != 0L) {
				System.out.println("this is subvendor");

				double updateamount = (useramount - gameamount);
				updateamount = Math.round(updateamount * 100.0) / 100.0;

				String remainingamount = Double.toString(updateamount);

				wallet.setAmount(remainingamount);

				if (updateamount >= 0) {

					walletService.updateWalletAmount(wallet);

					AdminWallet adminWallet = walletService.getAdminWallet();

//					List<AdminWallet> l = adminServices.fetchingAdminMoneyFromWallet();
//					int adminid = 0;
//					String adminamount = null;
//					for (AdminWallet admin : l) {
//						adminid = admin.getRegId();
//						adminamount = admin.getAmount();
//					}

					double adminmoney = Double.parseDouble(adminWallet.getAmount());
					double totaladminmoney = adminmoney + gameamount;
					adminWallet.setAmount(Double.toString(totaladminmoney));

					walletService.updateAdminWalletAmount(adminWallet);

					String std = ApplicationUtil.instantToZoneTimeDateSaving(ApplicationUtil.getUTCTime());
					Transactions tw = new Transactions();
					tw.setRegId(id.toString());
					tw.setRole(appConstantsReader.getValue(AppConstants.ROLE_VENDOR));
					tw.setCredit("0");
					tw.setDebit(transtotalamount);
					double walletamount = Double.parseDouble(amount);
					double spendamount = Double.parseDouble(transtotalamount);
					double totalamount = walletamount - spendamount;
					String contotalamount = Double.toString(totalamount);
					tw.setTotal(contotalamount);

					String jorole = appConstantsReader.getValue(AppConstants.ROLE_JOP);
					TransactionCodes transactionCodes = transactionsService.getTransactionCodesByStatusCode(jorole);

					tw.setPurpose(transactionCodes.getPurpose());
					tw.setDateTime(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
					tw.setTransactionCodes(transactionCodes.getCode());

					tw.setUniqueId(ApplicationUtil.utcToAsiaTimeZone(ApplicationUtil.getUTCTime()) + id + contotalamount
							+ "DEBT");
					int transaction_id = transactionsService.saveTransaction(tw);
					System.out.println("check transaction" + transaction_id);

					if (transaction_id != 0) {

						// int
						// transaction_id=userServices.gettransactionIdforBarCode(tw.getRegId(),tw.getRole(),tw.getDebit(),tw.getTotal(),tw.getTransactionId(),tw.getPurpose());
						String transid = ApplicationUtil.IntToLettersIncrement(transaction_id);

						barid = barid.concat(transid);
						System.out.println(barid + " " + "This is barcode for common ");
					}

					// storing vendor transection wise table
					tw.setCredit(Double.toString(vendorProfit));
					tw.setDebit("0");
					tw.setTotal(Double.toString(updateamount + vendorProfit));
					tw.setPurpose("Vendor Profit Added By Subvendor(" + vendorid + ")");
					tw.setUniqueId(ApplicationUtil.utcToAsiaTimeZone(ApplicationUtil.getUTCTime()) + id + contotalamount
							+ "CRDT");
					String jorole1 = appConstantsReader.getValue(AppConstants.ROLE_RMBA);
					tw.setTransactionCodes("VPA");
					transactionsService.saveTransaction(tw);

				}

			} else {

				double updateamount = (useramount - gameamount);
				updateamount = Math.round(updateamount * 100.0) / 100.0;

				String remainingamount = Double.toString(updateamount);
				wallet.setAmount(remainingamount);
				wallet.setRole(appConstantsReader.getValue(AppConstants.ROLE_VENDOR));

				if (updateamount >= 0) {

					walletService.updateWalletAmount(wallet);

					String std = ApplicationUtil.instantToZoneTimeDateSaving(ApplicationUtil.getUTCTime());
					Transactions tw = new Transactions();
					tw.setRegId(id.toString());
					tw.setRole(appConstantsReader.getValue(AppConstants.ROLE_VENDOR));
					tw.setCredit("0");
					tw.setDebit(transtotalamount);
					double walletamount = Double.parseDouble(amount);
					double spendamount = Double.parseDouble(transtotalamount);
					double totalamount = walletamount - spendamount;
					String contotalamount = Double.toString(totalamount);
					tw.setTotal(contotalamount);

					String jorole = appConstantsReader.getValue(AppConstants.ROLE_JOP);
					TransactionCodes transactionCodes = transactionsService.getTransactionCodesByStatusCode(jorole);

					tw.setPurpose(transactionCodes.getPurpose());
					tw.setDateTime(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
					tw.setTransactionCodes(transactionCodes.getCode());

					tw.setUniqueId(ApplicationUtil.utcToAsiaTimeZone(ApplicationUtil.getUTCTime()) + id + contotalamount
							+ "DEBT");
					int transaction_id = transactionsService.saveTransaction(tw);
					System.out.println("check transaction" + transaction_id);

					if (transaction_id != 0) {

						// int
						// transaction_id=userServices.gettransactionIdforBarCode(tw.getRegId(),tw.getRole(),tw.getDebit(),tw.getTotal(),tw.getTransactionId(),tw.getPurpose());
						String transid = ApplicationUtil.IntToLettersIncrement(transaction_id);

						barid = barid.concat(transid);
						System.out.println(barid + " " + "This is barcode for common ");
					}

					// storing vendor transection wise table
					tw.setCredit(Double.toString(vendorProfit));
					tw.setDebit("0");
					tw.setTotal(Double.toString(updateamount + vendorProfit));
					tw.setPurpose("Vendor Profit Added By Subvendor(" + vendorid + ")");
					String jorole1 = appConstantsReader.getValue(AppConstants.ROLE_RMBA);
					tw.setUniqueId(ApplicationUtil.utcToAsiaTimeZone(ApplicationUtil.getUTCTime()) + id + contotalamount
							+ "CRDT");
					tw.setTransactionCodes("VPA");
					transactionsService.saveTransaction(tw);
				}
			}

//			final String finalBarid = mainBarId;
//			if (!advanceTimes.isEmpty()) {
//				new Thread() {
//					@Override
//					public void run() {
//						userServices.saveAdvanceDrawTime(jodiGameId, finalBarid, advanceTimeArray);
//					}
//				}.start();
//			}

//			Object[] rs = new Object[] { barid, jodiGameId, drawtime, datetime };
			JodiGame jodiGame = new JodiGame();
			jodiGame.setBarId(barid);
			jodiGame.setGameId(jodiGameId);
			jodiGame.setDrawTime(drawtime);
			jodiGame.setDateTime(datetime);

			return jodiGame;
		}

	}

	@Override
	public String getFreezeTime() {
		// TODO Auto-generated method stub
		String hours = ApplicationUtil.instantToZoneTimeAndDate24hoursonlyformat(ApplicationUtil.getUTCTime());

		int hour = ApplicationUtil.stringInToInt(hours);

		String freeze = "";
		if (hour >= 9 && hour < 15) {
			freeze = "14,29,44,59";
		} else if (hour >= 15 && hour < 23) {
			freeze = "19,39,59";
		} else if (hour >= 23 || hour < 9) {
			freeze = "29,59";
		}

		return freeze;
	}

	private void updateJodiGameDto(JodiGame jodiGame, Long id, String vendorrole, String jodiGameId, String datetime,
			String starttime, String drawtime) {
		jodiGame.setRegId(id);
		jodiGame.setRole(vendorrole);
		jodiGame.setGameId(jodiGameId);
		jodiGame.setDateTime(datetime);
		jodiGame.setStartTime(starttime);
		jodiGame.setDrawTime(drawtime);
	}

	@Override
	public VendorRegistration getProfileDataByEmailId(String emailId) throws VendorNotFoundException {
		// TODO Auto-generated method stub

		VendorRegistration vendor = vendorRegistrationRepository.findByVendorEmail(emailId);
		if (Objects.isNull(vendor)) {
			String errorMsg = String.format(appConstantsReader.getValue(ExceptionMessageConstants.VENDOR_NOT_FOUND),
					"email", emailId);
			throw new VendorNotFoundException(ExceptionMessageConstants.VENDOR_NOT_FOUND, errorMsg);
		}
		return vendor;
	}

	@Override
	public VendorRegistration getVendorRegistration(Long id) {
		// TODO Auto-generated method stub
		return vendorRegistrationRepository.getOne(id);
	}
}
