package animo.realcom.mahakubera.util;

public enum GameJodiStatus {
	
	YET_TO_START("YET_TO_START"), CANCELED("CANCLED"), START("START"), COMPLETED("COMPLETED");
	private String status;

	private GameJodiStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}
}
