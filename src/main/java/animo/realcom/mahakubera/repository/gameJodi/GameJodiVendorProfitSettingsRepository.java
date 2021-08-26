package animo.realcom.mahakubera.repository.gameJodi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import animo.realcom.mahakubera.entity.User;
import animo.realcom.mahakubera.entity.GameJodi.GameJodiVendorProfitSettings;

@Repository
public interface GameJodiVendorProfitSettingsRepository extends JpaRepository<GameJodiVendorProfitSettings, Integer> {
	GameJodiVendorProfitSettings findByUser(User user);
}
