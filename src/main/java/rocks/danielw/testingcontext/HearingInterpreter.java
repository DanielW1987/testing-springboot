package rocks.danielw.testingcontext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HearingInterpreter {

  private final WordProducer wordProducer;

  @Autowired
  public HearingInterpreter(WordProducer wordProducer) {
    this.wordProducer = wordProducer;
  }

  public String whatIHeard() {
    return wordProducer.getWord();
  }

}
