package tr.com.nebildev.springsecurityjwt.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.google.common.hash.Hashing;
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
import tr.com.nebildev.springsecurityjwt.enums.RoleType;
import tr.com.nebildev.springsecurityjwt.entity.Role;
import tr.com.nebildev.springsecurityjwt.service.RoleService;

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

    @Test
    void testShortener(){
        final String s = Hashing.murmur3_32().hashString("https://github.com/asdasd/asds/fsd/f", Charset.defaultCharset()).toString();
        System.out.println(s);

        //5859515b
    }
}