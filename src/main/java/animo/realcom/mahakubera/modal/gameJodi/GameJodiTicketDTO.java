package animo.realcom.mahakubera.modal.gameJodi;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import animo.realcom.mahakubera.util.GameJodiStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(value = Include.NON_NULL)
public class GameJodiTicketDTO {

	private Long id;
	private String ticketNo;
	private LocalTime startTime;
	private LocalTime endTime;
	private LocalDate date;
	private GameJodiStatus gameStatus;
	private GameJodiStatus resultStatus;
	private List<GameJodiTicketCompanyDTO> companies;

}
