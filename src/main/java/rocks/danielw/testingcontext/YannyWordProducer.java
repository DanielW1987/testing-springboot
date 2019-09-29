package rocks.danielw.testingcontext;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Primary
@Profile("yanny")
public class YannyWordProducer implements WordProducer {

  @Override
  public String getWord() {
    return "Yanny";
  }
}
