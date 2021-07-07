package animo.realcom.mahakubera.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import animo.realcom.mahakubera.entity.TransactionCodes;

@Repository
public interface TransactionCodesRepository extends JpaRepository<TransactionCodes, Long> {

	TransactionCodes findByStatusId(String statusId);
}
