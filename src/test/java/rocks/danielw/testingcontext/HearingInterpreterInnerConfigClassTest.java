package rocks.danielw.testingcontext;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@ActiveProfiles("inner-class")
class HearingInterpreterInnerConfigClassTest implements WithAssertions {

  @Autowired
  private HearingInterpreter hearingInterpreter;

  @Test
  void whatIHeard() {
    String word = hearingInterpreter.whatIHeard();

    assertThat(word).isEqualTo("Laurel");
  }

  @TestConfiguration
  @Profile("inner-class")
  static class TestConfig {

    @Bean
    HearingInterpreter hearingInterpreter() {
      return new HearingInterpreter(new LaurelWordProducer());
    }

  }
}