package gbc.wellness.resource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ResourceServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(ResourceServiceApplication.class, args);
  }
}
