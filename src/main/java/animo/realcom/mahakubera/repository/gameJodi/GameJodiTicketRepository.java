package animo.realcom.mahakubera.repository.gameJodi;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import animo.realcom.mahakubera.entity.GameJodi.GameJodiTicket;
import animo.realcom.mahakubera.entity.GameJodi.GameJodiTicketCompany;
import animo.realcom.mahakubera.entity.GameJodi.GameJodiTimings;
import animo.realcom.mahakubera.util.GameJodiStatus;

@Repository
public interface GameJodiTicketRepository extends JpaRepository<GameJodiTicket, Long> {

	final String GAME_JODI_TICKET_BY_GAME_DATE = "select tkt from GameJodiTicket tkt join fetch tkt.companies company join fetch company.ticketCompany.company as company1 where tkt.gameDate = :gameDate and tkt.gameStatus = :gameStatus";

	@Query(value = "select max(id) from GameJodiTicket")
	Long findMaxId();

	@Query(value = GAME_JODI_TICKET_BY_GAME_DATE)
	List<GameJodiTicket> findRecentCompletedGameJodiResult(@Param("gameDate") LocalDate gameDate,
			@Param("gameStatus") GameJodiStatus status, Pageable page);

	@Query(value = "select tkt from GameJodiTicket tkt where gameDate = :gameDate and gameStatus in(:gameStatusList) order by ticketIndex asc")
	List<GameJodiTicket> findNextGame(@Param("gameDate") LocalDate gameDate,
			@Param("gameStatusList") List<GameJodiStatus> gameStatus, Pageable page);
	
	@Query(value = "select tkt from GameJodiTicket tkt where gameDate = :gameDate and gameStatus = :gameStatus order by ticketIndex asc")
	List<GameJodiTicket> findFutureGame(@Param("gameDate") LocalDate gameDate,
			@Param("gameStatus") GameJodiStatus gameStatus);

	@Query(value = "select tkt from GameJodiTicket tkt where gameDate <= :gameDate and gameStatus not in(:gameStatusList) order by ticketIndex asc")
	List<GameJodiTicket> findGameByGameDateAndGameStatusNotIn(@Param("gameDate") LocalDate gameDate,
			@Param("gameStatusList") List<GameJodiStatus> gameStatus);

	@Query(value = GAME_JODI_TICKET_BY_GAME_DATE, countQuery = "select count(1) from GameJodiTicket as t1 where t1.gameDate = :gameDate and t1.gameStatus = :gameStatus")
	Page<GameJodiTicket> findAllGameJodiTicketByGameDate(@Param("gameDate") LocalDate gameDate,
			@Param("gameStatus") GameJodiStatus status, Pageable page);

	Optional<GameJodiTicket> findTopByGameJodiTimeAndGameStatusOrderByTicketIndexAsc(GameJodiTimings gameJodiTime,
			GameJodiStatus gameStatus);

	Optional<GameJodiTicket> findTop1ByGameDateOrderByTicketIndexDesc(LocalDate localDate);
	
	Optional<GameJodiTicket> findTop1ByGameDateAndResultStatusOrderByTicketIndexDesc(LocalDate localDate, GameJodiStatus gameStatus);

}
