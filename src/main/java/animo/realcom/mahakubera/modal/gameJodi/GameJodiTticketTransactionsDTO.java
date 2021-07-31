package animo.realcom.mahakubera.modal.gameJodi;

import java.util.HashMap;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameJodiTticketTransactionsDTO {

	private Long id;
	private Long ticketId;
	private HashMap<String, Short> lotteryNoMap;
	private short totalQuantity;
	private Double ticketRate;
	private Double totalAmount;

}
