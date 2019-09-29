package rocks.danielw.mockmvc;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class NameService {

  private final List<String> names = new ArrayList<>();

  public NameService() {
    names.add("John Doe");
    names.add("Miranda Baily");
    names.add("Nick Smith");
  }

  public String randomName() {
    int index = ThreadLocalRandom.current().nextInt(names.size());
    return names.get(index);
  }

  public List<Person> findByLastName(String lastName) {
    return names.stream()
                .filter(name -> name.endsWith(lastName))
                .map(name -> {
                  String[] nameParts = name.split(" ");
                  return new Person(nameParts[0], nameParts[1]);
                })
                .collect(Collectors.toList());
  }

  public void createName(Person person) {
    names.add(person.toString());
  }

}
