package animo.realcom.mahakubera.modal.gameJodi;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameJodiTransactionSummary {

	private BigDecimal totalAmount;
	private BigDecimal totalCancelAmount;
	private BigDecimal totalClaimAmount;

}
