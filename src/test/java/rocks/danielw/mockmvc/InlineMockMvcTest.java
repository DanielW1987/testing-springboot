package rocks.danielw.mockmvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class InlineMockMvcTest {

  @Mock
  private NameService nameService;

  @InjectMocks
  private IndexController controller;

  private MockMvc mockMvc;

  @BeforeEach
  void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

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
