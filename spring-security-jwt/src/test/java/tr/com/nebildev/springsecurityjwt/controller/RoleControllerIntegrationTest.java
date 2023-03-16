package tr.com.nebildev.springsecurityjwt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tr.com.nebildev.springsecurityjwt.entity.Role;
import tr.com.nebildev.springsecurityjwt.enums.RoleType;
import tr.com.nebildev.springsecurityjwt.service.RoleService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class RoleControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetRoles() throws Exception {
        // Given
        Role role1 = new Role(1L, RoleType.ADMIN);
        Role role2 = new Role(2L, RoleType.USER);
        List<Role> roles = Arrays.asList(role1, role2);
        when(roleService.getRoles()).thenReturn(roles);

        // When and then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", equalTo(2)))
                .andExpect(jsonPath("$[0].name", equalTo("ADMIN")))
                .andExpect(jsonPath("$[1].name", equalTo("USER")));
    }

    @Test
    void testSaveRole() throws Exception {
        // Given
        Role roleRequest = new Role(null, RoleType.ADMIN);
        Role savedRole = new Role(1L, RoleType.ADMIN);

        // When and then
        when(roleService.saveRole(roleRequest)).thenReturn(savedRole);
        String roleJson = objectMapper.writeValueAsString(roleRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(roleJson))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/v1/roles"))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("ADMIN")));
    }
}