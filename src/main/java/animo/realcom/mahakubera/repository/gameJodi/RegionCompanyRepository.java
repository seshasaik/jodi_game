package animo.realcom.mahakubera.repository.gameJodi;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import animo.realcom.mahakubera.entity.Region;
import animo.realcom.mahakubera.entity.GameJodi.RegionCompany;
import animo.realcom.mahakubera.entity.GameJodi.RegionCompanyId;

@Repository
public interface RegionCompanyRepository extends JpaRepository<RegionCompany, RegionCompanyId> {
	List<RegionCompany> findAllByRegionCompanyId_Region(Region region);

	@Query(value = "select regComp from RegionCompany as regComp join fetch regComp.regionCompanyId.company where regComp.regionCompanyId.region = :region")
	List<RegionCompany> findAllByRegion(@Param("region") Region region);
}
