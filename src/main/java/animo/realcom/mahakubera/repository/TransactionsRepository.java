package animo.realcom.mahakubera.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import animo.realcom.mahakubera.entity.Transactions;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {

	@Procedure(name = "Transactions.save_transactions_slove")
	void saveTransaction(@Param("regId") Long regId, @Param("Role") String role, @Param("credit") String credit,
			@Param("debit") String debit, @Param("total") String total, @Param("transactionId") String transactionId,
			@Param("purpose") String purpose, @Param("dateTime") LocalDateTime dateTime,
			@Param("transactioncode") String transactioncode, @Param("uniqueId") String uniqueId,
			@Param("vsts") Integer vsts, @Param("vprofit") String vprofit, @Param("vtotal") String vtotal,
			@Param("vuniqueId") String vuniqueId);

}
