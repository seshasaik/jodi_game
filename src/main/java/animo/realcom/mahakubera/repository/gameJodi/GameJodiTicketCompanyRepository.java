package animo.realcom.mahakubera.repository.gameJodi;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import animo.realcom.mahakubera.entity.GameJodi.GameJodiTicket;
import animo.realcom.mahakubera.entity.GameJodi.GameJodiTicketCompany;

@Repository
public interface GameJodiTicketCompanyRepository extends JpaRepository<GameJodiTicketCompany, Long> {

	List<GameJodiTicketCompany> findAllByTicketCompany_ticket(GameJodiTicket gameJodiTicket);
}
