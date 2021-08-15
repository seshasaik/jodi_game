package animo.realcom.mahakubera.entity.attributeConverter;

import java.util.Arrays;

import javax.persistence.AttributeConverter;

import animo.realcom.mahakubera.util.JournalEntryStatusCode;

public class JournalEntryStatusCodeConverter implements AttributeConverter<JournalEntryStatusCode, String> {

	public JournalEntryStatusCodeConverter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String convertToDatabaseColumn(JournalEntryStatusCode attribute) {
		// TODO Auto-generated method stub
		return attribute.getCode();
	}

	@Override
	public JournalEntryStatusCode convertToEntityAttribute(String dbData) {
		// TODO Auto-generated method stub
		
		return Arrays.asList(JournalEntryStatusCode.values()).stream().filter(journalEntryCode -> journalEntryCode.getCode().equals(dbData))
				.findFirst().orElseThrow(() -> new RuntimeException("Unable to convert String to JournalEntryStatusCode Enum"));
	}

}
