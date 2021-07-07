package animo.realcom.mahakubera.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import animo.realcom.mahakubera.entity.JodiCompanies;

@Repository
public interface JodiCompanyRepository extends JpaRepository<JodiCompanies, Long> {

	@Query("from JodiCompanies where companyCode=:companyCode or concat(companyCode,closeCompanyCode)=:companyCode")
	JodiCompanies findbyCompanyCode(@Param("companyCode") String companyCode);

	@Query("from JodiCompanies where companyCode in (:companyCode) or concat(companyCode,closeCompanyCode) in(:companyCode)")
	List<JodiCompanies> findbyCompanyCode(@Param("companyCode") List<String> companyCode);
}
