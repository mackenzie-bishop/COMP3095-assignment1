package gbc.wellness.goal.web;

import gbc.wellness.goal.model.Goal;
import gbc.wellness.goal.repo.GoalRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
public class GoalController {
  private final GoalRepo repo;
  private final RestTemplate http = new RestTemplate();

  @Value("${app.resourcesBaseUrl:http://localhost:8081}")
  private String resourcesBaseUrl;

  public GoalController(GoalRepo repo){ this.repo = repo; }

  @GetMapping("/goals")
  public List<Goal> list(@RequestParam(required=false) String status,
                         @RequestParam(required=false) String category){
    if (status != null) return repo.findByStatusIgnoreCase(status);
    if (category != null) return repo.findByCategoryIgnoreCase(category);
    return repo.findAll();
  }

  @PostMapping("/goals")
  public Goal create(@RequestBody Goal g){
    if (g.getStatus()==null || g.getStatus().isBlank()) g.setStatus("in-progress");
    return repo.save(g);
  }

  @PutMapping("/goals/{id}")
  public ResponseEntity<Goal> update(@PathVariable("id") String id, @RequestBody Goal g){
    return repo.findById(id).map(db -> {
      db.setTitle(g.getTitle());
      db.setDescription(g.getDescription());
      db.setCategory(g.getCategory());
      db.setTargetDate(g.getTargetDate());
      db.setStatus(g.getStatus());
      return ResponseEntity.ok(repo.save(db));
    }).orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/goals/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") String id){
    if (!repo.existsById(id)) return ResponseEntity.notFound().build();
    repo.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/goals/{id}/complete")
  public ResponseEntity<Goal> complete(@PathVariable("id") String id){
    return repo.findById(id).map(db -> {
      db.setStatus("completed");
      return ResponseEntity.ok(repo.save(db));
    }).orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/goals/{id}/suggested-resources")
  public ResponseEntity<Map<String,Object>> suggest(@PathVariable("id") String id){
    return repo.findById(id).map(db -> {
      try {
        String category = db.getCategory() == null ? "" : db.getCategory();
        String url = resourcesBaseUrl + "/resources?category=" +
                     URLEncoder.encode(category, StandardCharsets.UTF_8);
        Object body = http.getForObject(url, Object.class); // could be List or []
        return ResponseEntity.ok(
          Map.of("goal", db, "resources", body == null ? List.of() : body)
        );
      } catch (Exception ex) {
        // Don’t 500 your demo — return the goal with an empty resources list and error info
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(
          Map.of("goal", db,
                 "resources", List.of(),
                 "error", ex.getClass().getSimpleName(),
                 "message", ex.getMessage())
        );
      }
    }).orElse(ResponseEntity.notFound().build());
  }
}

