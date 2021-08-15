package animo.realcom.mahakubera.entity.GameJodi;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import animo.realcom.mahakubera.entity.Company;
import lombok.Data;

@Data
@Embeddable
public class GameJodiTicketCompanyId  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameJodiTicketCompanyId() {
		// TODO Auto-generated constructor stub
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ticket_id")
	private GameJodiTicket ticket;

}
