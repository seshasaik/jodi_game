package animo.realcom.mahakubera.modal.response;

import lombok.Data;

@Data
public class LoginResponse extends SuccessResponse {

	public LoginResponse() {
		// TODO Auto-generated constructor stub
	}
	
	private String token;

}
