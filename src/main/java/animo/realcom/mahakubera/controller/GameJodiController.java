package animo.realcom.mahakubera.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import animo.realcom.mahakubera.modal.gameJodi.GameJodiTicketDTO;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTticketTransactionsDTO;
import animo.realcom.mahakubera.modal.gameJodi.RegionCompanyDTO;
import animo.realcom.mahakubera.service.jodiGame.GameJodiService;
import animo.realcom.mahakubera.util.URIConstants;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping(path = { URIConstants.VERSION + URIConstants.GAME_JODI_BASE_PATH })
public class GameJodiController {

	@Autowired
	GameJodiService gameJodiService;

	public GameJodiController() {
		// TODO Auto-generated constructor stub
	}

	@GetMapping(path = URIConstants.GAME_JODI_COMAPANY_LIST)
	public ResponseEntity<List<RegionCompanyDTO>> getRegionCompanies() {
		return ResponseEntity.ok(gameJodiService.getRegionCompanyData());
	}

	@GetMapping(path = URIConstants.GAME_JODI_NEXT_GAME)
	public ResponseEntity<GameJodiTicketDTO> getNextGame() {
		return ResponseEntity.ok(gameJodiService.getNextGame());
	}

	@GetMapping(path = URIConstants.RECENT_JODI_GAME_DRAW_RESULT)
	public ResponseEntity<?> getRecentGameJodiDrawDetails() {
		// gameJodiService.
		return ResponseEntity.ok(null);
	}

	@PostMapping(path = URIConstants.SAVE_GAME_GODI_TRANSACTION)
	public ResponseEntity<?> saveGameJodiTransaction(@RequestBody List<GameJodiTticketTransactionsDTO> jodiTickets) {
		gameJodiService.saveJodiTicket(jodiTickets);
		return ResponseEntity.created(null).build();
	}
	
	@PutMapping(path = URIConstants.CANCEL_GAME_GODI_TRANSACTION)
	public ResponseEntity<?> cancelGameJodiTransaction(@RequestBody List<GameJodiTticketTransactionsDTO> jodiTickets) {
		gameJodiService.saveJodiTicket(jodiTickets);
		return ResponseEntity.created(null).build();
	}
	
	@GetMapping(path = URIConstants.GET_GAME_GODI_TRANSACTION)
	public ResponseEntity<?> getGameJodiTransaction() {
		
		return ResponseEntity.created(null).build();
	}

	@GetMapping(path = { "load" })
	public ResponseEntity<String> loadGameJodiTimings() {
		gameJodiService.loadGameJodiTicketPerDay();
		return ResponseEntity.ok("Success");
	}
}
