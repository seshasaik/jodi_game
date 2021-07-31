package animo.realcom.mahakubera.entity.GameJodi;

import java.time.Instant;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import animo.realcom.mahakubera.entity.TransactionStatusCodes;
import lombok.Data;

@Data
@Entity
public class GameJodiTticketTransactions {

	public GameJodiTticketTransactions() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ticket_id")
	private GameJodiTicketCompany ticketId;

	@Column(name = "lottery_no_map")
	private String lotteryNo;

	private short totalQuantity;

	private Double ticketRate;
	private Double totalAmount;
	private Instant created;
	private Instant canceled;

	@ManyToOne
	@JoinColumn
	private TransactionStatusCodes status;
}
