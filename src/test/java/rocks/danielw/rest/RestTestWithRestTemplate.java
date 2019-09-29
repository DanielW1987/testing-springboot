package rocks.danielw.rest;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import rocks.danielw.rest.certification.CertificationDto;
import rocks.danielw.rest.certification.CertificationService;
import rocks.danielw.rest.misc.ApplicationConstants;
import rocks.danielw.restassured.CertificationTestUtil;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // necessary for tests with TestRestTemplate
class RestTestWithRestTemplate implements WithAssertions {

  @Autowired
  private TestRestTemplate restTemplate;

  @MockBean
  private CertificationService certificationService;

  @AfterEach
  void tearDown() {
    reset(certificationService);
  }

  @Test
  void testGetCertificationById() {
    CertificationDto certificationDto = CertificationTestUtil.testCertificationDto("MTA", LocalDate.of(2016,1,1));

    when(certificationService.find(anyLong())).thenReturn(Optional.of(certificationDto));

    CertificationDto certificationResult = restTemplate.getForObject("/rest/v1/certifications/1", CertificationDto.class);

    assertThat(certificationResult.getId()).isEqualTo(1L);
    assertThat(certificationResult.getName()).isEqualTo("MTA");
    assertThat(certificationResult.getCertificate()).isEqualTo("certification file");
    assertThat(certificationResult.getDateOfAchievement()).isEqualTo(LocalDate.of(2016,1,1));
    assertThat(certificationResult.getUserId()).isEqualTo(ApplicationConstants.PUBLIC_USER_ID);
  }

}
