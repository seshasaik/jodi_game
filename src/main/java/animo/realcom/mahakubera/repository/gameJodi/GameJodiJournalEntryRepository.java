package animo.realcom.mahakubera.repository.gameJodi;

import java.time.Instant;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import animo.realcom.mahakubera.entity.User;
import animo.realcom.mahakubera.entity.GameJodi.GameJodiJournalEntry;

@Repository
public interface GameJodiJournalEntryRepository extends JpaRepository<GameJodiJournalEntry, Long> {

	@Query(value = "select journal from GameJodiJournalEntry as journal join fetch journal.gameJodiTicket as jodiTicket join fetch journal.journalEntry as entry where journal.created between :jounalEntryStart and :jounalEntryEnd and journal.vendor = :vendor", countQuery = "select count(1) from GameJodiJournalEntry as journal where journal.created between :jounalEntryStart and :jounalEntryEnd  and journal.vendor = :vendor")
	Page<GameJodiJournalEntry> getJodiJournalEntry(@Param("jounalEntryStart") Instant jounalEntryStart,
			@Param("jounalEntryEnd") Instant jounalEntryEnd, @Param("vendor") User vendor, Pageable page);
}
