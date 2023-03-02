package tr.com.nebilk.springsecurityjwt.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import tr.com.nebilk.springsecurityjwt.dto.LoginResponse;
import tr.com.nebilk.springsecurityjwt.dto.UserCreateRequest;
import tr.com.nebilk.springsecurityjwt.dto.UserCreateResponse;
import tr.com.nebilk.springsecurityjwt.entity.Role;
import tr.com.nebilk.springsecurityjwt.entity.SampleUser;
import tr.com.nebilk.springsecurityjwt.enums.RoleType;
import tr.com.nebilk.springsecurityjwt.exception.UsernameTakenException;
import tr.com.nebilk.springsecurityjwt.security.JwtTokenProvider;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;


    public AuthService(JwtTokenProvider jwtTokenProvider,
                       UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    public LoginResponse refreshToken(String authHeader, String uri) {
        final String oldRefreshToken = validateToken(authHeader);

        final String refreshTokenUsername = jwtTokenProvider.getRefreshTokenUsername(oldRefreshToken);
        final SampleUser userByUsername = userService.findUserByUsername(refreshTokenUsername);
        final String newRefreshToken = jwtTokenProvider.createRefreshToken(refreshTokenUsername, uri);
        final String accessToken = jwtTokenProvider.createAccessToken(refreshTokenUsername,
                userByUsername.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName().toString()))
                        .collect(Collectors.toList())
                , uri);
        return new LoginResponse(accessToken, newRefreshToken);
    }

    public String validateToken(String authHeader) {
        final Optional<String> refreshTokenOptional = jwtTokenProvider.verifyHeader(authHeader);
        final String oldRefreshToken = refreshTokenOptional.orElseThrow(() -> new RuntimeException("Bearer not found!!!"));

        jwtTokenProvider.verifyRefreshToken(oldRefreshToken);
        return oldRefreshToken;
    }

    public UserCreateResponse register(UserCreateRequest userCreateRequest) {
        final SampleUser userByUsername = userService.findUserByUsername(userCreateRequest.getUsername());

        if (Objects.nonNull(userByUsername)) {
            throw new UsernameTakenException("Username is already taken!!!");
        }

        userService.saveUser(new SampleUser(null,
                userCreateRequest.getName(),
                userCreateRequest.getUsername(),
                userCreateRequest.getPassword(),
                Collections.emptyList()));
        final SampleUser sampleUser = userService.addRoleToUser(userCreateRequest.getUsername(), RoleType.USER);

        return UserCreateResponse.builder()
                .name(sampleUser.getName())
                .username(sampleUser.getUsername())
                .roles(sampleUser.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .build();
    }
}
