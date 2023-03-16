package tr.com.nebildev.springsecurityjwt.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import tr.com.nebildev.springsecurityjwt.dto.LoginResponse;
import tr.com.nebildev.springsecurityjwt.dto.UserCreateRequest;
import tr.com.nebildev.springsecurityjwt.dto.UserCreateResponse;
import tr.com.nebildev.springsecurityjwt.entity.SampleUser;
import tr.com.nebildev.springsecurityjwt.enums.RoleType;
import tr.com.nebildev.springsecurityjwt.exception.UsernameTakenException;
import tr.com.nebildev.springsecurityjwt.entity.Role;
import tr.com.nebildev.springsecurityjwt.security.JwtTokenProvider;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private UserService userService;
    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(jwtTokenProvider, userService);
    }

    @Test
    @DisplayName("Test refreshToken with valid token")
    void testRefreshTokenWithValidToken() {
        // Arrange
        final String authHeader = "Bearer oldRefreshToken";
        final String uri = "/test/uri";
        final String refreshTokenUsername = "testUser";


        final SampleUser sampleUser = new SampleUser(1L, "Test User", refreshTokenUsername, "password", Collections.singletonList(new Role(1L, RoleType.USER)));
        final String newRefreshToken = "newRefreshToken";
        final String accessToken = "accessToken";
        when(jwtTokenProvider.verifyHeader(authHeader)).thenReturn(Optional.of("oldRefreshToken"));
        when(jwtTokenProvider.getRefreshTokenUsername("oldRefreshToken")).thenReturn(refreshTokenUsername);
        when(userService.findUserByUsername(refreshTokenUsername)).thenReturn(sampleUser);
        when(jwtTokenProvider.createRefreshToken(refreshTokenUsername, uri)).thenReturn(newRefreshToken);
        when(jwtTokenProvider.createAccessToken(refreshTokenUsername, Collections.singletonList(new SimpleGrantedAuthority(RoleType.USER.toString())), uri)).thenReturn(accessToken);

        // Act
        final LoginResponse loginResponse = authService.refreshToken(authHeader, uri);

        // Assert
        assertThat(loginResponse.getAccessToken()).isEqualTo(accessToken);
        assertThat(loginResponse.getRefreshToken()).isEqualTo(newRefreshToken);
    }

    @Test
    @DisplayName("Test refreshToken with invalid token")
    void testRefreshTokenWithInvalidToken() {
        // Arrange
        final String authHeader = "Bearer invalidToken";
        when(jwtTokenProvider.verifyHeader(authHeader)).thenReturn(Optional.empty());

        // Act and Assert
        assertThatThrownBy(() -> authService.refreshToken(authHeader, "/test/uri"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Bearer not found!!!");
    }

    @Test
    @DisplayName("Test validateToken with valid token")
    void testValidateToken() {
        final String authHeader = "Bearer <JWT_TOKEN>";
        final String oldRefreshToken = "OLD_REFRESH_TOKEN";

        when(jwtTokenProvider.verifyHeader(authHeader)).thenReturn(java.util.Optional.ofNullable(oldRefreshToken));

        final String validatedToken = authService.validateToken(authHeader);

        assertThat(validatedToken).isEqualTo(oldRefreshToken);
    }

    @Test
    @DisplayName("Test validateToken with invalid token")
    void testValidateTokenWithInvalidToken() {
        final String authHeader = "Bearer <INVALID_TOKEN>";

        when(jwtTokenProvider.verifyHeader(authHeader)).thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> authService.validateToken(authHeader))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Bearer not found!!!");
    }

    @Test
    @DisplayName("Test register with valid user")
    void testRegisterWithNonExistingUsername() {
        UserCreateRequest request = new UserCreateRequest("John Doe", "johndoe", "password");
        final SampleUser user = new SampleUser(null, request.getName(), request.getUsername(), request.getPassword(), Collections.emptyList());
        final SampleUser addRoleUser = new SampleUser(user.getId(), user.getName(), user.getUsername(), user.getPassword(), Collections.singletonList(new Role(1L, RoleType.USER)));



        when(userService.findUserByUsername(request.getUsername())).thenReturn(null);
        when(userService.saveUser(user)).thenReturn(user);
        when(userService.addRoleToUser(user.getUsername(), RoleType.USER)).thenReturn(addRoleUser);

        UserCreateResponse response = authService.register(request);

        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo(user.getName());
        assertThat(response.getUsername()).isEqualTo(user.getUsername());
        assertThat(response.getRoles()).contains(RoleType.USER);
    }

    @Test
    @DisplayName("Test register with existing user")
    void testRegisterWithExistingUsername() {
        UserCreateRequest request = new UserCreateRequest("John Doe", "johndoe", "password");

        SampleUser sampleUser = new SampleUser(null, request.getName(), request.getUsername(), request.getPassword(), Collections.emptyList());
        when(userService.findUserByUsername(request.getUsername())).thenReturn(sampleUser);

        assertThatThrownBy(() -> authService.register(request))
                .isInstanceOf(UsernameTakenException.class)
                .hasMessageContaining("Username is already taken!!!");

    }

}