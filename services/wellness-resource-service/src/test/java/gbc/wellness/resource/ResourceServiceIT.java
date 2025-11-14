package gbc.wellness.resource;
import gbc.wellness.resource.model.WellnessResource;
import gbc.wellness.resource.repo.WellnessResourceRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ResourceServiceIT {
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
      .withDatabaseName("testdb").withUsername("app").withPassword("app");

  static {
    postgres.start();
  }

  @DynamicPropertySource
  static void ds(DynamicPropertyRegistry r){
    r.add("spring.datasource.url", postgres::getJdbcUrl);
    r.add("spring.datasource.username", postgres::getUsername);
    r.add("spring.datasource.password", postgres::getPassword);
    r.add("spring.jpa.hibernate.ddl-auto", () -> "update");
    r.add("spring.cache.type", () -> "none"); // disable cache in tests
  }

  @Autowired WellnessResourceRepo repo;

  @Test
  void savesAndReads(){
    WellnessResource wr = WellnessResource.builder().title("T").description("D").category("mindfulness").url("u").build();
    repo.save(wr);
    assertThat(repo.findByCategoryIgnoreCase("MINDFULNESS")).hasSize(1);
  }
}
