package animo.realcom.mahakubera.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import animo.realcom.mahakubera.entity.AdminWallet;

@Repository
public interface AdminWalletRepository extends JpaRepository<AdminWallet, Long> {
	
}
