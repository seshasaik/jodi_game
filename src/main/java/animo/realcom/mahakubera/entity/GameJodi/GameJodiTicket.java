package animo.realcom.mahakubera.entity.GameJodi;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
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

import animo.realcom.mahakubera.entity.GameJodi.attributeConverter.GameJodiStatusConverter;
import animo.realcom.mahakubera.util.GameJodiStatus;
import lombok.Data;

@Data
@Entity()
public class GameJodiTicket {

	public GameJodiTicket() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String ticketNo;
	private LocalTime startTime;
	private LocalTime endTime;
	private short ticketIndex;
	@Column(updatable = false)
	private Instant created;
	@Column(insertable = false)
	private Instant updated;
	@Convert(converter = GameJodiStatusConverter.class)
	private GameJodiStatus gameStatus = GameJodiStatus.YET_TO_START;
	
	@ManyToOne
	@JoinColumn(name = "game_time")
	private GameJodiTimings gameJodiTime;

}
