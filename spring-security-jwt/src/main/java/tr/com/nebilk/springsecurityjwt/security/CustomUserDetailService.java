package tr.com.nebilk.springsecurityjwt.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tr.com.nebilk.springsecurityjwt.entity.SampleUser;
import tr.com.nebilk.springsecurityjwt.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomUserDetailService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final SampleUser userByUsername = userService.findUserByUsername(username);
        if (userByUsername == null) {
            log.error("User not found!!!");
            throw new UsernameNotFoundException("User not found!!!");
        }
        log.info("User found {}", username);
        List<SimpleGrantedAuthority> authorities = userByUsername.getRoles()
                                                .stream()
                                                .map(
                                                        role -> new SimpleGrantedAuthority(role.getName().toString())
                                                ).collect(Collectors.toList());
        return new User(username, userByUsername.getPassword(), authorities);
    }
}
