package animo.realcom.mahakubera.modal.gameJodi;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameJodiJournalEntryDTO {

	private Double credit;
	private LocalDateTime createdOn;
	private Double debit;
	private String description;
	private String journalEntryCode;
	private String transactionCode;
	private String ticketNo;

}
