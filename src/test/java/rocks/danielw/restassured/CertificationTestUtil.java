package rocks.danielw.restassured;

import rocks.danielw.rest.certification.CertificationDto;
import rocks.danielw.rest.certification.CertificationRequestDto;
import rocks.danielw.rest.misc.ApplicationConstants;

import java.time.LocalDate;

public class CertificationTestUtil {

  private CertificationTestUtil() {
    // has only static methods
  }

  /*--------------------CertificationRequestDto---------------------*/
  static CertificationRequestDto createTestCertificationRequestDto() {
    return createTestCertificationRequestDto("Very cool certification", LocalDate.of(2019, 1, 1));
  }

  public static CertificationRequestDto createTestCertificationRequestDto(String name, LocalDate dateOfAchievement) {
    return CertificationRequestDto.builder()
            .name(name)
            .dateOfAchievement(dateOfAchievement)
            .certificate("certification file")
            .build();
  }

  /*--------------------CertificationDto---------------------*/
  static CertificationDto createMtaCertificationDto() {
    CertificationDto certificationDto = new CertificationDto();
    certificationDto.setId(1);
    certificationDto.setName("Microsoft Technology Associate: Database Fundamentals");
    certificationDto.setDateOfAchievement(LocalDate.of(2014, 10, 1));
    certificationDto.setCertificate("certification file");
    certificationDto.setUserId(ApplicationConstants.PUBLIC_USER_ID); // only for tests

    return certificationDto;
  }

  public static CertificationDto testCertificationDto(String name, LocalDate dateOfAchievement) {
    CertificationDto certificationDto = new CertificationDto();
    certificationDto.setId(1);
    certificationDto.setName(name);
    certificationDto.setDateOfAchievement(dateOfAchievement);
    certificationDto.setCertificate("certification file");
    certificationDto.setUserId(ApplicationConstants.PUBLIC_USER_ID);

    return certificationDto;
  }

}