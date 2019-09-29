package rocks.danielw.rest.certification;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class CertificationList extends PageImpl<CertificationDto> {

  public CertificationList(List<CertificationDto> certifications, Pageable pageable, long total) {
    super(certifications, pageable, total);
  }

}
