package animo.realcom.mahakubera.entity.GameJodi;

import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import animo.realcom.mahakubera.entity.GameJodi.attributeConverter.GameJodiStatusConverter;
import animo.realcom.mahakubera.util.GameJodiStatus;
import lombok.Data;

@Data
@Entity
public class GameJodiPlayStatus {

	public GameJodiPlayStatus() {
		// TODO Auto-generated constructor stub
	}

	@EmbeddedId
	private GameJodiPlayStatusId gameJodiPlayStatusId;

	private long recentPlay;
	private long currentPlay;

	@ManyToOne
	@JoinColumn(name = "play_session_time")
	private GameJodiTimings playSessionTime;

	@Convert(converter = GameJodiStatusConverter.class)
	private GameJodiStatus gameStatus = GameJodiStatus.YET_TO_START;

}
