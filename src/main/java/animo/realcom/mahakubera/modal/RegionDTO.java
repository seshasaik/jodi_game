package animo.realcom.mahakubera.modal;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegionDTO {

	@NotNull(message = "region id should not be empty")
	private Integer id;

	private String name;

	private String shortCode;

	private byte status;
	private CountryDTO country;

}
