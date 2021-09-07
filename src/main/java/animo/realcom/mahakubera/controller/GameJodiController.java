package animo.realcom.mahakubera.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import animo.realcom.mahakubera.entity.GameJodi.GameJodiWallet;
import animo.realcom.mahakubera.modal.PageDto;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiGlobalTimingDTO;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTicketDTO;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTicketTransactionRequestDTO;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTransactionSummary;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiWalletDTO;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTicketTransactionsDTO;
import animo.realcom.mahakubera.modal.gameJodi.RegionCompanyDTO;
import animo.realcom.mahakubera.modal.response.SuccessResponse;
import animo.realcom.mahakubera.service.jodiGame.GameJodiService;
import animo.realcom.mahakubera.util.URIConstants;

@RestController
@RequestMapping(path = { URIConstants.VERSION + URIConstants.GAME_JODI_BASE_PATH })
public class GameJodiController {

	@Autowired
	GameJodiService gameJodiService;

	@GetMapping(path = URIConstants.JODI_GAME_TIMING_LIST)
	public ResponseEntity<List<GameJodiGlobalTimingDTO>> getGameTimingList() {
		return ResponseEntity.ok(gameJodiService.getGameGlobalJodiTimeings());
	}

	@GetMapping(path = URIConstants.GAME_JODI_COMAPANY_LIST)
	public ResponseEntity<List<RegionCompanyDTO>> getRegionCompanies() {
		return ResponseEntity.ok(gameJodiService.getRegionCompanyData());
	}

	@GetMapping(path = URIConstants.GAME_JODI_NEXT_GAME)
	public ResponseEntity<GameJodiTicketDTO> getNextGame() {
		return ResponseEntity.ok(gameJodiService.getNextGame());
	}

	@GetMapping(path = URIConstants.GAME_JODI_FUTURE_GAME)
	public ResponseEntity<List<GameJodiTicketDTO>> getFutureGame() {
		return ResponseEntity.ok(gameJodiService.getFutureGames());
	}

	@GetMapping(path = URIConstants.JODI_GAME_RECENT_DRAW_RESULT)
	public ResponseEntity<GameJodiTicketDTO> getRecentGameJodiDrawDetails() {
		GameJodiTicketDTO gameJodiTicket = gameJodiService.getRecentRegionCompanyDrawResult();
		return ResponseEntity.ok(gameJodiTicket);
	}

	@GetMapping(path = URIConstants.JODI_GAME_CURRENT_DRAW_RESULT_STATUS)
	public ResponseEntity<GameJodiTicketDTO> getCurrentGameJodiDrawStatus(@PathVariable Long gameJodiTicketId) {
		GameJodiTicketDTO gameJodiTicket = gameJodiService.getCurrentGameDrawStatus(gameJodiTicketId);
		return ResponseEntity.ok(gameJodiTicket);
	}

	@PostMapping(path = URIConstants.GAME_GODI_SAVE_TRANSACTION)
	public ResponseEntity<GameJodiTicketTransactionRequestDTO> saveGameJodiTransaction(
			@RequestBody GameJodiTicketTransactionRequestDTO jodiTicket) {
		jodiTicket = gameJodiService.saveJodiTicket(jodiTicket);
		return ResponseEntity.ok(jodiTicket);
	}

	@PutMapping(path = URIConstants.GAME_GODI_CANCEL_TRANSACTION)
	public ResponseEntity<SuccessResponse> cancelGameJodiTransaction(
			@PathVariable(name = "transactionId", required = true) Long transactionId) {
		gameJodiService.cancelJodiTicket(transactionId);
		SuccessResponse successResponse = new SuccessResponse();
		successResponse.setMessage("Game Jodi Ticket Transaction with cancelled succefully");
		return ResponseEntity.ok(successResponse);
	}

	@PutMapping(path = URIConstants.GAME_GODI_CLAIM_TRANSACTION)
	public ResponseEntity<SuccessResponse> claimGameJodiTransaction(
			@PathVariable(name = "transactionId", required = true) Long transactionId) {
		gameJodiService.claimJodiTicket(transactionId);
		SuccessResponse successResponse = new SuccessResponse();
		successResponse.setMessage("Game Jodi Ticket Transaction claimed succefully");
		return ResponseEntity.ok(successResponse);
	}

	@GetMapping(path = URIConstants.GAME_GODI_TRANSACTION_LIST)
	public ResponseEntity<PageDto> getGameJodiTransaction(
			@RequestParam(name = "transactionDate", required = false) LocalDate transactionDate,
			@RequestParam(name = "page-index") int pageIndex, @RequestParam(name = "page-size") int pageSize) {
		PageRequest page = PageRequest.of(pageIndex, pageSize, Sort.by(Direction.DESC, "created"));
		return ResponseEntity.ok(gameJodiService.getGameJodiTransactions(transactionDate, page));
	}

	@GetMapping(path = URIConstants.GAME_GODI_CANCELED_TRANSACTION_LIST)
	public ResponseEntity<List<GameJodiTicketTransactionsDTO>> getGameJodiCancelTransaction(
			@RequestParam(name = "gameId") Long gameId) {

		return ResponseEntity.ok(gameJodiService.getGameJodiCancelTransactions(gameId));
	}

	@GetMapping(path = URIConstants.GAME_GODI_WINING_TRANSACTION_LIST)
	public ResponseEntity<List<GameJodiTicketTransactionsDTO>> getGameJodiWiningTransaction(
			@RequestParam(name = "transactionDate", required = false) LocalDate transactionDate,
			@RequestParam(name = "page-index") int pageIndex, @RequestParam(name = "page-size") int pageSize) {
		PageRequest page = PageRequest.of(pageIndex, pageSize, Sort.by(Direction.DESC, "created"));
		return ResponseEntity.ok(gameJodiService.getGameJodiWiningTransactions(transactionDate, page));
	}

	@GetMapping(path = URIConstants.GAME_GODI_TRANSACTION_DETAIL)
	public ResponseEntity<GameJodiTicketTransactionsDTO> getGameJodiTransactionDetail(
			@PathVariable(name = "transactionId", required = true) Long transactionId) {

		return ResponseEntity.ok(gameJodiService.getGameJodiTransactionsDetail(transactionId));
	}

	@GetMapping(path = URIConstants.JODI_GAME_JOURNAL_ENTRY)
	public ResponseEntity<PageDto> getGameJodiJournalEntry(
			@RequestParam(name = "journalDate") @DateTimeFormat(iso = ISO.DATE) LocalDate journalDate,
			@RequestParam(name = "page-index") int pageIndex, @RequestParam(name = "page-size") int pageSize,
			@RequestParam(name = "tiketId", required = false) String ticketId) {
		PageRequest page = PageRequest.of(pageIndex, pageSize, Sort.by(Direction.DESC, "created"));

		return ResponseEntity.ok(gameJodiService.getGameJodiJounalEntry(journalDate, page));
	}

	@GetMapping(path = URIConstants.JODI_GAME_JOURNAL_ENTRY_SUMMARY)
	public ResponseEntity<GameJodiTransactionSummary> getGameJodiJournalEntrySummary(
			@RequestParam(name = "journalDate") @DateTimeFormat(iso = ISO.DATE) LocalDate journalDate) {
		return ResponseEntity.ok(gameJodiService.getGameJodiJounalEntrySummary(journalDate));
	}

	@GetMapping(path = URIConstants.JODI_GAME_RESULTS)
	public ResponseEntity<PageDto> getGameJodiResults(
			@RequestParam(name = "gameDate") @DateTimeFormat(iso = ISO.DATE) LocalDate gameDate,
			@RequestParam(name = "page-index") int pageIndex, @RequestParam(name = "page-size") int pageSize) {

		PageRequest page = PageRequest.of(pageIndex, pageSize, Sort.by(Direction.DESC, "ticketIndex"));

		return ResponseEntity.ok(gameJodiService.getGameJodiResult(gameDate, page));
	}

	@GetMapping(path = URIConstants.JODI_GAME_WALLET)
	public ResponseEntity<GameJodiWalletDTO> getGameJodiWallet() {
		return ResponseEntity.ok(gameJodiService.getGameJodiWallet());
	}

	@GetMapping(path = { "load" })
	public ResponseEntity<String> loadGameJodiTimings() {
		gameJodiService.loadGameJodiTicketPerDay();
//		gameJodiService.clearPastGameJodiTicket();
		return ResponseEntity.ok("Success");
	}
}
