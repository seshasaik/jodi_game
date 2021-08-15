package animo.realcom.mahakubera.entity.GameJodi.attributeConverter;

import java.util.Arrays;

import javax.persistence.AttributeConverter;

import animo.realcom.mahakubera.util.GameJodiTransactionStatus;

public class GameJodiTransactionStatusConverter implements AttributeConverter<GameJodiTransactionStatus, String> {

	public GameJodiTransactionStatusConverter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String convertToDatabaseColumn(GameJodiTransactionStatus attribute) {
		// TODO Auto-generated method stub
		return attribute.getStatus();
	}

	@Override
	public GameJodiTransactionStatus convertToEntityAttribute(String dbData) {
		// TODO Auto-generated method stub
		return Arrays.asList(GameJodiTransactionStatus.values()).stream()
				.filter(status -> status.getStatus().equals(dbData)).findAny()
				.orElseThrow(() -> new RuntimeException("Unable to convert value to game jodi Transaction status"));

	}

}
