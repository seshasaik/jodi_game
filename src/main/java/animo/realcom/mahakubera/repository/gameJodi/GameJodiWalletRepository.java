package animo.realcom.mahakubera.repository.gameJodi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import animo.realcom.mahakubera.entity.User;
import animo.realcom.mahakubera.entity.GameJodi.GameJodiWallet;

@Repository
public interface GameJodiWalletRepository extends JpaRepository<GameJodiWallet, Integer> {

	GameJodiWallet findByUser(User user);
}
