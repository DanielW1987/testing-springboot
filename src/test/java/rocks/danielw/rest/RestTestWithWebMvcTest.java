package rocks.danielw.rest;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import rocks.danielw.rest.certification.CertificationDto;
import rocks.danielw.rest.certification.CertificationService;
import rocks.danielw.rest.certification.CertificationsRestController;
import rocks.danielw.restassured.CertificationTestUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CertificationsRestController.class)
class RestTestWithWebMvcTest {

  @MockBean
  private CertificationService certificationService;

  @Autowired
  private MockMvc mockMvc;

  @AfterEach
  void tearDown() {
    reset(certificationService);
  }

  @Test
  void testGetCertificationById() throws Exception {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    CertificationDto certificationDto = CertificationTestUtil.testCertificationDto("MTA", LocalDate.of(2016,1,1));

    when(certificationService.find(anyLong())).thenReturn(Optional.of(certificationDto));

    MvcResult result = mockMvc.perform(get("/rest/v1/certifications/1").accept(MediaType.APPLICATION_JSON)) // the accept header is mandatory
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.id", is((int) certificationDto.getId()))) // jsonPath returns int on id field
            .andExpect(jsonPath("$.name", is(certificationDto.getName())))
            .andExpect(jsonPath("$.certificate", is(certificationDto.getCertificate())))
            .andExpect(jsonPath("$.dateOfAchievement", is(formatter.format(certificationDto.getDateOfAchievement()))))
            .andExpect(jsonPath("$.userId", is(certificationDto.getUserId())))
            .andReturn();

    System.out.println(result.getResponse().getContentAsString());
  }

  @DisplayName("List Operation")
  @Nested
  class TestListOperations {

    @Test
    void testGetAllCertifications() throws Exception {
      CertificationDto mta = CertificationTestUtil.testCertificationDto("MTA", LocalDate.of(2016,1,1));
      CertificationDto oca = CertificationTestUtil.testCertificationDto("OCA", LocalDate.of(2017,6,15));
      CertificationDto ocp = CertificationTestUtil.testCertificationDto("OCP", LocalDate.of(2018,11,19));

      when(certificationService.findAll()).thenReturn(List.of(mta, oca, ocp));

      mockMvc.perform(get("/rest/v1/certifications/list").accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
              .andExpect(jsonPath("$.content", hasSize(3)))
              .andExpect(jsonPath("$.content[0].name", is(mta.getName())))
              .andExpect(jsonPath("$.content[1].name", is(oca.getName())))
              .andExpect(jsonPath("$.content[2].name", is(ocp.getName())));
    }

  }

}
