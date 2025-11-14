package gbc.wellness.event.repo;
import gbc.wellness.event.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface EventRepo extends JpaRepository<Event, Long> {
  List<Event> findByDate(LocalDate date);
  List<Event> findByLocationIgnoreCase(String location);
}
