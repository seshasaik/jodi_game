package animo.realcom.mahakubera.controller;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import animo.realcom.mahakubera.entity.JodiResult;
import animo.realcom.mahakubera.entity.JodiTicket;
import animo.realcom.mahakubera.repository.JodiTicketRepository;
import animo.realcom.mahakubera.service.GameOpenCloseService;
import animo.realcom.mahakubera.util.URIConstants;

@RestController
@RequestMapping(path = { URIConstants.VERSION })
public class GameTypeJodiGameController {

	@Autowired
	GameOpenCloseService gameOpenCloseService;
	
	@Autowired
	JodiTicketRepository repo;
	
	public GameTypeJodiGameController() {
		// TODO Auto-generated constructor stub
	}

	@GetMapping(path = URIConstants.RECENT_JODI_GAME_DRAW_RESULT)
	public ResponseEntity<List<JodiResult>> getRecentJodiGameDrawDetails() {
		List<JodiTicket> tikets = repo.tempRecentJodiTickets();
		
		List<JodiResult> jodiResultList = tikets.stream().distinct().map(y -> {
			JodiResult jr = new JodiResult();
			jr.setGameId(y.getGameId());
			jr.setCompany(y.getCompany());
			jr.setDrawTime(y.getDrawTime());
			jr.setDateTime(y.getDateTime().toString());
			int r = new Random().ints(1, 1, 8).findAny().orElse(1);
			jr.setWinningNumber(new Random().ints(r, 0, 100).distinct().mapToObj(String::valueOf).collect(Collectors.joining(",")));
			return jr;
		}).collect(Collectors.toList());
		
		return ResponseEntity.ok(jodiResultList);
//		return ResponseEntity.ok(gameOpenCloseService.getRecentGameResulst());
	}

}
