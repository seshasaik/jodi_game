package animo.realcom.mahakubera.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import animo.realcom.mahakubera.entity.JodiTicket;

@Repository
public interface JodiTicketRepository extends JpaRepository<JodiTicket, Long> {

	@Query(value = "select * from jodi_ticket_table_new where game_id = (select game_id from jodi_ticket_table_new where date_time between date_sub(UTC_TIMESTAMP(), interval 20 MINUTE) and UTC_TIMESTAMP() order by date_time desc limit 1)", nativeQuery = true)
	public List<JodiTicket> tempRecentJodiTickets();
}
