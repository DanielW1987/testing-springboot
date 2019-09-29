package rocks.danielw.rest.certification;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
public class CertificationRequestDto {

  @NotBlank
  private String name;

  @NotNull
  private LocalDate dateOfAchievement;

  private String certificate;

  public Map<String, String> toMap() {
    Map<String, String> map = new HashMap<>();
    map.put("name", name);
    map.put("dateOfAchievement", dateOfAchievement.toString());
    map.put("certificate", certificate);

    return map;
  }

}
