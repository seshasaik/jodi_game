package animo.realcom.mahakubera.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.Data;

@Data
@Entity
public class UserDetails {

	public UserDetails() {
		// TODO Auto-generated constructor stub
	}

	@Id
	private Long id;

	@OneToOne
	@MapsId
	@PrimaryKeyJoinColumn(name = "user_id")
	private User user;

	private String fistName;
	private String lastName;
	private LocalDate dateOfBirth;
	private String gender;
	private String emailId;
	private LocalDate dateOfRegister;
	private String address;
	private String remarks;
	private String gstn;
	private String pan;
	private String aadhar;

}
