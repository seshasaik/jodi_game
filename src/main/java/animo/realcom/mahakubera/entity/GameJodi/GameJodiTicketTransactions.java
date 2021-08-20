package animo.realcom.mahakubera.entity.GameJodi;

import java.time.Instant;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.StoredProcedureParameter;

import animo.realcom.mahakubera.entity.User;
import animo.realcom.mahakubera.entity.GameJodi.attributeConverter.GameJodiTransactionClaimStatusConverter;
import animo.realcom.mahakubera.entity.GameJodi.attributeConverter.GameJodiTransactionStatusConverter;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTransactionSummary;
import animo.realcom.mahakubera.util.GameJodiTransactionStatus;
import lombok.Data;

@Data
@Entity
@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "sp_prepare_game_result", procedureName = "sp_prepare_game_result", parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "game_id"),
				@StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "win_percentage") }) })
@NamedNativeQuery(name = "summarizedTransaction", query = "select sum(total_amount) as totalAmount, sum(if(status = 'CANCEL',total_amount,0)) as totalCancelAmount, sum(if(wining_status = 'WIN',claim_amount,0)) as totalClaimAmount from game_jodi_ticket_transactions where cast(created as date) = :transactionDate and vendor_id = :vendorId", resultSetMapping = "summarizedTransactionResultSetMapping")
@SqlResultSetMapping(name = "summarizedTransactionResultSetMapping", classes = {
		@ConstructorResult(targetClass = GameJodiTransactionSummary.class, columns = {
				@ColumnResult(name = "totalAmount"), @ColumnResult(name = "totalCancelAmount"),
				@ColumnResult(name = "totalClaimAmount") }) })
public class GameJodiTicketTransactions {

	public GameJodiTicketTransactions() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String transactionId;

	@ManyToOne
	@JoinColumn(name = "ticket_id")
	private GameJodiTicket jodiTicket;

	private short totalQuantity;

	private Double totalAmount;
	@Column(updatable = false)
	private Instant created;
	@Column(insertable = false)
	private Instant canceled;
	@Convert(converter = GameJodiTransactionStatusConverter.class)
	private GameJodiTransactionStatus status = GameJodiTransactionStatus.TRANSACTION_SUCCESS;

	@Convert(converter = GameJodiTransactionStatusConverter.class)
	private GameJodiTransactionStatus winingStatus = GameJodiTransactionStatus.WINING_STATUS_HOLD;

	private Double claimAmount;

	@Column(insertable = false)
	private Instant claimed;

	@Convert(converter = GameJodiTransactionClaimStatusConverter.class)
	private GameJodiTransactionStatus claimStatus;

	@ManyToOne
	@JoinColumn(name = "vendor_id ")
	private User vendor;

	@ManyToOne
	@JoinColumn(name = "vendor_user_id")
	private User vendorUser;

	@OneToMany(mappedBy = "detailId.transaction", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<GameJodiTicketTransactionDetails> details;
}
