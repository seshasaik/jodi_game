package animo.realcom.mahakubera.repository.gameJodi;

import java.time.LocalDate;
import java.util.Optional;

import animo.realcom.mahakubera.entity.GameJodi.GameJodiPlayStatus;

public interface CustomGameJodiPlayStatusRepository {

	Optional<GameJodiPlayStatus> findRecentGameByPlayDate(LocalDate date);
}
