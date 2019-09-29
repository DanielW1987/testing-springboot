package rocks.danielw.rest.certification;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface CertificationService {

  List<CertificationDto> findAll();

  Optional<CertificationDto> find(long id);

  Optional<CertificationDto> findByName(String certificationName);

  CertificationDto create(@NotNull CertificationRequestDto request);

  Optional<CertificationDto> update(long id, @NotNull CertificationRequestDto request);

  boolean delete(long id);

}
