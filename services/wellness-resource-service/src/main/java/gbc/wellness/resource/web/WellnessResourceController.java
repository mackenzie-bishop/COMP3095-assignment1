package gbc.wellness.resource.web;

import gbc.wellness.resource.model.WellnessResource;
import gbc.wellness.resource.repo.WellnessResourceRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class WellnessResourceController {

  private final WellnessResourceRepo repo;

  public WellnessResourceController(WellnessResourceRepo repo) { // <-- correct type/casing
    this.repo = repo;
  }

  @PostMapping("/resources")
  public ResponseEntity<WellnessResource> create(@RequestBody WellnessResource r) {
    return ResponseEntity.ok(repo.save(r));
  }

  @GetMapping("/resources")
  public ResponseEntity<List<WellnessResource>> list(
      @RequestParam(name = "category", required = false) String category) {
    List<WellnessResource> out = (category != null && !category.isBlank())
        ? repo.findByCategoryIgnoreCase(category)
        : repo.findAll();
    return ResponseEntity.ok(out); // always JSON array (possibly [])
  }

  @GetMapping("/resources/{id}")
  public ResponseEntity<WellnessResource> getOne(@PathVariable("id") Long id) {
    return repo.findById(id).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PatchMapping("/resources/{id}")
  public ResponseEntity<WellnessResource> patch(@PathVariable("id") Long id,
                                                @RequestBody Map<String, Object> body) {
    return repo.findById(id).map(db -> {
      if (body.containsKey("title")) db.setTitle((String) body.get("title"));
      if (body.containsKey("description")) db.setDescription((String) body.get("description"));
      if (body.containsKey("category")) db.setCategory((String) body.get("category"));
      if (body.containsKey("url")) db.setUrl((String) body.get("url"));
      return ResponseEntity.ok(repo.save(db));
    }).orElse(ResponseEntity.notFound().build());
  }
}
