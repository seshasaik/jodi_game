package animo.realcom.mahakubera.repository.gameJodi;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import animo.realcom.mahakubera.entity.GameJodi.GameJodiTicket;
import animo.realcom.mahakubera.entity.GameJodi.GameJodiTimings;
import animo.realcom.mahakubera.util.GameJodiStatus;

@Repository
public interface GameJodiTicketRepository extends JpaRepository<GameJodiTicket, Long> {

	@Query(value = "select max(id) from GameJodiTicket")
	Long findMaxId();

	List<GameJodiTicket> findAllByTicketNoAndGameStatus(String ticketNo, GameJodiStatus status);

	Optional<GameJodiTicket> findTopByGameJodiTimeAndGameStatusOrderByTicketIndexAsc(GameJodiTimings gameJodiTime,
			GameJodiStatus gameStatus);
	
	
}
