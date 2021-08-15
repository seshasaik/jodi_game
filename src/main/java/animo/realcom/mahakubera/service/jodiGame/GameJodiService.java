package animo.realcom.mahakubera.service.jodiGame;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import animo.realcom.mahakubera.entity.GameJodi.GameJodiTicket;
import animo.realcom.mahakubera.modal.PageDto;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiGlobalTimingDTO;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTicketDTO;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTicketTransactionRequestDTO;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTticketTransactionsDTO;
import animo.realcom.mahakubera.modal.gameJodi.RegionCompanyDTO;

public interface GameJodiService {

	public void loadGameJodiTicketPerDay();

	public void prepareGameJodiTicketResult();

	public void clearPastGameJodiTicket();

	public List<RegionCompanyDTO> getRegionCompanyData();

	public GameJodiTicketDTO getRecentRegionCompanyDrawResult();

	public GameJodiTicketDTO getNextGame();

	public void getGameByGameId(Long gameId);

	public PageDto getGameJodiTransactions(LocalDate transactionDate, PageRequest page);
	
	public GameJodiTticketTransactionsDTO getGameJodiTransactionsDetail(Long transactionId);

	public GameJodiTicketTransactionRequestDTO saveJodiTicket(GameJodiTicketTransactionRequestDTO jodiTickets);

	public void cancelJodiTicket(Long transactionId);

	public boolean isTodayGameJodiPrepared();
	
	public List<GameJodiGlobalTimingDTO> getGameGlobalJodiTimeings();
	
	public PageDto getGameJodiJounalEntry(LocalDate entryDate, PageRequest page);

}
