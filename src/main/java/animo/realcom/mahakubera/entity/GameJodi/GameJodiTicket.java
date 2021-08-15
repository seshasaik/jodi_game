package animo.realcom.mahakubera.entity.GameJodi;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
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
	private LocalDate gameDate = LocalDate.now(ZoneOffset.UTC);
	private short ticketIndex;
	@Column(updatable = false)
	private Instant created;
	@Column(insertable = false)
	private Instant updated;
	@Convert(converter = GameJodiStatusConverter.class)
	private GameJodiStatus gameStatus = GameJodiStatus.YET_TO_START;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "game_time")
	private GameJodiTimings gameJodiTime;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ticketCompany.ticket", orphanRemoval = true)
	private List<GameJodiTicketCompany> companies;

}
