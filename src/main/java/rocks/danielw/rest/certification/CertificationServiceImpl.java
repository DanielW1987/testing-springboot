package rocks.danielw.rest.certification;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rocks.danielw.rest.misc.ApplicationConstants;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CertificationServiceImpl implements CertificationService {

  private final CertificationRepository certificationRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public CertificationServiceImpl(CertificationRepository certificationRepository) {
    this.certificationRepository = certificationRepository;
    this.modelMapper             = new ModelMapper();
  }

  @Override
  public List<CertificationDto> findAll() {
    Sort sort = new Sort(Sort.Direction.DESC, "dateOfAchievement");

    return certificationRepository.findAll(sort)
            .stream()
            .map(certification -> modelMapper.map(certification, CertificationDto.class))
            .collect(Collectors.toList());
  }

  @Override
  public Optional<CertificationDto> find(long id) {
    Optional<Certification> certification = certificationRepository.findById(id);

    return certification.map(value -> modelMapper.map(value, CertificationDto.class));
  }

  @Override
  public Optional<CertificationDto> findByName(String certificationName) {
    return certificationRepository.findAll()
            .stream()
            .filter(certification -> certification.getName().equalsIgnoreCase(certificationName))
            .map(certification -> modelMapper.map(certification, CertificationDto.class))
            .findFirst();
  }

  @Override
  public CertificationDto create(CertificationRequestDto request) {
    Certification certification = modelMapper.map(request, Certification.class);
    certification.setUserId(ApplicationConstants.PUBLIC_USER_ID); // only for tests
    certificationRepository.save(certification);

    return modelMapper.map(certification, CertificationDto.class);
  }

  @Override
  public Optional<CertificationDto> update(long id, CertificationRequestDto request) {
    Optional<Certification> certificationOptional = certificationRepository.findById(id);
    CertificationDto certificationResponse        = null;

    if (certificationOptional.isPresent()) {
      Certification certification = certificationOptional.get();
      modelMapper.map(request, certification);

      certificationRepository.save(certification);
      certificationResponse = modelMapper.map(certification, CertificationDto.class);
    }

    return Optional.ofNullable(certificationResponse);
  }

  @Override
  public boolean delete(long id) {
    if (certificationRepository.existsById(id)) {
      certificationRepository.deleteById(id);
      return true;
    }

    // entity doesn't exist
    return false;
  }

}
