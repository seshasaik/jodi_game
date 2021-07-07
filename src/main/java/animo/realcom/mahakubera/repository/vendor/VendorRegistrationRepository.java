package animo.realcom.mahakubera.repository.vendor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import animo.realcom.mahakubera.entity.VendorRegistration;

@Repository
public interface VendorRegistrationRepository extends JpaRepository<VendorRegistration, Long> {

	VendorRegistration findByVendorEmail(String vendorEmail);
}
