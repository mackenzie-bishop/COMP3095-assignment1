package gbc.wellness.event.web;
import gbc.wellness.event.model.Event;
import gbc.wellness.event.repo.EventRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
public class EventController {
  private final EventRepo repo;
  public EventController(EventRepo repo){ this.repo = repo; }

  @GetMapping("/events")
  public List<Event> list(@RequestParam(required=false) String date,
                          @RequestParam(required=false) String location){
    if (date != null) return repo.findByDate(LocalDate.parse(date));
    if (location != null) return repo.findByLocationIgnoreCase(location);
    return repo.findAll();
  }

  @PostMapping("/events")
  public Event create(@RequestBody Event e){
    if (e.getRegisteredStudents()==null) e.setRegisteredStudents(0);
    return repo.save(e);
  }

  @PutMapping("/events/{id}")
  public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Event e){
    return repo.findById(id).map(db -> {
      db.setTitle(e.getTitle()); db.setDescription(e.getDescription());
      db.setDate(e.getDate()); db.setLocation(e.getLocation());
      db.setCapacity(e.getCapacity());
      return ResponseEntity.ok(repo.save(db));
    }).orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/events/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id){
    if (!repo.existsById(id)) return ResponseEntity.notFound().build();
    repo.deleteById(id); return ResponseEntity.noContent().build();
  }

  @PostMapping("/events/{id}/register")
  public ResponseEntity<?> register(@PathVariable Long id){
    return repo.findById(id).map(e -> {
      if (e.getCapacity()!=null && e.getRegisteredStudents()!=null &&
          e.getRegisteredStudents() >= e.getCapacity()) {
        return ResponseEntity.badRequest().build();
      }
      e.setRegisteredStudents((e.getRegisteredStudents()==null?0:e.getRegisteredStudents()) + 1);
      return ResponseEntity.ok(repo.save(e));
    }).orElse(ResponseEntity.notFound().build());
  }
}
