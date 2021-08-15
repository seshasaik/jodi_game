package animo.realcom.mahakubera.util;

public enum JournalEntryStatusCode {

	VENDOR_PROFIT_ADDED("VPA"), JODI_PURCHASE_TICKET("JOPT"), JODI_CANCEL_TICKET("JOCLT"), JODI_CLAIM_TICKET("JOCMT");

	private String code;

	private JournalEntryStatusCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}
}
