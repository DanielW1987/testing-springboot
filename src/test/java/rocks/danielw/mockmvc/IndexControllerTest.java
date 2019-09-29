package rocks.danielw.mockmvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IndexController.class)
class IndexControllerTest {

  @MockBean
  private NameService nameService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  void testFindByLastName() throws Exception {
    when(nameService.findByLastName(anyString())).thenReturn(List.of(new Person("John", "Doe")));

    mockMvc.perform(get("/find").param("lastName", "Doe"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("result"))
            .andExpect(view().name("html/search-results"));
  }

  @Test
  void testCreateNameWithValidParameter() throws Exception {
    mockMvc.perform(post("/create-name")
              .param("firstName", "John")
              .param("lastName", "Doe"))
            .andExpect(status().is3xxRedirection())
            .andExpect(model().attributeExists("createNameRequest"))
            .andExpect(model().errorCount(0))
            .andExpect(view().name("redirect:/index"));
  }

  @Test
  void testCreateNameWithInvalidParameter() throws Exception {
    mockMvc.perform(post("/create-name")
              .param("firstName", "John")
              .param("lastName", ""))
            .andExpect(status().isBadRequest())
            .andExpect(model().attributeExists("createNameRequest"))
            .andExpect(model().errorCount(1))
            .andExpect(model().attributeHasErrors("person"))
            .andExpect(model().attributeHasFieldErrors("person","lastName"))
            .andExpect(view().name("html/create-name"));
  }

}
