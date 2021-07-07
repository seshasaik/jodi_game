package animo.realcom.mahakubera.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import animo.realcom.mahakubera.entity.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

	Wallet findByRegIdAndRole(String regId, String role);
}
