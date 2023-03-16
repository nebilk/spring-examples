package tr.com.nebildev.springsecurityjwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tr.com.nebildev.springsecurityjwt.dto.UserCreateRequest;
import tr.com.nebildev.springsecurityjwt.security.CustomAuthenticationFilter;
import tr.com.nebildev.springsecurityjwt.dto.LoginResponse;
import tr.com.nebildev.springsecurityjwt.dto.UserCreateResponse;
import tr.com.nebildev.springsecurityjwt.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * login phase is processing in {@link CustomAuthenticationFilter}
     * @PostMapping("/login")
     * @RequestBody LoginRequest loginRequest
     */

    @PostMapping("/register")
    public ResponseEntity<UserCreateResponse> register(@RequestBody UserCreateRequest userCreateRequest){
        return ResponseEntity.ok(authService.register(userCreateRequest));
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponse> refreshToken(@RequestHeader("Authorization") String authorizationHeader) {
        return ResponseEntity.ok(authService.refreshToken(authorizationHeader, ServletUriComponentsBuilder.fromCurrentRequest().toUriString()));
    }

    @PostMapping("/validate-token")
    public ResponseEntity<Void> checkToken(@RequestHeader("Authorization") String authorizationHeader) {
        authService.validateToken(authorizationHeader);
        return ResponseEntity.noContent().build();
    }
}
