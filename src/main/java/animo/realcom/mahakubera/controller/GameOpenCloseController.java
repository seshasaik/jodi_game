package animo.realcom.mahakubera.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import animo.realcom.mahakubera.entity.JodiResult;
import animo.realcom.mahakubera.service.GameOpenCloseService;
import animo.realcom.mahakubera.util.URIConstants;

@RestController
@RequestMapping(path = { URIConstants.VERSION })
public class GameOpenCloseController {

	@Autowired
	GameOpenCloseService gameOpenCloseService;

	public GameOpenCloseController() {
		// TODO Auto-generated constructor stub
	}

	@GetMapping(path = URIConstants.RECENT_JODI_GAME_DRAW_RESULT)
	public ResponseEntity<List<JodiResult>> getRecentJodiGameDrawDetails() {
		return ResponseEntity.ok(gameOpenCloseService.getRecentGameResulst());
	}

}
