package tr.com.nebildev.springsecurityjwt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;
import tr.com.nebildev.springsecurityjwt.dto.LoginRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager,
                                      JwtTokenProvider jwtTokenProvider) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        log.info("The username is {} and the password is {}", username, password);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username,
                password
        );
        return authenticationManager.authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        User user = (User) authResult.getPrincipal();

        String accessToken = jwtTokenProvider.createAccessToken(user.getUsername(), user.getAuthorities(), request.getRequestURI());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUsername(), request.getRequestURI());

        Map<String, String> tokensMap = new HashMap<>();
        tokensMap.put("access_token", accessToken);
        tokensMap.put("refresh_token", refreshToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        final String tokenJson = new ObjectMapper().writeValueAsString(tokensMap);
        SecurityContextHolder.getContext().setAuthentication(authResult);
        response.getWriter().write(tokenJson);
        response.flushBuffer();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // you can check brute force attack
        // you can see how many times the user is trying to log in
        // if the user exceeds the number of times that what we can give a specific time to lock the user in this method
        log.info(failed.getLocalizedMessage());
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
