package animo.realcom.mahakubera.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name = "transaction_wise")
@Data
@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "Transactions.save_transactions_slove", procedureName = "save_transactions_slove", parameters = {
				@StoredProcedureParameter(name = "regId", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name = "Role", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "credit", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "debit", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "total", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "transactionId", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "purpose", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "dateTime", mode = ParameterMode.IN, type = LocalDateTime.class),
				@StoredProcedureParameter(name = "transactioncode", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "uniqueId", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "vsts", mode = ParameterMode.IN, type = Integer.class),
				@StoredProcedureParameter(name = "vprofit", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "vtotal", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "vuniqueId", mode = ParameterMode.IN, type = String.class) }, resultSetMappings = {}) })
public class Transactions {

	@Id
	@Column(name = "trans_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "reg_id")
	private String regId;

	@Column(name = "roles")
	private String role; 

	@Column(name = "credit")
	private String credit;

	@Column(name = "debit")
	private String debit;

	@Column(name = "transaction_id")
	private String transactionId;

	@Column(name = "purpose")
	private String purpose;

	@Column(name = "total")
	private String total;

	@Column(name = "date_time")
//	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime dateTime;

	@Column(name = "transaction_codes")
	private String transactionCodes;

	@Column(name = "status")
	private String status;

	@Column(name = "unique_id", unique = true)
	private String uniqueId;

	@Column(name = "pending")
	private String pending;

}
