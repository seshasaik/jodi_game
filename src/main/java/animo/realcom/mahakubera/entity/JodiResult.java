package animo.realcom.mahakubera.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "jodi_result")
@Data

@NamedNativeQueries({
		@NamedNativeQuery(name = "JodiResult.findRecentGameDrawResult", query = "SELECT * FROM jodi_result jr WHERE jr.s_no = (SELECT s_no FROM jodi_result ORDER BY s_no DESC LIMIT 1)", resultClass = JodiResult.class) })
//@NamedStoredProcedureQueries(value = { @NamedStoredProcedureQuery(name = "", procedureName = "", parameters = {
//		@StoredProcedureParameter(mode = ParameterMode.IN, name = "", type = Integer.class) }) })
public class JodiResult {

	@Id
	@Column(name = "s_no", updatable = false)
	private Long id;

	@Column(name = "game_id")
	private String gameId;

	@Column(name = "date_time")
	private String dateTime;

	@Column(name = "start_time")
	private String startTime;

	@Column(name = "draw_time")
	private String drawTime;

	@Column(name = "company")
	private String company;

	@Column(name = "winning_number")
	private String winningNumber;

	@Column(name = "unique_id")
	private String uniqueId;

}
