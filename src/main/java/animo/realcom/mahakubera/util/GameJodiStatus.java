package animo.realcom.mahakubera.util;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GameJodiStatus {
	
	YET_TO_START("YET_TO_START"), CANCELED("CANCLED"), START("START"), COMPLETED("COMPLETED");
	private String status;

	private GameJodiStatus(String status) {
		this.status = status;
	}

	@JsonValue
	public String getStatus() {
		return this.status;
	}
}
