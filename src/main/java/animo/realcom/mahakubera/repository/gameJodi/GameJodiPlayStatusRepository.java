package animo.realcom.mahakubera.repository.gameJodi;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import animo.realcom.mahakubera.entity.GameJodi.GameJodiPlayStatus;
import animo.realcom.mahakubera.entity.GameJodi.GameJodiPlayStatusId;
import animo.realcom.mahakubera.util.GameJodiStatus;

@Repository
public interface GameJodiPlayStatusRepository extends JpaRepository<GameJodiPlayStatus, GameJodiPlayStatusId> {

	Optional<GameJodiPlayStatus> findTop1ByGameJodiPlayStatusId_PlayDateAndGameStatusIn(LocalDate playDate,
			List<GameJodiStatus> gameStatus, Sort sort);
}
