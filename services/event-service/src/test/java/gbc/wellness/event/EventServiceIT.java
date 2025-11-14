package gbc.wellness.event;
import gbc.wellness.event.model.Event;
import gbc.wellness.event.repo.EventRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class EventServiceIT {
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
      .withDatabaseName("testdb").withUsername("app").withPassword("app");
  static { postgres.start(); }

  @DynamicPropertySource
  static void ds(DynamicPropertyRegistry r){
    r.add("spring.datasource.url", postgres::getJdbcUrl);
    r.add("spring.datasource.username", postgres::getUsername);
    r.add("spring.datasource.password", postgres::getPassword);
    r.add("spring.jpa.hibernate.ddl-auto", () -> "update");
  }

  @Autowired EventRepo repo;

  @Test
  void savesAndQueries(){
    Event ev = Event.builder().title("Yoga").description("Morning").date(LocalDate.now())
        .location("Campus").capacity(10).registeredStudents(0).build();
    repo.save(ev);
    assertThat(repo.findByLocationIgnoreCase("campus")).hasSize(1);
  }
}
