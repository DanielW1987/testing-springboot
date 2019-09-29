package rocks.danielw.mockmvc;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class IndexController {

  private final NameService nameService;

  public IndexController(NameService nameService) {
    this.nameService = nameService;
  }

  @GetMapping(path = {"", "/", "/index"}, produces = "text/html")
  public ModelAndView showIndexPage() {
    Map<String, Object> viewModel = new HashMap<>();
    viewModel.put("name", nameService.randomName());
    return new ModelAndView("html/index", viewModel, HttpStatus.OK);
  }

  @GetMapping(path = "/find", produces = "text/html")
  public ModelAndView findByLastName(Person person) { // query parameter firstName and/or lastName are automatically bound to the object
    String lastName = person.getLastName();
    List<Person> persons = nameService.findByLastName(lastName);

    Map<String, Object> viewModel = new HashMap<>();
    viewModel.put("result", persons.stream().map(Person::toString).collect(Collectors.joining(", ")));

    return new ModelAndView("html/search-results", viewModel, HttpStatus.OK);
  }

  @GetMapping(path = "/create-name", produces = "text/html")
  public ModelAndView showCreateNameForm() {
    Map<String, Object> viewModel = new HashMap<>();
    viewModel.put("createNameRequest", new Person());
    return new ModelAndView("html/create-name", viewModel, HttpStatus.OK);
  }

  @PostMapping(path = "/create-name")
  public ModelAndView createName(@Valid Person createNameRequest, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      Map<String, Object> viewModel = new HashMap<>();
      viewModel.put("createNameRequest", new Person());
      return new ModelAndView("html/create-name", viewModel, HttpStatus.BAD_REQUEST);
    }

    nameService.createName(createNameRequest);
    Map<String, Object> viewModel = new HashMap<>();
    viewModel.put("createNameRequest", new Person());
    return new ModelAndView("redirect:/index", viewModel);
  }

}
