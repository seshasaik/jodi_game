package animo.realcom.mahakubera.service.jodiGame;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;

import animo.realcom.mahakubera.modal.PageDto;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiGlobalTimingDTO;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTicketDTO;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTicketTransactionRequestDTO;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTicketTransactionsDTO;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTransactionSummary;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiWalletDTO;
import animo.realcom.mahakubera.modal.gameJodi.RegionCompanyDTO;

public interface GameJodiService {

	public void loadGameJodiTicketPerDay();

	public void prepareGameJodiTicketResult();

	public void clearPastGameJodiTicket();

	public List<RegionCompanyDTO> getRegionCompanyData();

	public GameJodiTicketDTO getRecentRegionCompanyDrawResult();
	
	public GameJodiTicketDTO getCurrentGameDrawStatus(Long gameJodiTicketId);

	public GameJodiTicketDTO getNextGame();
	
	public List<GameJodiTicketDTO> getFutureGames();

	public void getGameByGameId(Long gameId);

	public PageDto getGameJodiTransactions(LocalDate transactionDate, PageRequest page);
	
	public List<GameJodiTicketTransactionsDTO> getGameJodiCancelTransactions(Long gameId);
	
	public List<GameJodiTicketTransactionsDTO> getGameJodiWiningTransactions(LocalDate transactionDate, PageRequest page);
	
	public GameJodiTicketTransactionsDTO getGameJodiTransactionsDetail(Long gameId);

	public GameJodiTicketTransactionsDTO saveJodiTicket(GameJodiTicketTransactionRequestDTO jodiTickets);

	public void cancelJodiTicket(Long transactionId);
	
	public void claimJodiTicket(Long transactionId);

	public boolean isTodayGameJodiPrepared();
	
	public List<GameJodiGlobalTimingDTO> getGameGlobalJodiTimeings();
	
	public PageDto getGameJodiJounalEntry(LocalDate entryDate, PageRequest page);
	
	public GameJodiTransactionSummary getGameJodiJounalEntrySummary(LocalDate transactionDate);
	
	public PageDto getGameJodiResult(LocalDate gameDate, PageRequest page);
	
	public GameJodiWalletDTO getGameJodiWallet();

}
