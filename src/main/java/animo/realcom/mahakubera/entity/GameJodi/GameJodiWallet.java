package animo.realcom.mahakubera.entity.GameJodi;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import animo.realcom.mahakubera.entity.User;
import lombok.Data;

@Data
@Entity
public class GameJodiWallet {

	public GameJodiWallet() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn
	private GameJodiTicket ticketId;

	private String transaction_type;

	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "vendor_id")
	private User user;

	private BigDecimal amount;
	private LocalDateTime created;

}
