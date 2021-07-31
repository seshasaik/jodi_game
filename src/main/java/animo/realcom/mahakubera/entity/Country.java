package animo.realcom.mahakubera.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Country {

	public Country() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String name;
	private String shortCode;

	private byte status;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
	private List<Region> regions;
	

}
