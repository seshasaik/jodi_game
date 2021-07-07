package animo.realcom.mahakubera.entity;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "jodi_companies")
public class JodiCompanies {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "company_id")
	private Long id;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "company_code")
	private String companyCode;

	@Column(name = "close_company_code")
	private String closeCompanyCode;

	@Column(name = "show_id")
	private String showId;

	@Column(name = "company_ticket_price")
	private String companyTicketPrice;

	@Column(name = "openclose_company_ticket_price")
	private String openCloseCompanyTicketPrice;

	@Column(name = "winning_price")
	private String winningPrice;

	@Column(name = "openclose_winning_price")
	private String openCloseWinningPrice;

	@Column(name = "date_time")
	private LocalTime dateTime;

	@Column(name = "winning_percentage")
	private String winningPercentage;

}
