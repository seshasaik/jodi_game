package animo.realcom.mahakubera.repository.gameJodi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import animo.realcom.mahakubera.entity.GameJodi.GameJodiTimings;

@Repository
public interface GameJodiTimingsRepository extends JpaRepository<GameJodiTimings, Short> {

}
