package animo.realcom.mahakubera.entity.GameJodi;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class GameJodiTicketTransactionDetails {
	public GameJodiTicketTransactionDetails() {
		// TODO Auto-generated constructor stub
	}

	@EmbeddedId
	private GameJodiTicketTransactionsDetailId detailId;

	private short quantity;
	private Double ticketRate;
	private Double amount;
	private Double winingAmount;
	private String winingStatus;

}
