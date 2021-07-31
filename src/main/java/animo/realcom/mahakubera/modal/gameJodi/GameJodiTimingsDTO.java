package animo.realcom.mahakubera.modal.gameJodi;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameJodiTimingsDTO {

	private Short id;
	private LocalTime startTime;
	private LocalTime endTime;
	private byte duration;
	private String durationUnit;
	private String description;
	private LocalDateTime created;
	private LocalDateTime updated;
	
	//Company related filed
	private Long companyId;
	private String companyLocalName;
	private String code;
	private LocalDateTime dateOfRegister;
	private BigDecimal ticketRate;
	private Short maxQuantity;
	private BigDecimal winningPrizeAmount;

}
