package rocks.danielw.testingcontext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("base-test")
public class YannyConfig {

  @Bean
  public YannyWordProducer yannyWordProducer() {
    return new YannyWordProducer();
  }

}
