package animo.realcom.mahakubera.entity;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import animo.realcom.mahakubera.entity.attributeConverter.JournalEntryStatusCodeConverter;
import animo.realcom.mahakubera.util.JournalEntryStatusCode;
import lombok.Data;

@Data
@Entity
public class JournalEntryStatus {

	public JournalEntryStatus() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Convert(converter = JournalEntryStatusCodeConverter.class)
	private JournalEntryStatusCode journalCode;

	private String description;

}
