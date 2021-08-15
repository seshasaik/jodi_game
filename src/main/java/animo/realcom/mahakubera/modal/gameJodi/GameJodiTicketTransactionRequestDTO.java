package animo.realcom.mahakubera.modal.gameJodi;

import java.time.LocalDateTime;
import java.util.List;

import animo.realcom.mahakubera.modal.UserDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameJodiTicketTransactionRequestDTO {

	private Long id;
	private Long ticketId;
	private String gameId;
	private String transactionCode;
	private short totalQuantity;
	private Double totalAmount;
	private LocalDateTime dateTime;
	private UserDTO user;
	private List<GameJodiTicketTransactionDetailDTO> detail;

}
