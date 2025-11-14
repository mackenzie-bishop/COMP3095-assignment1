package gbc.wellness.goal;
import gbc.wellness.goal.model.Goal;
import gbc.wellness.goal.repo.GoalRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GoalServiceIT {
  static MongoDBContainer mongo = new MongoDBContainer("mongo:7");
  static { mongo.start(); }

  @DynamicPropertySource
  static void ds(DynamicPropertyRegistry r){
    r.add("spring.data.mongodb.uri", mongo::getConnectionString);
  }

  @Autowired GoalRepo repo;

  @Test
  void savesAndFinds(){
    Goal g = Goal.builder().title("Meditate").description("Daily").category("mindfulness").status("in-progress").build();
    repo.save(g);
    assertThat(repo.findByCategoryIgnoreCase("MINDFULNESS")).hasSize(1);
  }
}
