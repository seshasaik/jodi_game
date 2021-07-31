package animo.realcom.mahakubera.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import animo.realcom.mahakubera.entity.GameJodi.GameJodiTicketCompany;
import animo.realcom.mahakubera.entity.GameJodi.RegionCompany;
import lombok.Data;

@Data
@Entity
public class Company {

	public Company() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String name;
	private String code;
	private LocalDateTime dateOfRegister;
	private BigDecimal ticketRate;
	private Short maxQuantity;
	private BigDecimal winningPrizeAmount;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "company")
	private List<GameJodiTicketCompany> gameJodiTicketes;

	@OneToMany(mappedBy = "regionCompanyId.company", fetch = FetchType.LAZY)
	private List<RegionCompany> regions;

	private byte status;
}
