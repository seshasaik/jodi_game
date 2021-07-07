package animo.realcom.mahakubera.modal;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private String gender;
	private String emailId;
	private String password;
	private String contactNumber;
	private String country;
	private String state;
	private String city;
	private String pincode;
	private String userStatus;
	private String role;
	private String userOtp;
	private LocalDateTime dateOfRegister;
	private String remarks;
	private String refer;

}
