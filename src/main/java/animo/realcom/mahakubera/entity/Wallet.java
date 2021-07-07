package animo.realcom.mahakubera.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "wallet")
@Data
public class Wallet {

	@Id
	@Column(name = "wallet_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "reg_id")
	private String regId;

	@Column(name = "role")
	private String role;

	@Column(name = "amount")
	private String amount;

	@Column(name = "date_time")
	private String dateTime;

	@Column(name = "walletamt")
	private String walletAmount;

	@Column(name = "bankamount")
	private String bankAmount;
}
