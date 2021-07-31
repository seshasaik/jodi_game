package animo.realcom.mahakubera.entity.GameJodi;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import animo.realcom.mahakubera.entity.Company;
import lombok.Data;

@Data
@Entity()
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "ticket_id", "company_id" }) })
public class GameJodiTicketCompany implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameJodiTicketCompany() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String winningNumbers;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ticket_id")
	private GameJodiTicket gameJodiTicket;

}
