package gbc.wellness.resource.repo;
import gbc.wellness.resource.model.WellnessResource;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WellnessResourceRepo extends JpaRepository<WellnessResource, Long> {
  List<WellnessResource> findByCategoryIgnoreCase(String category);
}
