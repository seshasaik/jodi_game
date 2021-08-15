package animo.realcom.mahakubera.modal;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(value = Include.NON_NULL)
public class UserDTO {

	private Integer id;	
	private String userName;
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
