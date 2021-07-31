package animo.realcom.mahakubera.repository.gameJodi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import animo.realcom.mahakubera.entity.GameJodi.GameJodiTticketTransactions;

@Repository
public interface GameJodiTticketTransactionsRepository extends JpaRepository<GameJodiTticketTransactions, Long> {

}
