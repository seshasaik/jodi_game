package animo.realcom.mahakubera.config;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import animo.realcom.mahakubera.service.jodiGame.GameJodiService;

@Component
public class ApplicationStarUpListener implements ApplicationRunner {

	@Autowired
	GameJodiService gameJodiService;

	AtomicInteger counter = new AtomicInteger(0);

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("counter value " + counter.get());
		if (counter.get() == 0) {
			
			if (!gameJodiService.isTodayGameJodiPrepared())
				gameJodiService.loadGameJodiTicketPerDay();

			gameJodiService.clearPastGameJodiTicket();
			counter.set(1);
		}
	}

}
