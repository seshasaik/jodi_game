package animo.realcom.mahakubera.repository.gameJodi;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import animo.realcom.mahakubera.entity.Region;
import animo.realcom.mahakubera.entity.User;
import animo.realcom.mahakubera.entity.GameJodi.GameJodiTicket;
import animo.realcom.mahakubera.entity.GameJodi.GameJodiTicketTransactions;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTransactionSummary;
import animo.realcom.mahakubera.modal.gameJodi.GameJodiTicketTransactionsDTO;
import animo.realcom.mahakubera.util.GameJodiTransactionStatus;

@Repository
public interface GameJodiTicketTransactionsRepository extends JpaRepository<GameJodiTicketTransactions, Long> {

	@Query(value = "select t1 from GameJodiTicketTransactions as t1 join fetch t1.jodiTicket as t2 where t1.vendorUser = :user or t1.vendor = :user order by t1.created desc", countQuery = "select count(1) from GameJodiTicketTransactions as t1 where t1.vendorUser = :user or t1.vendor = :user")
	public Page<GameJodiTicketTransactions> findGameJodiTransactionDetails(@Param(value = "user") User user,
			Pageable pageable);

	@Query(value = "select t1 from GameJodiTicketTransactions as t1 join fetch t1.jodiTicket as t2 where t2.gameDate = :gameDate and (t1.vendorUser = :user or t1.vendor = :user)", countQuery = "select count(1) from GameJodiTicketTransactions as t1 join t1.jodiTicket as t2 where t2.gameDate = :gameDate and (t1.vendorUser = :user or t1.vendor = :user)")
	public Page<GameJodiTicketTransactions> findGameJodiTransactionDetailsByDate(
			@Param(value = "gameDate") LocalDate gameDate, @Param(value = "user") User user, Pageable pageable);

	@Query(value = "select t1 from GameJodiTicketTransactions as t1 join fetch t1.jodiTicket as t2 where t2 = :gameJodiTicket  and (t1.vendorUser = :user or t1.vendor = :user)  order by t1.created desc")
	public List<GameJodiTicketTransactions> findGameJodiTransactionDetailsByDate(
			@Param(value = "gameJodiTicket") GameJodiTicket gameJodiTicket, @Param(value = "user") User user);

	@Procedure(name = "sp_prepare_game_result2")
	public void prepareGameResult(@Param(value = "game_id") Long gameId,
			@Param(value = "company_profit_percent") Double companyProfitPercent);

	@Query(value = "select t1 from GameJodiTicketTransactions as t1 join fetch t1.jodiTicket as t2 join fetch t1.details as t3 join fetch t3.detailId.company as t4  where t1.id = :transactionId")
	public GameJodiTicketTransactions findGameJodiTransactionDetailsByGameJodi(
			@Param(value = "transactionId") Long transactionId);

	@Query(value = "select t1 from GameJodiTicketTransactions as t1 join fetch t1.jodiTicket  where t1.jodiTicket = :gameJodiTicket  and (t1.vendorUser = :user or t1.vendor = :user) and t1.status = :status  order by t1.canceled desc")
	public List<GameJodiTicketTransactions> findGameJodiCancelTransactionDetails(
			@Param(value = "gameJodiTicket") GameJodiTicket gameJodiTicket,
			@Param("status") GameJodiTransactionStatus status, @Param(value = "user") User user);
	
	@Query(value = "select t1 from GameJodiTicketTransactions as t1 join fetch t1.jodiTicket  where t1.jodiTicket = :gameJodiTicket  and (t1.vendorUser = :user or t1.vendor = :user) and t1.status = :status  order by t1.canceled desc")
	public List<GameJodiTicketTransactions> findGameJodiWinTransactionDetails(
			@Param(value = "gameJodiTicket") GameJodiTicket gameJodiTicket,
			@Param("status") GameJodiTransactionStatus status, @Param(value = "user") User user);

	@Query(name = "summarizedTransaction", nativeQuery = true)
	GameJodiTransactionSummary getGameJodiJounalEntrySummary(@Param("transactionDate") LocalDate transactionDate,
			@Param("vendorId") Integer vendorId);

}
