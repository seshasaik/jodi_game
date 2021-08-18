package animo.realcom.mahakubera.util;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GameJodiTransactionStatus {

	TRANSACTION_CANCEL("CANCEL"), TRANSACTION_SUCCESS("SUCCESS"), WINING_STATUS_HOLD("HOLD"),
	WINING_STATUS_LOOSE("LOOSE"), WINING_STATUS_WIN("WIN"), CLAIM_STATUS_CLAIMED("CLAIMED"),
	CLAIM_STATUS_NON_CLAIMED("NON-CLAIMED");

	private String status;

	private GameJodiTransactionStatus(String status) {
		this.status = status;
	}

	@JsonValue
	public String getStatus() {
		return this.status;
	}
}
