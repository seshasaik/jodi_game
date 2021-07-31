package animo.realcom.mahakubera.modal.gameJodi;

import java.time.LocalTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameJodiTicketDTO {

	private Long id;
	private String ticketNo;
	private LocalTime startTime;
	private LocalTime endTime;
	private List<GameJodiTicketCompanyDTO> companyTickets;

}
