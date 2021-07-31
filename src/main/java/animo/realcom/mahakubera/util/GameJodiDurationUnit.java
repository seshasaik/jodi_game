package animo.realcom.mahakubera.util;

public enum GameJodiDurationUnit {

	DURATION_MINUTE("MIN"), DURATION_HOUR("HR");

	private GameJodiDurationUnit(String unit) {
		this.unit = unit;
	}

	private String unit;

	public String getUnit() {
		return unit;
	}
	
	

}
