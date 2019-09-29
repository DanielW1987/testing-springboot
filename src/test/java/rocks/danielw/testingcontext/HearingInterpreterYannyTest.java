package rocks.danielw.testingcontext;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {BaseConfig.class, YannyConfig.class})
@SpringJUnitConfig(classes = {BaseConfig.class, YannyConfig.class}) // combines @ExtendWith and @ContextConfiguration
@ActiveProfiles("base-test")
class HearingInterpreterYannyTest implements WithAssertions {

  @Autowired
  private HearingInterpreter hearingInterpreter;

  @Test
  void whatIHeard() {
    String word = hearingInterpreter.whatIHeard();

    assertThat(word).isEqualTo("Yanny");
  }
}