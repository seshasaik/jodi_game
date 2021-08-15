package animo.realcom.mahakubera.modal.gameJodi;

import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameJodiGlobalTimingDTO {

	private Short id;
	private String name;
	private LocalTime startTime;
	private LocalTime endTime;
	private byte duration;
	private String durationUnit;
	private String description;

}
