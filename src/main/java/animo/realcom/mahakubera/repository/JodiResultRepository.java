package animo.realcom.mahakubera.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import animo.realcom.mahakubera.entity.JodiResult;

@Repository
public interface JodiResultRepository extends JpaRepository<JodiResult, Long> {

	List<JodiResult> findRecentGameDrawResult();
}
