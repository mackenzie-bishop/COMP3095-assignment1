package gbc.wellness.resource.model;
import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WellnessResource {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  @Column(length=2000)
  private String description;
  private String category; // e.g. counseling, mindfulness
  private String url;
}
