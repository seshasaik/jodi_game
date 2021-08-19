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
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
public class GameJodiTticketTransactionsDTO extends GameJodiTicketTransactionRequestDTO {

	private String status;
	private LocalDateTime created;
	private LocalDateTime canceled;
	private LocalDateTime claimed;
	private String winningNumbers;
	private LocalTime startTime;
	private LocalTime endTime;
	private GameJodiStatus gameStatus;
	private GameJodiTransactionStatus winStatus;
	private GameJodiTransactionStatus claimStatus;

	@Builder(builderMethodName = "childBuilder")
	public GameJodiTticketTransactionsDTO(Long id, Long ticketId, String ticketCode, String transactionCode,
			short totalQuantity, Double totalAmount, UserDTO user, List<GameJodiTicketTransactionDetailDTO> detail,
			String status, LocalDateTime created, LocalDateTime canceled, LocalDateTime claimed, String winningNumbers,
			LocalTime startTime, LocalTime endTime, GameJodiStatus gameStatus, GameJodiTransactionStatus winStatus,
			GameJodiTransactionStatus claimStatus) {
		super(id, ticketId, ticketCode, transactionCode, totalQuantity, totalAmount, user, detail);
		this.status = status;
		this.created = created;
		this.canceled = canceled;
		this.claimed = claimed;
		this.winningNumbers = winningNumbers;
		this.startTime = startTime;
		this.endTime = endTime;
		this.gameStatus = gameStatus;
		this.winStatus = winStatus;
		this.claimStatus = claimStatus;
	}

}
