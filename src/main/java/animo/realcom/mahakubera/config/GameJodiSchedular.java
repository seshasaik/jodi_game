package animo.realcom.mahakubera.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;

import animo.realcom.mahakubera.service.jodiGame.GameJodiService;
import animo.realcom.mahakubera.util.AppConstants;

@Configuration
@EnableScheduling
public class GameJodiSchedular {

	@Autowired
	GameJodiService gameJodiService;

	public GameJodiSchedular() {
		// TODO Auto-generated constructor stub
	}

	@Scheduled(cron = "0 0 0 * * *", zone = AppConstants.TIME_ZONE_ASIA_KOLKATA)
	public void createGameJodiTicketPerDay() {
		gameJodiService.loadGameJodiTicketPerDay();
	}

	@Schedules(value = { @Scheduled(cron = "30 0/15 9-14 * * *", zone = AppConstants.TIME_ZONE_ASIA_KOLKATA),
			@Scheduled(cron = "30 0/20 15-22 * * *", zone = AppConstants.TIME_ZONE_ASIA_KOLKATA),
			@Scheduled(cron = "30 0/30 23 * * *", zone = AppConstants.TIME_ZONE_ASIA_KOLKATA),
			@Scheduled(cron = "30 0/30 0-8 * * *", zone = AppConstants.TIME_ZONE_ASIA_KOLKATA) })

	public void prepareGameJodiResultForIndiaTimeZone() {
		gameJodiService.prepareGameJodiTicketResult();
	}

}
