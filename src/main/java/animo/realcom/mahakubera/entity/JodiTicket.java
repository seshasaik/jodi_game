package animo.realcom.mahakubera.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.experimental.Tolerate;

@Builder
@Data
@Table(name = "jodi_ticket_table_new")
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonInclude(content = com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
public class JodiTicket {

	@Tolerate
	public JodiTicket() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@Column(name = "s_no", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "reg_id")
	private String regId;

	@Column(name = "company")
	@Include
	private String company;

	@Column(name = "game_id")
	@Include
	private String gameId;

	@Column(name = "date_time")
	private LocalDateTime dateTime;

	@Column(name = "issue_time")
	private String issueTime;

	@Column(name = "ticket_id")
	private String ticketId;

	@Column(name = "amount")
	private String amount;

	@Column(name = "quantity")
	private String quantity;

	@Column(name = "draw_time")
	private String drawTime;

	@Column(name = "roles")
	private String roles;

	@Column(name = "ticket_status")
	private String ticketStatus;

	@Column(name = "winning_amount")
	private String winningAmount;

	@Column(name = "putting_number")
	private String puttingNumber;

	@Column(name = "joker_quantity")
	private String jokerQuantity;

	@Column(name = "joker_hit_count")
	private int jokerHitCount;

	@Column(name = "is_audit_record")
	private String isAuditRecord;

}
