package animo.realcom.mahakubera.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ApplicationUtil {

	public LocalDate getCurrentLocalDate(String zone) {
		ZoneId zoneId;
		if (StringUtils.hasText(zone)) {
			zoneId = ZoneId.of(zone);
		} else {
			zoneId = ZoneId.of(AppConstants.TIME_ZONE_ASIA_KOLKATA);
		}

		return LocalDate.now(zoneId);
	}

	public LocalTime getCurrentLocalTime(String zone) {
		ZoneId zoneId;
		if (StringUtils.hasText(zone)) {
			zoneId = ZoneId.of(zone);
		} else {
			zoneId = ZoneId.of(AppConstants.TIME_ZONE_ASIA_KOLKATA);
		}

		return LocalTime.now(zoneId);
	}
}
