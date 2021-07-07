package animo.realcom.mahakubera.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "admin_wallet")
@Data
public class AdminWallet {

	@Id
	@Column(name = "s_no")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "reg_id")
	private String regId;

	@Column(name = "role")
	private String role;

	@Column(name = "amount")
	private String amount;

}
