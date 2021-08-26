package animo.realcom.mahakubera.entity.GameJodi;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import animo.realcom.mahakubera.entity.User;
import lombok.Data;

@Data
@Entity
public class GameJodiVendorProfitSettings {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Double percentage;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

}
