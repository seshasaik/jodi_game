package animo.realcom.mahakubera.entity.GameJodi;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Embeddable
@Data
@AllArgsConstructor
public class GameJodiPlayStatusId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameJodiPlayStatusId() {
		// TODO Auto-generated constructor stub
	}

	private LocalDate playDate;
	private byte sessionIndex;

}
