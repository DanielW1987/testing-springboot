package rocks.danielw.rest.certification;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity(name = "Certification")
@Table(name = "certification")
public class Certification implements Serializable {

  public static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private LocalDate dateOfAchievement;

  private String certificate;

  @Column(nullable = false)
  private String userId;

}
