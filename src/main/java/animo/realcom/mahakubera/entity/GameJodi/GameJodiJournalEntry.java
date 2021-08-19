package animo.realcom.mahakubera.entity.GameJodi;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import animo.realcom.mahakubera.entity.JournalEntryStatus;
import animo.realcom.mahakubera.entity.User;
import lombok.Data;

@Data
@Entity
public class GameJodiJournalEntry {

	public GameJodiJournalEntry() {
		// TODO Auto-generated constructor stub
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "vendor_id")
	private User vendor;
	
	@ManyToOne
	@JoinColumn(name = "vendor_user_id")
	private User vendorUser;
	
	@ManyToOne
	@JoinColumn(name = "admin_id")
	private User admin;
	
	@ManyToOne
	@JoinColumn(name = "ticket_id")
	private GameJodiTicket gameJodiTicket;
	
	private Double credit = new Double(0);
	private Double debit = new Double(0);
	
	private Instant created = Instant.now();
	
	@ManyToOne
	@JoinColumn(name = "journal_code_id")
	private JournalEntryStatus journalEntry;
	
	


}
