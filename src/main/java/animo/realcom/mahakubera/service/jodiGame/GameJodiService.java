package animo.realcom.mahakubera.service.jodiGame;

import java.util.List;

import animo.realcom.mahakubera.entity.GameJodi.GameJodiTicket;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTicketDTO;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTticketTransactionsDTO;
import animo.realcom.mahakubera.modal.gameJodi.RegionCompanyDTO;

public interface GameJodiService {

	public void saveJodiTicket(List<GameJodiTticketTransactionsDTO> jodiTickets);

	public void loadGameJodiTicketPerDay();

	public List<RegionCompanyDTO> getRegionCompanyData();

	public List<GameJodiTicket> getRegionCompanyDrawResult(String ticketNo);

	public GameJodiTicketDTO getNextGame();

	public void getGameByGameId(Long gameId);

}
