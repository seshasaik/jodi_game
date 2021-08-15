package animo.realcom.mahakubera.serviceImpl.jodiGame;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import animo.realcom.mahakubera.entity.Company;
import animo.realcom.mahakubera.entity.JournalEntryStatus;
import animo.realcom.mahakubera.entity.Region;
import animo.realcom.mahakubera.entity.Roles;
import animo.realcom.mahakubera.entity.User;
import animo.realcom.mahakubera.entity.GameJodi.GameJodiJournalEntry;
import animo.realcom.mahakubera.entity.GameJodi.GameJodiPlayStatus;
import animo.realcom.mahakubera.entity.GameJodi.GameJodiPlayStatusId;
import animo.realcom.mahakubera.entity.GameJodi.GameJodiTicket;
import animo.realcom.mahakubera.entity.GameJodi.GameJodiTicketCompany;
import animo.realcom.mahakubera.entity.GameJodi.GameJodiTicketCompanyId;
import animo.realcom.mahakubera.entity.GameJodi.GameJodiTicketTransactionDetails;
import animo.realcom.mahakubera.entity.GameJodi.GameJodiTicketTransactions;
import animo.realcom.mahakubera.entity.GameJodi.GameJodiTicketTransactionsDetailId;
import animo.realcom.mahakubera.entity.GameJodi.GameJodiTimings;
import animo.realcom.mahakubera.entity.GameJodi.RegionCompany;
import animo.realcom.mahakubera.entity.GameJodi.RegionCompanyId;
import animo.realcom.mahakubera.exception.gameJodi.GameJodiTicketNotFoundException;
import animo.realcom.mahakubera.exception.gameJodi.InvalidGameJodiTicketTransaction;
import animo.realcom.mahakubera.modal.CustomUserDetails;
import animo.realcom.mahakubera.modal.PageDto;
import animo.realcom.mahakubera.modal.PageDto.PageDtoBuilder;
import animo.realcom.mahakubera.modal.UserDTO;
import animo.realcom.mahakubera.modal.UserDTO.UserDTOBuilder;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiGlobalTimingDTO;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiJournalEntryDTO;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiJournalEntryDTO.GameJodiJournalEntryDTOBuilder;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiGlobalTimingDTO.GameJodiGlobalTimingDTOBuilder;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTicketCompanyDTO;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTicketCompanyDTO.GameJodiTicketCompanyDTOBuilder;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTicketDTO;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTicketDTO.GameJodiTicketDTOBuilder;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTicketTransactionDetailDTO;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTicketTransactionDetailDTO.GameJodiTicketTransactionDetailDTOBuilder;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTicketTransactionRequestDTO;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTticketTransactionsDTO;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTticketTransactionsDTO.GameJodiTticketTransactionsDTOBuilder;
import animo.realcom.mahakubera.modal.gameJodi.RegionCompanyDTO;
import animo.realcom.mahakubera.modal.gameJodi.RegionCompanyDTO.RegionCompanyDTOBuilder;
import animo.realcom.mahakubera.repository.CompanyRepository;
import animo.realcom.mahakubera.repository.JournalEntryStatusRepository;
import animo.realcom.mahakubera.repository.gameJodi.GameJodiJournalEntryRepository;
import animo.realcom.mahakubera.repository.gameJodi.GameJodiPlayStatusRepository;
import animo.realcom.mahakubera.repository.gameJodi.GameJodiTicketCompanyRepository;
import animo.realcom.mahakubera.repository.gameJodi.GameJodiTicketRepository;
import animo.realcom.mahakubera.repository.gameJodi.GameJodiTimingsRepository;
import animo.realcom.mahakubera.repository.gameJodi.GameJodiTticketTransactionsRepository;
import animo.realcom.mahakubera.repository.gameJodi.RegionCompanyRepository;
import animo.realcom.mahakubera.service.UserService;
import animo.realcom.mahakubera.service.jodiGame.GameJodiService;
import animo.realcom.mahakubera.util.AppConstants;
import animo.realcom.mahakubera.util.ApplicationUtil;
import animo.realcom.mahakubera.util.DateFormatConstants;
import animo.realcom.mahakubera.util.ExceptionMessageConstants;
import animo.realcom.mahakubera.util.GameJodiDurationUnit;
import animo.realcom.mahakubera.util.GameJodiStatus;
import animo.realcom.mahakubera.util.GameJodiTransactionStatus;
import animo.realcom.mahakubera.util.JournalEntryStatusCode;
import animo.realcom.mahakubera.util.RoleName;

@Service
@Transactional
public class GameJodiServiceImpl implements GameJodiService {

	private static final Logger log = LoggerFactory.getLogger(GameJodiServiceImpl.class);

	@Value("${game-jodi.wining-ration}")
	Integer winPercentage;

	@Autowired
	GameJodiTicketRepository gameJodiTicketRepository;

	@Autowired
	GameJodiTicketCompanyRepository gameJodiTicketCompanyRepository;

	@Autowired
	GameJodiTimingsRepository gameJodiTimingsRepository;

	@Autowired
	GameJodiTticketTransactionsRepository gameJodiTticketTransactionsRepository;

	@Autowired
	RegionCompanyRepository regionCompanyRepository;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	GameJodiPlayStatusRepository gameJodiPlayStatusRepository;

	@Autowired
	GameJodiJournalEntryRepository gameJodiJournalEntryRepository;

	@Autowired
	JournalEntryStatusRepository journalEntryStatusRepository;

	@Autowired
	UserService userService;

	@Autowired
	ApplicationUtil applicationUtil;

	public GameJodiServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	private final DateTimeFormatter hourMinuteFmt = DateTimeFormatter
			.ofPattern(DateFormatConstants.DATE_FORMAT_HOURS_BIG + DateFormatConstants.DATE_FORMAT_MINUTES);

	@Override
	public void loadGameJodiTicketPerDay() {

		List<GameJodiTicket> gameJodiTicketList = generateGameJodiTickets();

		int batchSize = 50;

		List<Company> companyList = companyRepository.findAll();

		List<GameJodiTicketCompany> batchGameJodiTicketList = new ArrayList<>(batchSize);

		companyList.stream().forEach(company -> {

			log.debug("Company Id {0} and Name {0}", company.getId(), company.getName());
			gameJodiTicketList.stream().map(jodiTicket -> {
				GameJodiTicketCompany newJodiTicketCompany = new GameJodiTicketCompany();
				GameJodiTicketCompanyId gameJodiTicketCompanyId = new GameJodiTicketCompanyId();
				gameJodiTicketCompanyId.setCompany(company);
				gameJodiTicketCompanyId.setTicket(jodiTicket);

				newJodiTicketCompany.setTicketCompany(gameJodiTicketCompanyId);

				int r = new Random().ints(1, 1, 8).findAny().orElse(1);
				newJodiTicketCompany.setWinningNumbers(new Random().ints(r, 0, 100).distinct().mapToObj(String::valueOf)
						.collect(Collectors.joining(",")));
				return newJodiTicketCompany;

			}).forEach(jodiTicket -> {
				batchGameJodiTicketList.add(jodiTicket);
				if (batchGameJodiTicketList.size() == batchSize) {
					gameJodiTicketCompanyRepository.saveAll(batchGameJodiTicketList);
					batchGameJodiTicketList.clear();
				}
			});

			if (batchGameJodiTicketList.size() < batchSize) {
				gameJodiTicketCompanyRepository.saveAll(batchGameJodiTicketList);
				batchGameJodiTicketList.clear();
			}
			gameJodiTicketCompanyRepository.flush();

		});

	}

	private List<GameJodiTicket> generateGameJodiTickets() {
		Instant createdDate = Instant.now();
		List<GameJodiTimings> gameJodiTimings = gameJodiTimingsRepository.findAll();

		LocalDate today = applicationUtil.getCurrentLocalDate(null);
		AtomicInteger counter = new AtomicInteger(0);
		Optional<GameJodiTicket> gameJodi = gameJodiTicketRepository.findTop1ByGameDateOrderByTicketIndexDesc(today);
		if (gameJodi.isPresent()) {
			counter.set(gameJodi.get().getTicketIndex());
		}

		LocalTime dayStartTime = LocalTime.of(0, 0);

		List<GameJodiTicket> gameJodiTicketList = gameJodiTimings.stream()
				.map(gameJodiTiming -> this.calculateGameJodiTiming(gameJodiTiming, counter, dayStartTime))
				.flatMap(List::stream).map(obj -> {
					StringBuilder sb = new StringBuilder();
					sb.append(obj.getGameDate().format(DateTimeFormatter.BASIC_ISO_DATE))
							.append(obj.getStartTime().format(hourMinuteFmt))
							.append(obj.getEndTime().format(hourMinuteFmt));
					obj.setTicketNo(sb.toString());
					obj.setCreated(createdDate);
					return obj;
				}).collect(Collectors.toList());

		gameJodiTicketList = gameJodiTicketRepository.saveAll(gameJodiTicketList);
		AtomicInteger atomicInteger = new AtomicInteger();
		atomicInteger.set(1);

		List<GameJodiPlayStatus> gameJodiPlayStatusList = gameJodiTimings.stream().map(jameJodiTiming -> {
			GameJodiPlayStatus gameJodiPlayStatus = new GameJodiPlayStatus();
			GameJodiPlayStatusId gameJodiPlayStatusId = new GameJodiPlayStatusId(LocalDate.now(),
					(byte) atomicInteger.getAndIncrement());
			gameJodiPlayStatus.setGameJodiPlayStatusId(gameJodiPlayStatusId);
			gameJodiPlayStatus.setPlaySessionTime(jameJodiTiming);
			return gameJodiPlayStatus;
		}).collect(Collectors.toList());

		gameJodiPlayStatusRepository.saveAll(gameJodiPlayStatusList);
		return gameJodiTicketList;
	}

	private List<GameJodiTicket> calculateGameJodiTiming(GameJodiTimings gameJodiTimings, AtomicInteger counter,
			LocalTime dayStartTime) {
		List<GameJodiTicket> calculateGameJodiTimingList = new ArrayList<GameJodiTicket>();

		LocalTime startTime = gameJodiTimings.getStartTime();
		LocalTime endTime = gameJodiTimings.getEndTime();
		byte duration = gameJodiTimings.getDuration();
		LocalDate gameDate = applicationUtil.getCurrentLocalDate(null);
		boolean dateChanged = false;
		LocalTime todayEndTime = LocalTime.of(23, (60 / duration - 1) * duration);
		GameJodiDurationUnit durationUnit = Stream.of(GameJodiDurationUnit.values())
				.filter(e -> e.getUnit().equals(gameJodiTimings.getDurationUnit())).findAny()
				.orElseThrow(() -> new RuntimeException("There is no valid GameJodiDurationUnit"));

		while (true) {

			if (GameJodiDurationUnit.DURATION_MINUTE.equals(durationUnit)) {
				LocalTime tempEndTime = startTime.plusMinutes(duration);
				long tempDuration = ChronoUnit.MINUTES.between(startTime, tempEndTime);

				if (tempDuration == duration) {
					if (isDayStartTime(dayStartTime, startTime) && !dateChanged) {
						gameDate = gameDate.plusDays(1);
						dateChanged = true;
						counter.set(0);
					}
					this.addGameJodiTicketToList(calculateGameJodiTimingList, gameDate, startTime, tempEndTime,
							gameJodiTimings, counter.incrementAndGet());

					if (ChronoUnit.MINUTES.between(tempEndTime, endTime) <= duration) {

						if (tempEndTime.equals(todayEndTime)) {
							this.addGameJodiTicketToList(calculateGameJodiTimingList, gameDate, tempEndTime,
									dayStartTime, gameJodiTimings, counter.incrementAndGet());
							startTime = dayStartTime;
							continue;
						}
						this.addGameJodiTicketToList(calculateGameJodiTimingList, gameDate, tempEndTime, endTime,
								gameJodiTimings, counter.incrementAndGet());
						break;
					}

					startTime = tempEndTime;

					continue;
				}

			} else if (GameJodiDurationUnit.DURATION_HOUR.equals(durationUnit)) {
			}

			break;
		}

		return calculateGameJodiTimingList;
	}

	private void addGameJodiTicketToList(List<GameJodiTicket> calculateGameJodiTimingList, LocalDate gameDate,
			LocalTime startTime, LocalTime endTime, GameJodiTimings gameJodiTimings, int index) {
		GameJodiTicket gameJodiTicket = new GameJodiTicket();
		gameJodiTicket.setStartTime(startTime);
		gameJodiTicket.setEndTime(endTime);
		gameJodiTicket.setTicketIndex((short) index);
		gameJodiTicket.setGameJodiTime(gameJodiTimings);
		gameJodiTicket.setGameDate(gameDate);

		calculateGameJodiTimingList.add(gameJodiTicket);
	}

	private boolean isDayStartTime(LocalTime dayStartTime, LocalTime startTime) {
		return startTime.equals(dayStartTime);
	}

	@Override
	public List<RegionCompanyDTO> getRegionCompanyData() {
		// TODO Auto-generated method stub
		CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Region region = new Region();
		region.setId(user.getRegionId());
		List<RegionCompany> regionCompanyList = regionCompanyRepository.findAllByRegionCompanyId_Region(region);
		return regionCompanyList.stream().map(rc -> {

			Company originalCompany = rc.getRegionCompanyId().getCompany();

			RegionCompanyDTOBuilder builder = RegionCompanyDTO.builder();
			builder.id(originalCompany.getId());
			builder.originalCompanyName(originalCompany.getName()).originalCompanyCode(originalCompany.getCode());
			builder.regionCompanyName(rc.getName()).regionCompanyCode(rc.getCode());
			builder.dateOfRegister(rc.getDateOfRegister());
			builder.ticketRate(originalCompany.getTicketRate()).maxQuantity(originalCompany.getMaxQuantity());
			builder.winningPrizeAmount(originalCompany.getWinningPrizeAmount()).status(originalCompany.getStatus());

			return builder.build();
		}).collect(Collectors.toList());
	}

	@Override
	public GameJodiTicketDTO getRecentRegionCompanyDrawResult() {
		// TODO Auto-generated method stub
		LocalDate gameDate = LocalDate.now(ZoneId.of(AppConstants.TIME_ZONE_ASIA_KOLKATA));
		GameJodiTicket gameJodiTicket = gameJodiTicketRepository
				.findRecentCompletedGameJodiResult(gameDate, GameJodiStatus.COMPLETED, PageRequest.of(0, 1)).get(0);
		GameJodiTicketDTOBuilder builder = GameJodiTicketDTO.builder();

		List<GameJodiTicketCompanyDTO> ticketCompaniesDtoList = gameJodiTicket.getCompanies().stream()
				.map(ticketCompany -> {
					Company company = ticketCompany.getTicketCompany().getCompany();
					GameJodiTicketCompanyDTOBuilder gameJodiTicketCompanyDTOBuilder = GameJodiTicketCompanyDTO
							.builder();
					gameJodiTicketCompanyDTOBuilder.companyId(company.getId()).companyName(company.getName())
							.winningNumbers(ticketCompany.getWinningNumbers());

					// List<RegionCompany> regionCompany = company.getRegions();

					return gameJodiTicketCompanyDTOBuilder.build();
				}).collect(Collectors.toList());
		builder.id(gameJodiTicket.getId()).companies(ticketCompaniesDtoList);
		builder.startTime(gameJodiTicket.getStartTime()).endTime(gameJodiTicket.getEndTime());
		builder.ticketNo(gameJodiTicket.getTicketNo()).date(gameJodiTicket.getGameDate());
		builder.gameStatus(gameJodiTicket.getGameStatus());
		return builder.build();
	}

	@Override
	public void getGameByGameId(Long gameId) {
		// TODO Auto-generated method stub
		gameJodiTicketRepository.findById(gameId);
	}

	private GameJodiTicket getNextGame(LocalDate gameDate) {
		List<GameJodiTicket> gameJodiTicketList = gameJodiTicketRepository.findNextGame(gameDate,
				Arrays.asList(GameJodiStatus.YET_TO_START, GameJodiStatus.START), PageRequest.of(0, 1));

		if (gameJodiTicketList.isEmpty()) {
			throw new GameJodiTicketNotFoundException();
		}

		return gameJodiTicketList.get(0);
	}

	@Override
	public GameJodiTicketDTO getNextGame() {
		LocalDate gameDate = applicationUtil.getCurrentLocalDate(null);

		GameJodiTicket gameJodiTicket = getNextGame(gameDate);

		GameJodiTicketDTOBuilder builder = GameJodiTicketDTO.builder();

		builder.id(gameJodiTicket.getId()).ticketNo(gameJodiTicket.getTicketNo());
		builder.endTime(gameJodiTicket.getEndTime()).startTime(gameJodiTicket.getStartTime());

		return builder.build();

	}

	@Override
	public GameJodiTicketTransactionRequestDTO saveJodiTicket(GameJodiTicketTransactionRequestDTO jodiTicket) {

		CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		User currentUser = userService.getUserByUserName(user.getUsername());
		Roles vendorRole = currentUser.getUserRoles().stream()
				.filter(role -> RoleName.VENDOR.equals(role.getName()) || RoleName.SUB_VENDOR.equals(role.getName()))
				.findAny().orElse(null);
		GameJodiTicket gameJodiTicket = gameJodiTicketRepository.findById(jodiTicket.getTicketId()).orElseThrow(
				() -> new GameJodiTicketNotFoundException(ExceptionMessageConstants.GAME_JODI_TICKET_NOT_FOUND,
						String.format("Game jodi ticket %d not found", jodiTicket.getTicketId())));

		if (gameJodiTicket.getGameStatus().equals(GameJodiStatus.CANCELED)) {
			throw new InvalidGameJodiTicketTransaction(
					ExceptionMessageConstants.GAME_JODI_TICKET_TRANSACTION_INVALID_GAME_CANCELED);
		}
		if (gameJodiTicket.getGameStatus().equals(GameJodiStatus.COMPLETED)) {
			throw new InvalidGameJodiTicketTransaction(
					ExceptionMessageConstants.GAME_JODI_TICKET_TRANSACTION_INVALID_GAME_COMPLETED);
		}

		String transactionId = UUID.randomUUID().toString();
		GameJodiTicketTransactions gameJodiTicketTransaction = new GameJodiTicketTransactions();
		gameJodiTicketTransaction.setCreated(Instant.now());
		gameJodiTicketTransaction.setTransactionId(transactionId);
		gameJodiTicketTransaction.setJodiTicket(gameJodiTicket);
		gameJodiTicketTransaction.setTotalQuantity(jodiTicket.getTotalQuantity());
		gameJodiTicketTransaction.setTotalAmount(jodiTicket.getTotalAmount());

		if (Objects.nonNull(vendorRole)) {
			gameJodiTicketTransaction.setVendor(currentUser);
		} else {
			gameJodiTicketTransaction.setVendorUser(currentUser);
			gameJodiTicketTransaction.setVendor(currentUser.getParent());
		}

		List<GameJodiTicketTransactionDetails> gameJodiTicketTransactionsDetailsList = jodiTicket.getDetail().stream()
				.map(detail -> {
					GameJodiTicketTransactionDetails transactionDetails = new GameJodiTicketTransactionDetails();
					// transaction detail id using gameJodiTicketTransaction and lottery no
					Company company = new Company();
					company.setId(detail.getCompanyId());
					GameJodiTicketTransactionsDetailId transactionsDetailId = new GameJodiTicketTransactionsDetailId();
					transactionsDetailId.setTransaction(gameJodiTicketTransaction);
					transactionsDetailId.setCompany(company);
					transactionsDetailId.setLotteryNo(detail.getLotteryNo());

					transactionDetails.setDetailId(transactionsDetailId);
					transactionDetails.setQuantity(detail.getQuantity());
					transactionDetails.setTicketRate(detail.getTicketRate());
					transactionDetails.setAmount(detail.getAmount());

					return transactionDetails;
				}).collect(Collectors.toList());

		gameJodiTicketTransaction.setDetails(gameJodiTicketTransactionsDetailsList);

		GameJodiTicketTransactions savedJameJodiTicketTransaction = gameJodiTticketTransactionsRepository
				.save(gameJodiTicketTransaction);

		createGameJodiJournalEntry(savedJameJodiTicketTransaction, vendorRole, currentUser,
				JournalEntryStatusCode.JODI_PURCHASE_TICKET);

		jodiTicket.setId(savedJameJodiTicketTransaction.getId());
		jodiTicket.setTransactionCode(savedJameJodiTicketTransaction.getTransactionId());
		jodiTicket.setDateTime(LocalDateTime.ofInstant(savedJameJodiTicketTransaction.getCreated(),
				ZoneId.of(AppConstants.TIME_ZONE_ASIA_KOLKATA)));
		jodiTicket.setGameId(savedJameJodiTicketTransaction.getJodiTicket().getTicketNo());
		UserDTOBuilder userBuilder = UserDTO.builder();
		userBuilder.id(savedJameJodiTicketTransaction.getVendor().getId())
				.userName(savedJameJodiTicketTransaction.getVendor().getUserName()).contactNumber("9155887457");
		jodiTicket.setUser(userBuilder.build());

		Map<Integer, List<GameJodiTicketTransactionDetailDTO>> transDetailMap = jodiTicket.getDetail().stream()
				.collect(Collectors.groupingBy(GameJodiTicketTransactionDetailDTO::getCompanyId));

		List<Map.Entry<Integer, Map<Byte, Short>>> entySetList = transDetailMap.entrySet().stream().map(mapset -> {
			Map<Byte, Short> map = mapset.getValue().stream().collect(Collectors.toMap(
					GameJodiTicketTransactionDetailDTO::getLotteryNo, GameJodiTicketTransactionDetailDTO::getQuantity));
			Map.Entry<Integer, Map<Byte, Short>> entySet = new AbstractMap.SimpleEntry<>(mapset.getKey(), map);
			return entySet;

		}).collect(Collectors.toList());

		jodiTicket.setDetail(entySetList.stream().map(t -> {
			GameJodiTicketTransactionDetailDTOBuilder transDetail = GameJodiTicketTransactionDetailDTO.builder();
			Company company = new Company();
			company.setId(t.getKey());
			Region region = savedJameJodiTicketTransaction.getVendor().getRegion();
			RegionCompanyId regionCompanyId = new RegionCompanyId();
			regionCompanyId.setCompany(company);
			regionCompanyId.setRegion(region);

			RegionCompany regionCompany = regionCompanyRepository.findById(regionCompanyId).get();
			transDetail.companyId(company.getId()).companyName(regionCompany.getName())
					.companyCode(regionCompany.getCode());

			transDetail.detail(t.getValue());
			return transDetail.build();

		}).collect(Collectors.toList()));

		return jodiTicket;
	}

	private void createGameJodiJournalEntry(GameJodiTicketTransactions savedJameJodiTicketTransaction, Roles vendorRole,
			User currentUser, JournalEntryStatusCode journalEntryStatusCode) {

		JournalEntryStatus journalEntryStatus = journalEntryStatusRepository.findByJournalCode(journalEntryStatusCode)
				.get();

		GameJodiJournalEntry gameJodiJournalEntry = new GameJodiJournalEntry();

		gameJodiJournalEntry.setTransaction(savedJameJodiTicketTransaction);

		gameJodiJournalEntry.setJournalEntry(journalEntryStatus);

		if (Objects.nonNull(vendorRole)) {
			gameJodiJournalEntry.setVendor(currentUser);
		} else {
			gameJodiJournalEntry.setVendorUser(currentUser);
			gameJodiJournalEntry.setVendor(currentUser.getParent());
		}

		switch (journalEntryStatusCode) {
		case JODI_CLAIM_TICKET:
			gameJodiJournalEntry.setDebit(savedJameJodiTicketTransaction.getTotalAmount());
			break;
		case JODI_PURCHASE_TICKET:
			gameJodiJournalEntry.setCredit(savedJameJodiTicketTransaction.getTotalAmount());
			break;
		case JODI_CANCEL_TICKET:
			gameJodiJournalEntry.setDebit(savedJameJodiTicketTransaction.getTotalAmount());
			break;
		default:
			throw new RuntimeException("Invalid JournalEntryStatusCode");

		}

		gameJodiJournalEntryRepository.save(gameJodiJournalEntry);

	}

	@Override
	public void cancelJodiTicket(Long transactionId) {
		// TODO Auto-generated method stub

		CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		User currentUser = userService.getUserByUserName(user.getUsername());
		Roles vendorRole = currentUser.getUserRoles().stream()
				.filter(role -> RoleName.VENDOR.equals(role.getName()) || RoleName.SUB_VENDOR.equals(role.getName()))
				.findAny().orElse(null);

		GameJodiTicketTransactions gameJodiTicketTransactions = gameJodiTticketTransactionsRepository
				.findById(transactionId)
				.orElseThrow(() -> new GameJodiTicketNotFoundException(
						ExceptionMessageConstants.GAME_JODI_TICKET_TRANSACTION_NOT_FOUND,
						String.format("Game jodi ticket Transaction %d not found", transactionId)));
		gameJodiTicketTransactions.setStatus(GameJodiTransactionStatus.CANCEL);
		gameJodiTicketTransactions.setCanceled(Instant.now());

		createGameJodiJournalEntry(gameJodiTicketTransactions, vendorRole, currentUser,
				JournalEntryStatusCode.JODI_CANCEL_TICKET);
	}

	@Override
	public PageDto getGameJodiTransactions(LocalDate gameDate, PageRequest page) {
		// TODO Auto-generated method stub

		CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User currentUser = userService.getUserByUserName(user.getUsername());

		Page<GameJodiTicketTransactions> gameJodiTransactionPage;
		if (Objects.isNull(gameDate)) {
			gameJodiTransactionPage = gameJodiTticketTransactionsRepository.findGameJodiTransactionDetails(currentUser,
					page);
		} else {
			gameJodiTransactionPage = gameJodiTticketTransactionsRepository
					.findGameJodiTransactionDetailsByDate(gameDate, currentUser, page);
		}
		List<GameJodiTticketTransactionsDTO> gameJodiTticketTransactionsDTOList = gameJodiTransactionPage.getContent()
				.stream().map(transaction -> {
					GameJodiTicket ticket = transaction.getJodiTicket();
					GameJodiTticketTransactionsDTOBuilder builder = GameJodiTticketTransactionsDTO.builder();
					builder.Id(transaction.getId()).transactionCode(transaction.getTransactionId())
							.ticketNo(ticket.getTicketNo());
					builder.totalAmount(transaction.getTotalAmount()).totalQuantity(transaction.getTotalQuantity());
					builder.created(LocalDateTime.ofInstant(transaction.getCreated(),
							ZoneId.of(AppConstants.TIME_ZONE_ASIA_KOLKATA)));
					if (Objects.nonNull(transaction.getCanceled()))
						builder.canceled(LocalDateTime.ofInstant(transaction.getCanceled(),
								ZoneId.of(AppConstants.TIME_ZONE_ASIA_KOLKATA)));
					builder.startTime(ticket.getStartTime()).endTime(ticket.getEndTime())
							.gameStatus(ticket.getGameStatus());
					return builder.build();
				}).collect(Collectors.toList());

		PageDtoBuilder pageDtoBuilder = PageDto.builder();
		pageDtoBuilder.data(gameJodiTticketTransactionsDTOList)
				.page(gameJodiTransactionPage.getPageable().getPageNumber());
		pageDtoBuilder.size(gameJodiTransactionPage.getSize());
		pageDtoBuilder.total(gameJodiTransactionPage.getTotalElements());
		return pageDtoBuilder.build();

	}

	@Override
	public void prepareGameJodiTicketResult() {
		// TODO Auto-generated method stub
		LocalDate gameDate = applicationUtil.getCurrentLocalDate(null);
		GameJodiTicket gameJodiTicket = getNextGame(gameDate);

		clearPastGameJodiTicket();
		gameJodiTticketTransactionsRepository.prepareGameResult(gameJodiTicket.getId(), winPercentage);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
	public void clearPastGameJodiTicket() {
		LocalDate today = applicationUtil.getCurrentLocalDate(null);
		List<GameJodiTicket> gameJodiList = gameJodiTicketRepository.findGameByGameDateAndGameStatusNotIn(today,
				Arrays.asList(GameJodiStatus.CANCELED, GameJodiStatus.COMPLETED));
		LocalTime zeroHour = LocalTime.of(0, 0);

		LocalTime now = applicationUtil.getCurrentLocalTime(null);

		// update previous game to completed
		gameJodiList = gameJodiList.stream().filter(gameJodi -> {
			if (gameJodi.getEndTime().isBefore(now) && !gameJodi.getEndTime().equals(zeroHour)) {
				return true;
			}

			if (now.isAfter(gameJodi.getStartTime()) && now.isBefore(gameJodi.getEndTime())
					&& !gameJodi.getGameStatus().equals(GameJodiStatus.START)) {
				return true;
			}

			return false;
		}).map(gameJodi -> {
			if (gameJodi.getEndTime().isBefore(now)) {
				gameJodi.setGameStatus(GameJodiStatus.COMPLETED);
			} else if (now.isAfter(gameJodi.getStartTime()) && now.isBefore(gameJodi.getEndTime())) {
				gameJodi.setGameStatus(GameJodiStatus.START);
			}
			gameJodi.setUpdated(Instant.now());
			return gameJodi;
		}).collect(Collectors.toList());

		if (!gameJodiList.isEmpty())
			gameJodiTicketRepository.saveAll(gameJodiList);

	}

	@Override
	public boolean isTodayGameJodiPrepared() {
		// TODO Auto-generated method stub
		LocalDate today = applicationUtil.getCurrentLocalDate(null);
		Optional<GameJodiTicket> gameJodi = gameJodiTicketRepository.findTop1ByGameDateOrderByTicketIndexDesc(today);
		if (gameJodi.isPresent()) {
			return gameJodi.get().getEndTime().equals(LocalTime.of(0, 0));
		}
		return gameJodi.isPresent();
	}

	@Override
	public List<GameJodiGlobalTimingDTO> getGameGlobalJodiTimeings() {

		List<GameJodiTimings> timingList = gameJodiTimingsRepository.findAllByStatusIsTrue();
		return timingList.stream().map(obj -> {
			GameJodiGlobalTimingDTOBuilder builder = GameJodiGlobalTimingDTO.builder();
			builder.id(obj.getId()).startTime(obj.getStartTime()).endTime(obj.getEndTime()).duration(obj.getDuration());
			builder.durationUnit(obj.getDurationUnit()).description(obj.getDescription());
			builder.name(obj.getName());
			return builder.build();
		}).collect(Collectors.toList());

	}

	@Override
	public GameJodiTticketTransactionsDTO getGameJodiTransactionsDetail(Long transactionId) {
		// TODO Auto-generated method stub
		CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Region region = new Region();
		region.setId(user.getRegionId());
		GameJodiTicket gameJodiTicket = new GameJodiTicket();
		gameJodiTicket.setId(transactionId);

		GameJodiTicketTransactions savedJameJodiTicketTransaction = gameJodiTticketTransactionsRepository
				.findGameJodiTransactionDetailsByGameJodi(transactionId);

		GameJodiTticketTransactionsDTOBuilder transactionsDTO = GameJodiTticketTransactionsDTO.builder();

		transactionsDTO.Id(savedJameJodiTicketTransaction.getId())
				.ticketNo(savedJameJodiTicketTransaction.getJodiTicket().getTicketNo())
				.transactionCode(savedJameJodiTicketTransaction.getTransactionId());
		transactionsDTO.totalQuantity(savedJameJodiTicketTransaction.getTotalQuantity())
				.totalAmount(savedJameJodiTicketTransaction.getTotalAmount());
		transactionsDTO.created(LocalDateTime.ofInstant(savedJameJodiTicketTransaction.getCreated(),
				ZoneId.of(AppConstants.TIME_ZONE_ASIA_KOLKATA)));
		if (Objects.nonNull(savedJameJodiTicketTransaction.getCanceled()))
			transactionsDTO.canceled(LocalDateTime.ofInstant(savedJameJodiTicketTransaction.getCanceled(),
					ZoneId.of(AppConstants.TIME_ZONE_ASIA_KOLKATA)));

		transactionsDTO.startTime(savedJameJodiTicketTransaction.getJodiTicket().getStartTime());
		transactionsDTO.endTime(savedJameJodiTicketTransaction.getJodiTicket().getEndTime());

//		UserDTOBuilder userBuilder = UserDTO.builder();
//		userBuilder.id(savedJameJodiTicketTransaction.getVendor().getId())
//				.userName(savedJameJodiTicketTransaction.getVendor().getUserName()).contactNumber("9155887457");
//		transactionsDTO.user(userBuilder.build());

		List<RegionCompany> regionCompanies = regionCompanyRepository.findAllByRegion(region);
		
		Map<Integer, List<GameJodiTicketTransactionDetailDTO>> transDetailMap = savedJameJodiTicketTransaction
				.getDetails().stream().map(gameJodiTransactionDetail -> {
					Company originalCompany = gameJodiTransactionDetail.getDetailId().getCompany();
					RegionCompany regionCompany = regionCompanies.stream().filter(obj -> obj.getRegionCompanyId().getCompany().getId().equals(originalCompany.getId())).findFirst().get();
					GameJodiTicketTransactionDetailDTOBuilder detailDTO = GameJodiTicketTransactionDetailDTO.builder();
					detailDTO.companyId(originalCompany.getId());
					detailDTO.companyName(regionCompany.getName()).companyCode(regionCompany.getCode());
					detailDTO.lotteryNo(gameJodiTransactionDetail.getDetailId().getLotteryNo()).quantity(gameJodiTransactionDetail.getQuantity());
					return detailDTO.build();
				}).collect(Collectors.groupingBy(GameJodiTicketTransactionDetailDTO::getCompanyId));

		transactionsDTO.detail(transDetailMap.entrySet().stream().map(mapset -> {
			GameJodiTicketTransactionDetailDTO dto = mapset.getValue().get(0);
			
			int totalQuantity = mapset.getValue().stream().mapToInt(v -> v.getQuantity()).sum();
			Map<Byte, Short> map = mapset.getValue().stream().collect(Collectors.toMap(
					GameJodiTicketTransactionDetailDTO::getLotteryNo, GameJodiTicketTransactionDetailDTO::getQuantity));
			dto.setDetail(map);
			dto.setQuantity((short) totalQuantity);
			dto.setLotteryNo(null);
			return dto;

		}).collect(Collectors.toList()));
		return transactionsDTO.build();
	}

	@Override
	public PageDto getGameJodiJounalEntry(LocalDate entryDate, PageRequest page) {
		Instant startTime = entryDate.atTime(LocalTime.of(0, 0, 0)).toInstant(ZoneOffset.UTC);
		Instant endTime = entryDate.atTime(LocalTime.of(23, 59, 59)).toInstant(ZoneOffset.UTC);
		CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User currentUser = userService.getUserByUserName(user.getUsername());
		if (!currentUser.getUserRoles().stream()
				.anyMatch(role -> role.getName().getRoleName().equals(RoleName.VENDOR.getRoleName()))) {
			throw new RuntimeException("Access denied for viewing journal entry");
		}
		Page<GameJodiJournalEntry> gameJodiJournalEntrypage = gameJodiJournalEntryRepository
				.getJodiJournalEntry(startTime, endTime, currentUser, page);
		List<GameJodiJournalEntryDTO> gameJodiJournalEntryDTOList = gameJodiJournalEntrypage.get().map(journalEntry -> {
			GameJodiJournalEntryDTOBuilder entryDTOBuilder = GameJodiJournalEntryDTO.builder();
			entryDTOBuilder.createdOn(
					LocalDateTime.ofInstant(journalEntry.getCreated(), ZoneId.of(AppConstants.TIME_ZONE_ASIA_KOLKATA)));
			entryDTOBuilder.credit(journalEntry.getCredit()).debit(journalEntry.getDebit())
					.description(journalEntry.getJournalEntry().getDescription());
			entryDTOBuilder.journalEntryCode(journalEntry.getJournalEntry().getJournalCode().getCode());
			entryDTOBuilder.ticketNo(journalEntry.getTransaction().getJodiTicket().getTicketNo());
			entryDTOBuilder.transactionCode(journalEntry.getTransaction().getTransactionId());
			return entryDTOBuilder.build();
		}).collect(Collectors.toList());

		PageDtoBuilder pageDtoBuilder = PageDto.builder();
		pageDtoBuilder.data(gameJodiJournalEntryDTOList).page(gameJodiJournalEntrypage.getPageable().getPageNumber());
		pageDtoBuilder.size(gameJodiJournalEntrypage.getSize());
		pageDtoBuilder.total(gameJodiJournalEntrypage.getTotalElements());
		return pageDtoBuilder.build();
	}

}
