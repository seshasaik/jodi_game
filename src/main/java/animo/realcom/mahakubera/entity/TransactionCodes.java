package animo.realcom.mahakubera.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "transaction_codes")
@Data
public class TransactionCodes {

	@Id
	@Column(name = "s_no")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "status_id")
	private String statusId;

	@Column(name = "status_purpose")
	private String purpose;

	@Column(name = "status_code")
	private String code;

}
