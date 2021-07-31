package animo.realcom.mahakubera.entity.GameJodi.attributeConverter;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;

import animo.realcom.mahakubera.exception.AttributeConvertionException;
import animo.realcom.mahakubera.util.GameJodiStatus;

public class GameJodiStatusConverter implements AttributeConverter<GameJodiStatus, String> {

	public GameJodiStatusConverter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String convertToDatabaseColumn(GameJodiStatus attribute) {
		// TODO Auto-generated method stub
		return attribute.getStatus();
	}

	@Override
	public GameJodiStatus convertToEntityAttribute(String dbData) {
		// TODO Auto-generated method stub
		return Stream.of(GameJodiStatus.values()).filter(status -> status.getStatus().equals(dbData)).findAny()
				.orElseThrow(() -> new AttributeConvertionException("Unable to convert the data"));

	}

}
