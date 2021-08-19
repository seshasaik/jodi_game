package animo.realcom.mahakubera.modal.gameJodi;

import java.util.List;

import animo.realcom.mahakubera.modal.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GameJodiTicketTransactionRequestDTO {

	private Long id;
	private Long ticketId;
	private String ticketCode;
	private String transactionCode;
	private short totalQuantity;
	private Double totalAmount;
	private UserDTO user;
	private List<GameJodiTicketTransactionDetailDTO> detail;

}
