package rocks.danielw.testingcontext;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class LaurelWordProducer implements WordProducer {

  @Override
  public String getWord() {
    return "Laurel";
  }

}
