package animo.realcom.mahakubera.modal.gameJodi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(value = Include.NON_NULL)
public class GameJodiTicketCompanyDTO {

	private Long id;
	private String winningNumbers;
	private Integer companyId;
	
}
