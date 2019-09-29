package rocks.danielw.testingcontext;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@ActiveProfiles("yanny")
class HearingInterpreterActiveProfileTest implements WithAssertions {

  @Autowired
  private HearingInterpreter hearingInterpreter;

  @Test
  void whatIHeard() {
    String word = hearingInterpreter.whatIHeard();

    assertThat(word).isEqualTo("Yanny");
  }

  @TestConfiguration
  @ComponentScan("rocks.danielw.testingcontext") // picks up all beans from this package
  @Profile("yanny")
  static class TestConfig {
    // class needed for component scan
  }
}