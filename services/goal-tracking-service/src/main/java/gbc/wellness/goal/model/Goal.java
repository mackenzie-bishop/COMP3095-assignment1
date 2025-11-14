package gbc.wellness.goal.model;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Goal {
  @Id
  private String id;
  private String title;
  private String description;
  private String category; // align to resource categories
  private String status; // in-progress | completed
  private String targetDate; // ISO string, optional
}
