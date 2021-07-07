package animo.realcom.mahakubera.util;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserType {

	VENDOR("ROLE_VENDOR"), USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

	String type;

	UserType(String type) {
		this.type = type;
	}

	@JsonValue
	public String getType() {
		return this.type;
	}
	
	@JsonCreator
	public static UserType getUserTypeFromValue (String value) {
		return Arrays.stream(UserType.values()).filter(userType -> userType.getType().equals(value)).findAny().orElse(null);
	}
}
