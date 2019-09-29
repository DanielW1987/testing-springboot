package rocks.danielw.rest.misc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class AppExceptionHandler {

  @ExceptionHandler(value = IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleBadRequests(IllegalArgumentException exception) {
    log.warn(exception.getMessage());
    return ResponseEntity.badRequest().body(createErrorResponse(exception));
  }

  private ErrorResponse createErrorResponse(IllegalArgumentException exception) {
    List<String> messages = new ArrayList<>();
    messages.add(exception.getMessage());
    return new ErrorResponse(messages);
  }

}
