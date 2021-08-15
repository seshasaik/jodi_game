package animo.realcom.mahakubera.modal.gameJodi;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(value = Include.NON_NULL)
public class GameJodiTicketTransactionDetailDTO {

	private Integer companyId;
	private Byte lotteryNo;
	private Short quantity;
	private Double ticketRate;
	private Double amount;

	private String companyName;
	private String companyCode;
	private Map<Byte, Short> detail;

}
