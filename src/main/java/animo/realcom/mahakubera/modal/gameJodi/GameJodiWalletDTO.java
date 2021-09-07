package animo.realcom.mahakubera.modal.gameJodi;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameJodiWalletDTO {

	private Integer id;
	private String userName;
	BigDecimal amount;

}
