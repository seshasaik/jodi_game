package animo.realcom.mahakubera.entity;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import animo.realcom.mahakubera.entity.attributeConverter.RoleNameConverter;
import animo.realcom.mahakubera.util.RoleName;
import lombok.Data;

@Data
@Entity
public class Roles {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Convert(converter = RoleNameConverter.class)
	private RoleName name;
	private String description;

	public Roles() {
		// TODO Auto-generated constructor stub
	}

}
