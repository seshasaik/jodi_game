package animo.realcom.mahakubera.entity.GameJodi;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class GameJodiTimings {

	public GameJodiTimings() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Short id;
	private String name;
	private LocalTime startTime;
	private LocalTime endTime;
	private byte duration;
	private String durationUnit;
	private String description;
	private LocalDateTime created;
	private LocalDateTime updated;
	private boolean status;

}
