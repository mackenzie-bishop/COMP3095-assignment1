package gbc.wellness.goal.repo;
import gbc.wellness.goal.model.Goal;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface GoalRepo extends MongoRepository<Goal, String> {
  List<Goal> findByStatusIgnoreCase(String status);
  List<Goal> findByCategoryIgnoreCase(String category);
}
