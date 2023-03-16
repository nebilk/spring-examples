package tr.com.nebildev.springsecurityjwt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public CustomAuthorizationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
        } else {
            final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            final Optional<String> token = jwtTokenProvider.verifyHeader(authHeader);
            if (token.isPresent()) {
                try {
                    final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = jwtTokenProvider.verifyAccessToken(token.get());
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception exception) {
                    log.error("Login failed : {}", exception.getMessage());
                    response.setHeader("error", exception.getMessage());
                    Map<String, String> errors = new HashMap<>();
                    errors.put("error_message", exception.getMessage());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    final String tokenJson = new ObjectMapper().writeValueAsString(errors);
                    response.getWriter().write(tokenJson);
                    response.flushBuffer();
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
