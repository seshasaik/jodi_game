package animo.realcom.mahakubera.entity.GameJodi;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import animo.realcom.mahakubera.entity.Company;
import lombok.Data;

@Embeddable
@Data
public class GameJodiTicketTransactionsDetailId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameJodiTicketTransactionsDetailId() {
		// TODO Auto-generated constructor stub
	}

	@ManyToOne
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;

	@ManyToOne
	@JoinColumn(name = "transaction_id", nullable = false)
	private GameJodiTicketTransactions transaction;

	@Column(nullable = false)
	private byte lotteryNo;

}
