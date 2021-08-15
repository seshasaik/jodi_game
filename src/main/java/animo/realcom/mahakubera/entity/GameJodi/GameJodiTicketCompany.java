package animo.realcom.mahakubera.entity.GameJodi;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity()
public class GameJodiTicketCompany implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameJodiTicketCompany() {
		// TODO Auto-generated constructor stub
	}

	@EmbeddedId
	private GameJodiTicketCompanyId ticketCompany;
	private String winningNumbers;

}
