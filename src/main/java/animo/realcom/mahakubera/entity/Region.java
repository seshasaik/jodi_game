package animo.realcom.mahakubera.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import animo.realcom.mahakubera.entity.GameJodi.RegionCompany;
import lombok.Data;

@Data
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "short_code" }) })
public class Region {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	@Column(name = "short_code")
	private String shortCode;

	private byte status;

	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country country;

	@OneToMany(mappedBy = "regionCompanyId.region", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.REMOVE)
	private List<RegionCompany> companies;

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.REMOVE, mappedBy = "region")
	private List<User> users;

	public Region() {
		// TODO Auto-generated constructor stub
	}

}
