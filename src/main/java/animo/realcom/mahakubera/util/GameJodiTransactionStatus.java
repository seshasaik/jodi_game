package animo.realcom.mahakubera.util;

public enum GameJodiTransactionStatus {

	CANCEL("CANCEL"), SUCCESS("SUCCESS");

	private String status;

	private GameJodiTransactionStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}
}
