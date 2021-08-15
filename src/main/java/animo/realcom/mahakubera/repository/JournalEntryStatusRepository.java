package animo.realcom.mahakubera.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import animo.realcom.mahakubera.entity.JournalEntryStatus;
import animo.realcom.mahakubera.util.JournalEntryStatusCode;

@Repository
public interface JournalEntryStatusRepository extends JpaRepository<JournalEntryStatus, Integer> {

	
	Optional<JournalEntryStatus> findByJournalCode(JournalEntryStatusCode journalCode);

	List<JournalEntryStatus> findByJournalCodeIn(List<JournalEntryStatusCode> journalCode);
}
