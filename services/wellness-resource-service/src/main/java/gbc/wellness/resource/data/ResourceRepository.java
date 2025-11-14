package gbc.wellness.resource.data;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import gbc.wellness.resource.model.WellnessResource;

public interface ResourceRepository extends JpaRepository<WellnessResource, Long> {
    List<WellnessResource> findByCategory(String category);

    // bridge to your existing repo method (ignore-case)
    List<WellnessResource> findByCategoryIgnoreCase(String category);
}
