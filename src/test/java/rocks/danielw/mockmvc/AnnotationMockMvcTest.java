package rocks.danielw.mockmvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IndexController.class)
class AnnotationMockMvcTest {

  @MockBean
  private NameService nameService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  void testShowIndexPage() throws Exception {
    when(nameService.randomName()).thenReturn("John Doe");

    mockMvc.perform(get("/index"))
            .andExpect(status().isOk())
            .andExpect(model().size(1))
            .andExpect(model().attributeExists("name"))
            .andExpect(view().name("html/index"));
  }

}
