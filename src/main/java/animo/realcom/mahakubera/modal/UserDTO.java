package animo.realcom.mahakubera.modal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(value = Include.NON_NULL)
public class UserDTO {

	private Integer id;
	@NotBlank(message = "user name should not be blank")
	private String userName;
	@NotBlank(message = "first name should not be blank")
	private String firstName;
	@NotBlank(message = "last name should not be blank")
	private String lastName;
	@NotNull(message = "date of birth should not be empty")
	private LocalDate dateOfBirth;
	@NotBlank(message = "gender should not be blank")
	@Pattern(regexp = "[MF]", message = "gender either M[ale] or F[emale] only")
	private String gender;
	@NotBlank(message = "email id should not be blank")
	@Email
	private String emailId;
	private String password;

	@NotBlank(message = "email id should not be blank")
	@Pattern(regexp = "//d+{10}", message = "Invalid mobile number it should be 10 digits length")
	private String mobile;
	@NotBlank(message = "address should not be blank")
	private String address;

	private String userStatus;
	@NotNull
	private List<RoleDTO> roleList;
	private LocalDateTime dateOfRegister;
	@NotNull
	private RegionDTO region;
	@NotBlank(message = "gstn should not be blank")
	private String gstn;
	@NotBlank(message = "pan should not be blank")
	private String pan;
	@NotBlank(message = "aadhar number should not be blank")
	private String aadhar;

	private String remarks;

}
