package rocks.danielw.restdocs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;
import rocks.danielw.rest.certification.CertificationDto;
import rocks.danielw.rest.certification.CertificationRequestDto;
import rocks.danielw.rest.certification.CertificationService;
import rocks.danielw.rest.certification.CertificationsRestController;
import rocks.danielw.restassured.CertificationTestUtil;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*; // important to use this class instead of MockMvcRequestBuilders
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.mycv.com", uriPort = 80)
@WebMvcTest(CertificationsRestController.class)
class RestDocsTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @MockBean
  private CertificationService certificationService;

  @Test
  @DisplayName("Documenting path parameter")
  void DocumentingPathParameters() throws Exception {
    CertificationDto certificationDto = CertificationTestUtil.testCertificationDto("MTA", LocalDate.of(2016,1,1));

    when(certificationService.find(anyLong())).thenReturn(Optional.of(certificationDto));

    mockMvc.perform(get("/rest/v1/certifications/{certId}", 1L)
            .accept(MediaType.APPLICATION_JSON)) // the accept header is mandatory
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            // ...do response assertion here
            .andDo(document("v1/certification-by-id",
              pathParameters(
                parameterWithName("certId").description("ID of the desired Certification to get.")
            )));
  }

  @Test
  @DisplayName("Documenting query parameter")
  void documentingQueryParameters() throws Exception {
    CertificationDto certificationDto = CertificationTestUtil.testCertificationDto("MTA", LocalDate.of(2016,1,1));

    when(certificationService.findByName(anyString())).thenReturn(Optional.of(certificationDto));

    mockMvc.perform(get("/rest/v1/certifications/search")
            .param("certificationName", "MTA")
            .accept(MediaType.APPLICATION_JSON)) // the accept header is mandatory
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            // ...do response assertion here
            .andDo(
              document("v1/certification-by-name",
                requestParameters(
                  parameterWithName("certificationName").description("Name of the desired Certification to get.")
            )));
  }

  @Test
  @DisplayName("Documenting responses")
  void documentingResponses() throws Exception {
    CertificationDto certificationDto = CertificationTestUtil.testCertificationDto("MTA", LocalDate.of(2016,1,1));

    when(certificationService.find(anyLong())).thenReturn(Optional.of(certificationDto));

    mockMvc.perform(get("/rest/v1/certifications/{certId}", 1L)
            .accept(MediaType.APPLICATION_JSON)) // the accept header is mandatory
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            // ...do response assertion here
            .andDo(
              document("v1/certification-by-id-with-response",
                responseFields(
                        fieldWithPath("id").description("ID of certification"),
                        fieldWithPath("name").description("Name of certification"),
                        fieldWithPath("certificate").description("Name of certification file"),
                        fieldWithPath("dateOfAchievement").description("The date on which the certificate was obtained"),
                        fieldWithPath("userId").description("Public id of the user the certificate belongs to").type(UUID.class)
                )));
  }

  @Test
  @DisplayName("Documenting requests")
  void documentingRequests() throws Exception {
    CertificationRequestDto oca = CertificationTestUtil.createTestCertificationRequestDto("OCA", LocalDate.of(2017,6,15));
    String ocaCertificationJson = mapper.writeValueAsString(oca);

    // fails as long as we have an individual request-fields.snippet file in test resources folder
    // we are forced to document the field constraints - see method documentingFieldConstraints()
/*    mockMvc.perform(post("/rest/v1/certifications")
            .content(ocaCertificationJson)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andDo(document("v1/create-certification",
              requestFields(
                fieldWithPath("name").description("Name of certification"),
                fieldWithPath("certificate").description("Name of certification file"),
                fieldWithPath("dateOfAchievement").ignored() // ignore field for documentation
            )));*/
  }

  @Test
  @DisplayName("Documenting field constraints")
  void documentingFieldConstraints() throws Exception {
    CertificationRequestDto oca = CertificationTestUtil.createTestCertificationRequestDto("OCA", LocalDate.of(2017,6,15));
    String ocaCertificationJson = mapper.writeValueAsString(oca);

    ConstraintFields fields = new ConstraintFields(CertificationRequestDto.class);

    mockMvc.perform(post("/rest/v1/certifications")
            .content(ocaCertificationJson)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andDo(document("v1/create-certification-with-constraints",
                    requestFields(
                            fields.withPaths("name").description("Name of certification"),
                            fields.withPaths("certificate").description("Name of certification file"),
                            fields.withPaths("dateOfAchievement").ignored() // ignore field for documentation
                    )));
  }

  private static class ConstraintFields {

    private final ConstraintDescriptions constraintDescriptions;

    ConstraintFields(Class<?> input) {
      constraintDescriptions = new ConstraintDescriptions(input);
    }

    private FieldDescriptor withPaths(String path) {
      return fieldWithPath(path)
              .attributes(key("constraints")
              .value(StringUtils.collectionToDelimitedString(constraintDescriptions.descriptionsForProperty(path), ". ")));
    }

  }

}
