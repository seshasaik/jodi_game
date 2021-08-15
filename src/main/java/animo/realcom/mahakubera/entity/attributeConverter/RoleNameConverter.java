package animo.realcom.mahakubera.entity.attributeConverter;

import java.util.Arrays;

import javax.persistence.AttributeConverter;

import animo.realcom.mahakubera.util.RoleName;

public class RoleNameConverter implements AttributeConverter<RoleName, String> {

	public RoleNameConverter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String convertToDatabaseColumn(RoleName attribute) {
		// TODO Auto-generated method stub
		return attribute.getRoleName();
	}

	@Override
	public RoleName convertToEntityAttribute(String dbData) {
		// TODO Auto-generated method stub
		return Arrays.asList(RoleName.values()).stream().filter(roleEnum -> roleEnum.getRoleName().equals(dbData))
				.findFirst().orElseThrow(() -> new RuntimeException("Unable to convert String to RoleName Enum"));

	}

}
