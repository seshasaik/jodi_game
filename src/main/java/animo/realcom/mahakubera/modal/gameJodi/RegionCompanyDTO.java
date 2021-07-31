package animo.realcom.mahakubera.modal.gameJodi;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegionCompanyDTO {

	private Integer id;
	private String originalCompanyName;
	private String originalCompanyCode;
	private String regionCompanyName;
	private String regionCompanyCode;
	private LocalDateTime dateOfRegister;
	private BigDecimal ticketRate;
	private Short maxQuantity;
	private BigDecimal winningPrizeAmount;
	private byte status;

}
