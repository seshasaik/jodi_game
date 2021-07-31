package animo.realcom.mahakubera.entity.GameJodi;

import java.time.LocalDateTime;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class RegionCompany {

	public RegionCompany() {
		// TODO Auto-generated constructor stub
	}

	@EmbeddedId
	private RegionCompanyId regionCompanyId;

	private String name;
	private String code;
	private LocalDateTime dateOfRegister;
	private byte status;
}
