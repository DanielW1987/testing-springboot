package rocks.danielw.rest.certification;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CertificationDto {

  private long id;
  private String name;
  private LocalDate dateOfAchievement;
  private String certificate;
  private String userId;

}
