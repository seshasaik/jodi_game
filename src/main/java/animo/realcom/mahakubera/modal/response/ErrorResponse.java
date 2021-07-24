package animo.realcom.mahakubera.modal.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

	private String status;
	private String error;
	private String developerMessage;
	private String userMessage;
	private LocalDateTime timestamp;

}
