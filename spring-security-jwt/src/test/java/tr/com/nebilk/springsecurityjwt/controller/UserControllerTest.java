package tr.com.nebilk.springsecurityjwt.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tr.com.nebilk.springsecurityjwt.entity.SampleUser;
import tr.com.nebilk.springsecurityjwt.service.UserService;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void canGetUsers() {

        final List<SampleUser> userList = Arrays.asList(
                new SampleUser(1L, "user_test", "username_test", "password_test", Collections.emptyList()),
                new SampleUser(2L, "user_test2", "username_test2", "password_test2", Collections.emptyList())
        );

        when(userService.findAllUsers()).thenReturn(userList);

        final ResponseEntity<List<SampleUser>> users = userController.getUsers();
        assertThat(users.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(users).isNotNull();
        assertThat(users.getBody()).hasSize(2);
    }

    /**
     *     @GetMapping("/username")
     *     public ResponseEntity<String> getUserPrinciple(Principal principal) {
     *         return ResponseEntity.ok(principal.getName());
     *     }
     */

    @Test
    void canGetUserPrincipal(){
        final Principal principal = new UsernamePasswordAuthenticationToken("username", "password");
        final ResponseEntity<String> userPrinciple = userController.getUserPrinciple(principal);

        assertThat(userPrinciple.getBody()).isEqualTo(principal.getName() );
    }

}