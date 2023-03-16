package tr.com.nebildev.springsecurityjwt.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ResourceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetRoles() throws Exception {
        // Given
        final List<String> exceptedResponse = Arrays.asList("Sample1", "Sample2");

        // When and then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/public/resources")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", equalTo(2)))
                .andExpect(jsonPath("$[0]", equalTo(exceptedResponse.get(0))))
                .andExpect(jsonPath("$[1]", equalTo(exceptedResponse.get(1))));

    }

}