package animo.realcom.mahakubera.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "user_registration")
@Data
public class UserRegistration {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "reg_id")
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

	@Column(name = "gender")
	private String gender;

	@Column(name = "email_id", unique = true)
	private String emailId;

	@Column(name = "password")
	private String password;

	@Column(name = "contact_number", unique = true)
	private String contactNumber;

	@Column(name = "country")
	private String country;

	@Column(name = "state")
	private String state;

	@Column(name = "city")
	private String city;

	@Column(name = "pincode")
	private String pincode;

	@Column(name = "user_status")
	private String userStatus;

	@Column(name = "role")
	private String role;

	@Column(name = "user_otp")
	private String userOtp;

	@Column(name = "date_of_register")
	private LocalDateTime dateOfRegister;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "refer_code")
	private String refer;

}
