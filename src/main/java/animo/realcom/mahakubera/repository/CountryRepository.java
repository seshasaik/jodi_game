package animo.realcom.mahakubera.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import animo.realcom.mahakubera.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

}
