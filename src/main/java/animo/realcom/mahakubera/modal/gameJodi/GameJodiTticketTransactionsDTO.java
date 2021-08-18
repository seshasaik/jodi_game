package animo.realcom.mahakubera.modal.gameJodi;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import animo.realcom.mahakubera.modal.UserDTO;
import animo.realcom.mahakubera.util.GameJodiStatus;
import animo.realcom.mahakubera.util.GameJodiTransactionStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(value = Include.NON_NULL)
public class GameJodiTticketTransactionsDTO {

	private Long Id;
	private String transactionCode;
	private Short totalQuantity;
	private Double totalAmount;
	private String status;
	private LocalDateTime created;
	private LocalDateTime canceled;
	private LocalDateTime claimed;
	private String winningNumbers;
	private String ticketNo;
	private LocalTime startTime;
	private LocalTime endTime;
	private GameJodiStatus gameStatus;
	private GameJodiTransactionStatus winStatus;
	private GameJodiTransactionStatus claimStatus;
	private UserDTO user;

	private List<GameJodiTicketTransactionDetailDTO> detail;
}
