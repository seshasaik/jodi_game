package animo.realcom.mahakubera.modal.response;

import lombok.Data;

@Data
public class ErrorResponse {

	public ErrorResponse() {
		// TODO Auto-generated constructor stub
	}

	private String statusCode;
	private String message;

}
