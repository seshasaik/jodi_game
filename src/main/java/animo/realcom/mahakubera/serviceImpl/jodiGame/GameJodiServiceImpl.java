package animo.realcom.mahakubera.serviceImpl.jodiGame;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import animo.realcom.mahakubera.entity.Company;
import animo.realcom.mahakubera.entity.Region;
import animo.realcom.mahakubera.entity.GameJodi.GameJodiPlayStatus;
import animo.realcom.mahakubera.entity.GameJodi.GameJodiPlayStatusId;
import animo.realcom.mahakubera.entity.GameJodi.GameJodiTicket;
import animo.realcom.mahakubera.entity.GameJodi.GameJodiTicketCompany;
import animo.realcom.mahakubera.entity.GameJodi.GameJodiTimings;
import animo.realcom.mahakubera.entity.GameJodi.GameJodiTticketTransactions;
import animo.realcom.mahakubera.entity.GameJodi.RegionCompany;
import animo.realcom.mahakubera.exception.gameJodi.GameJodiPlayStatusException;
import animo.realcom.mahakubera.exception.gameJodi.GameJodiTicketNotFoundException;
import animo.realcom.mahakubera.modal.CustomUserDetails;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTicketCompanyDTO;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTicketCompanyDTO.GameJodiTicketCompanyDTOBuilder;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTicketDTO;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTicketDTO.GameJodiTicketDTOBuilder;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTticketTransactionsDTO;
import animo.realcom.mahakubera.modal.gameJodi.RegionCompanyDTO;
import animo.realcom.mahakubera.modal.gameJodi.RegionCompanyDTO.RegionCompanyDTOBuilder;
import animo.realcom.mahakubera.repository.CompanyRepository;
import animo.realcom.mahakubera.repository.gameJodi.GameJodiPlayStatusRepository;
import animo.realcom.mahakubera.repository.gameJodi.GameJodiTicketCompanyRepository;
import animo.realcom.mahakubera.repository.gameJodi.GameJodiTicketRepository;
import animo.realcom.mahakubera.repository.gameJodi.GameJodiTimingsRepository;
import animo.realcom.mahakubera.repository.gameJodi.GameJodiTticketTransactionsRepository;
import animo.realcom.mahakubera.repository.gameJodi.RegionCompanyRepository;
import animo.realcom.mahakubera.service.jodiGame.GameJodiService;
import animo.realcom.mahakubera.util.DateFormatConstants;
import animo.realcom.mahakubera.util.GameJodiDurationUnit;
import animo.realcom.mahakubera.util.GameJodiStatus;

@Service
@Transactional
public class GameJodiServiceImpl implements GameJodiService {

	private static final Logger log = LoggerFactory.getLogger(GameJodiServiceImpl.class);

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
	ObjectMapper objectMapper;

	public GameJodiServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void loadGameJodiTicketPerDay() {

		String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
		Instant createdDate = Instant.now();
		DateTimeFormatter hourMinuteFmt = DateTimeFormatter
				.ofPattern(DateFormatConstants.DATE_FORMAT_HOURS_BIG + DateFormatConstants.DATE_FORMAT_MINUTES);

		List<GameJodiTicket> gameJodiTicketList = generateGameJodiTickets(today, hourMinuteFmt, createdDate);

		int batchSize = 50;

		List<GameJodiTicketCompany> batchGameJodiTicketList = new ArrayList<>(batchSize);

		companyRepository.findAll().stream().forEach(company -> {

			log.debug("Company Id {0} and Name {0}", company.getId(), company.getName());
			gameJodiTicketList.stream().map(jodiTicket -> {
				GameJodiTicketCompany newJodiTicketCompany = new GameJodiTicketCompany();

				newJodiTicketCompany.setCompany(company);
				newJodiTicketCompany.setGameJodiTicket(jodiTicket);

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

	private List<GameJodiTicket> generateGameJodiTickets(String today, DateTimeFormatter hourMinuteFmt,
			Instant createdDate) {
		List<GameJodiTimings> gameJodiTimings = gameJodiTimingsRepository.findAll();
		List<GameJodiTicket> gameJodiTicketList = gameJodiTimings.stream().map(this::calculateGameJodiTiming)
				.flatMap(List::stream).map(obj -> {
					StringBuilder sb = new StringBuilder();
					sb.append(today).append(obj.getStartTime().format(hourMinuteFmt))
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

	private List<GameJodiTicket> calculateGameJodiTiming(GameJodiTimings gameJodiTimings) {
		List<GameJodiTicket> calculateGameJodiTimingList = new ArrayList<GameJodiTicket>();

		LocalTime startTime = gameJodiTimings.getStartTime();
		LocalTime endTime = gameJodiTimings.getEndTime();
		byte duration = gameJodiTimings.getDuration();

		GameJodiDurationUnit durationUnit = Stream.of(GameJodiDurationUnit.values())
				.filter(e -> e.getUnit().equals(gameJodiTimings.getDurationUnit())).findAny()
				.orElseThrow(() -> new RuntimeException("There is no valid GameJodiDurationUnit"));
		short index = 0;
		while (true) {

			if (GameJodiDurationUnit.DURATION_MINUTE.equals(durationUnit)) {
				LocalTime tempEndTime = startTime.plusMinutes(duration);
				long tempDuration = ChronoUnit.MINUTES.between(startTime, tempEndTime);

				if (tempDuration == duration) {
					this.addGameJodiTicketToList(calculateGameJodiTimingList, startTime, tempEndTime, gameJodiTimings,
							++index);

					if (ChronoUnit.MINUTES.between(tempEndTime, endTime) <= duration) {
						this.addGameJodiTicketToList(calculateGameJodiTimingList, tempEndTime, endTime, gameJodiTimings,
								++index);
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

	private void addGameJodiTicketToList(List<GameJodiTicket> calculateGameJodiTimingList, LocalTime startTime,
			LocalTime endTime, GameJodiTimings gameJodiTimings, short index) {
		GameJodiTicket gameJodiTicket = new GameJodiTicket();
		gameJodiTicket.setStartTime(startTime);
		gameJodiTicket.setEndTime(endTime);
		gameJodiTicket.setTicketIndex(index);
		gameJodiTicket.setGameJodiTime(gameJodiTimings);

		calculateGameJodiTimingList.add(gameJodiTicket);
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
	public List<GameJodiTicket> getRegionCompanyDrawResult(String ticketNo) {
		// TODO Auto-generated method stub
		return gameJodiTicketRepository.findAllByTicketNoAndGameStatus(ticketNo, GameJodiStatus.COMPLETED);
	}

	@Override
	public void getGameByGameId(Long gameId) {
		// TODO Auto-generated method stub

	}

	@Override
	public GameJodiTicketDTO getNextGame() {

		// Get current playing game session
		Sort sort = Sort.by(Order.by("GameJodiPlayStatusId_PlayDate").withProperty("GameJodiPlayStatusId_sessionIndex"))
				.ascending();

		List<GameJodiStatus> gameJodiStatusList = Arrays.asList(GameJodiStatus.YET_TO_START, GameJodiStatus.START);

		GameJodiPlayStatus recentGameJodiPlayStatus = gameJodiPlayStatusRepository
				.findTop1ByGameJodiPlayStatusId_PlayDateAndGameStatusIn(LocalDate.now(), gameJodiStatusList, sort)
				.orElseThrow(GameJodiPlayStatusException::new);

		// Get Game Jodi ticket by current playing game id or else get the top in game
		// session(morning, or afternoon)
		Optional<GameJodiTicket> gameJodiTicketOptional;
		if (recentGameJodiPlayStatus.getCurrentPlay() > 0) {
			gameJodiTicketOptional = gameJodiTicketRepository
					.findById((long) recentGameJodiPlayStatus.getCurrentPlay());
		} else {
			gameJodiTicketOptional = gameJodiTicketRepository.findTopByGameJodiTimeAndGameStatusOrderByTicketIndexAsc(
					recentGameJodiPlayStatus.getPlaySessionTime(), GameJodiStatus.YET_TO_START);
		}

		GameJodiTicket gameJodiTicket = gameJodiTicketOptional.orElseThrow(GameJodiTicketNotFoundException::new);

		GameJodiTicketDTOBuilder builder = GameJodiTicketDTO.builder();

		// Find all companies mapped to this game jodi ticket
		List<GameJodiTicketCompany> jameGodiTicketCompany = gameJodiTicketCompanyRepository
				.findAllByGameJodiTicket(gameJodiTicket);

		List<GameJodiTicketCompanyDTO> gameJodiTicketCompanies = jameGodiTicketCompany.stream().map(cmp -> {
			GameJodiTicketCompanyDTOBuilder gameJodiTicketCompanyBuilder = GameJodiTicketCompanyDTO.builder();
			gameJodiTicketCompanyBuilder.id(cmp.getId()).companyId(cmp.getCompany().getId());
			return gameJodiTicketCompanyBuilder.build();
		}).collect(Collectors.toList());

		builder.companyTickets(gameJodiTicketCompanies);
		builder.endTime(gameJodiTicket.getEndTime()).startTime(gameJodiTicket.getStartTime()).id(gameJodiTicket.getId())
				.ticketNo(gameJodiTicket.getTicketNo());

		return builder.build();
	}

	@Override
	public void saveJodiTicket(List<GameJodiTticketTransactionsDTO> jodiTickets) {

		List<GameJodiTticketTransactions> gameJodiTticketTransactionsList = jodiTickets.stream().map(jodiTicket -> {
			GameJodiTticketTransactions gameJodiTicketTransaction = new GameJodiTticketTransactions();
			GameJodiTicketCompany ticketCompany = new GameJodiTicketCompany();
			ticketCompany.setId(jodiTicket.getTicketId());
			gameJodiTicketTransaction.setTicketId(ticketCompany);
			gameJodiTicketTransaction.setCreated(Instant.now());
			String lotteryNo = "";
			try {
				lotteryNo = objectMapper.writeValueAsString(jodiTicket.getLotteryNoMap());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			gameJodiTicketTransaction.setLotteryNo(lotteryNo);
			gameJodiTicketTransaction.setTotalQuantity(jodiTicket.getTotalQuantity());
			gameJodiTicketTransaction.setTicketRate(jodiTicket.getTicketRate());
			gameJodiTicketTransaction.setTotalAmount(jodiTicket.getTotalAmount());
			return gameJodiTicketTransaction;
		}).collect(Collectors.toList());

		gameJodiTticketTransactionsRepository.saveAll(gameJodiTticketTransactionsList);
	}

}
