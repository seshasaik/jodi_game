package animo.realcom.mahakubera.modal;

import javax.validation.constraints.NotNull;

import animo.realcom.mahakubera.util.RoleName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleDTO {

	@NotNull(message = "role id should not be empty")
	private Long id;
	private RoleName name;
	private String description;

}
