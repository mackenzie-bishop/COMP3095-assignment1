package gbc.wellness.event.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name="events")
public class Event {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  @Column(length=2000)
  private String description;
  private LocalDate date;
  private String location;
  private Integer capacity;
  private Integer registeredStudents;
}
