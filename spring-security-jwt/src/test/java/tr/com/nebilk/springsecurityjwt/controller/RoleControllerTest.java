package tr.com.nebilk.springsecurityjwt.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tr.com.nebilk.springsecurityjwt.entity.Role;
import tr.com.nebilk.springsecurityjwt.enums.RoleType;
import tr.com.nebilk.springsecurityjwt.service.RoleService;

@ExtendWith(MockitoExtension.class)
class RoleControllerTest {
    @Mock
    private RoleService roleService;
    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    public void setUp(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void testSaveRole() {
        Role role = new Role(1L, RoleType.ADMIN);

        when(roleService.saveRole(any(Role.class))).thenReturn(role);

        ResponseEntity<Role> result = roleController.saveRole(role);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(role);
    }

    @Test
    void testGetRoles() {
        Role role1 = new Role(1L, RoleType.ADMIN);
        Role role2 = new Role(2L, RoleType.USER);
        List<Role> roles = new ArrayList<>();
        roles.add(role1);
        roles.add(role2);

        when(roleService.getRoles()).thenReturn(roles);

        ResponseEntity<List<Role>> result = roleController.getRoles();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(roles);
    }
}