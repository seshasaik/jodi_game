package animo.realcom.mahakubera.modal.request;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_NULL)
public class JodiGame {

	private Long regId;
	private String gameId;
	private String ticketRate;
	private String dateTime;
	private String winningAmount;

	private String company;
	private List<String> advanceTimes;
	private String barId;

	private String company1Sum;
	private String company1Mul;
	private String startTime;
	private String drawTime;
	private String role;
	private String totalQuantity;
	private String totalAmount;
	private String quantity;
	private String total;
	private Map<String, Integer> ticketDetails;
	
	private String jokerQuantity;
	private int jokerHitCount;

}
