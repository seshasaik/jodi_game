package animo.realcom.mahakubera.modal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountryDTO {

	private Integer id;

	private String name;
	private String shortCode;

	private byte status;
}
