package animo.realcom.mahakubera.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import animo.realcom.mahakubera.entity.Country;
import animo.realcom.mahakubera.entity.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {
	@Query(value = "select region from Region region where region.country = :country and country.status = 1")
	List<Region> findByCountryAndStatusIsActive(@Param("country") Country country);
}
