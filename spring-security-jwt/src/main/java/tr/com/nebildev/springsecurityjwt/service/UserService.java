package tr.com.nebildev.springsecurityjwt.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tr.com.nebildev.springsecurityjwt.entity.Role;
import tr.com.nebildev.springsecurityjwt.entity.SampleUser;
import tr.com.nebildev.springsecurityjwt.enums.RoleType;
import tr.com.nebildev.springsecurityjwt.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository,
                       RoleService roleService,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public SampleUser saveUser(SampleUser sampleUser) {
        sampleUser.setPassword(bCryptPasswordEncoder.encode(sampleUser.getPassword()));
        return userRepository.save(sampleUser);
    }

    public SampleUser findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<SampleUser> findAllUsers() {
        return userRepository.findAll();
    }

    public SampleUser addRoleToUser(String username, RoleType roleName) {
        SampleUser byUsername = this.findUserByUsername(username);
        List<Role> roles = byUsername.getRoles();
        Role roleByName = roleService.findRoleByName(roleName);
        if (CollectionUtils.isEmpty(roles)) {
            roles = new ArrayList<>();
        }
        roles.add(roleByName);
        byUsername.setRoles(roles);
        return userRepository.save(byUsername);
    }


}
