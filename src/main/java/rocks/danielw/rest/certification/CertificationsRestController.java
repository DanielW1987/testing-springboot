package rocks.danielw.rest.certification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/v1/certifications")
public class CertificationsRestController {

  private final CertificationService certificationService;

  @Autowired
  public CertificationsRestController(CertificationService certificationService) {
    this.certificationService = certificationService;
  }

  @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<CertificationDto> get(@PathVariable String id) {
    Optional<CertificationDto> certificationDto = certificationService.find(Long.parseLong(id));

    return ResponseEntity.of(certificationDto);
  }

  @GetMapping(path = "/search", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<CertificationDto> getByName(@RequestParam String certificationName) {
    Optional<CertificationDto> certificationDto = certificationService.findByName(certificationName);

    return ResponseEntity.of(certificationDto);
  }

  @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<List<CertificationDto>> getAll() {
    List<CertificationDto> certificationDtos = certificationService.findAll();

    return ResponseEntity.ok(certificationDtos);
  }

  @GetMapping(path = "/list", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<CertificationList> listCertifications() {
    List<CertificationDto> certificationDtos = certificationService.findAll();
    CertificationList certificationList = new CertificationList(certificationDtos, PageRequest.of(0, 3), certificationDtos.size());
    return ResponseEntity.ok(certificationList);
  }

  @PostMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
               consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<CertificationDto> create(@Valid @RequestBody CertificationRequestDto request, BindingResult bindingResult) {
    validateRequest(bindingResult);

    CertificationDto certification = certificationService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON_UTF8).body(certification);
  }

  @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
                              produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<CertificationDto> update(@PathVariable long id,
                                                 @Valid @RequestBody CertificationRequestDto request,
                                                 BindingResult bindingResult) {
    validateRequest(bindingResult);

    Optional<CertificationDto> certification = certificationService.update(id, request);
    return ResponseEntity.of(certification);
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable long id) {
    boolean success = certificationService.delete(id);

    return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
  }

  private void validateRequest(BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new IllegalArgumentException("There were errors validating your request. Please refer to the documentation for a valid request.");
    }
  }

}
