package rocks.danielw.rest.misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import rocks.danielw.rest.certification.CertificationRequestDto;
import rocks.danielw.rest.certification.CertificationService;

import java.time.LocalDate;

@Component
@Profile({"dev", "integration-test"})
public class DevelopmentDbInitializer implements ApplicationRunner {

  private final CertificationService certificationService;

  @Autowired
  public DevelopmentDbInitializer(CertificationService certificationService) {
    this.certificationService = certificationService;
  }

  @Override
  public void run(ApplicationArguments applicationArguments) {
    createTestCertifications();
  }

  private void createTestCertifications() {
    CertificationRequestDto mta = CertificationRequestDto.builder()
            .name("Microsoft Technology Associate: Database Fundamentals")
            .dateOfAchievement(LocalDate.of(2014, 10, 1))
            .certificate("certification file")
            .build();

    CertificationRequestDto oca = CertificationRequestDto.builder()
            .name("Oracle Certified Associate, Java SE 8 Programmer I")
            .dateOfAchievement(LocalDate.of(2017, 7, 1))
            .certificate("certification file")
            .build();

    CertificationRequestDto ocp = CertificationRequestDto.builder()
            .name("Oracle Certified Professional, Java SE 8 Programmer II")
            .dateOfAchievement(LocalDate.of(2018, 3, 1))
            .certificate("certification file")
            .build();

    certificationService.create(mta);
    certificationService.create(oca);
    certificationService.create(ocp);
  }

}
