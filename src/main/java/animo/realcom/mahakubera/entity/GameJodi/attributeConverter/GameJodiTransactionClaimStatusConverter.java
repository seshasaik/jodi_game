package animo.realcom.mahakubera.entity.GameJodi.attributeConverter;

import java.util.Arrays;
import java.util.Objects;

import javax.persistence.AttributeConverter;

import animo.realcom.mahakubera.util.GameJodiTransactionStatus;

public class GameJodiTransactionClaimStatusConverter implements AttributeConverter<GameJodiTransactionStatus, String> {

	public GameJodiTransactionClaimStatusConverter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String convertToDatabaseColumn(GameJodiTransactionStatus attribute) {
		// TODO Auto-generated method stub
		if(Objects.nonNull(attribute)) {
			return attribute.getStatus();
		}
		return null;
	}

	@Override
	public GameJodiTransactionStatus convertToEntityAttribute(String dbData) {
		// TODO Auto-generated method stub
		if(Objects.nonNull(dbData)) {
			return Arrays.asList(GameJodiTransactionStatus.values()).stream()
					.filter(status -> status.getStatus().equals(dbData)).findAny()
					.orElseThrow(() -> new RuntimeException("Unable to convert value to game jodi Transaction status"));
		}
		return null;
	}

}
