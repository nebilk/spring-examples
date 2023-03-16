package tr.com.nebildev.springsecurityjwt.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tr.com.nebildev.springsecurityjwt.entity.SampleUser;
import tr.com.nebildev.springsecurityjwt.enums.RoleType;
import tr.com.nebildev.springsecurityjwt.entity.Role;
import tr.com.nebildev.springsecurityjwt.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleService roleService;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, roleService, bCryptPasswordEncoder);
    }

    @Test
    void testSaveUser_ShouldEncodePasswordAndSaveUser() {
        // given
        SampleUser user = new SampleUser();
        user.setPassword("password");

        when(bCryptPasswordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        // when
        SampleUser savedUser = userService.saveUser(user);

        // then
        assertThat(savedUser.getPassword()).isEqualTo("encodedPassword");
        verify(bCryptPasswordEncoder).encode("password");
        verify(userRepository).save(user);
    }

    @Test
    void testFindUserByUsername_ShouldReturnUser() {
        // given
        SampleUser user = new SampleUser();
        user.setUsername("username");

        when(userRepository.findByUsername("username")).thenReturn(user);

        // when
        SampleUser foundUser = userService.findUserByUsername("username");

        // then
        assertThat(foundUser).isEqualTo(user);
        verify(userRepository).findByUsername("username");
    }

    @Test
    void testFindAllUsers_ShouldReturnAllUsers() {
        // given
        List<SampleUser> users = new ArrayList<>();
        SampleUser user1 = new SampleUser();
        SampleUser user2 = new SampleUser();
        users.add(user1);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);

        // when
        List<SampleUser> foundUsers = userService.findAllUsers();

        // then
        assertThat(foundUsers).isEqualTo(users);
        verify(userRepository).findAll();
    }

    @Test
    void testAddRoleToUser_ShouldAddRoleToUser() {
        // given
        SampleUser user = new SampleUser();
        user.setUsername("username");
        Role role = new Role();
        role.setName(RoleType.USER);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);

        when(userRepository.findByUsername("username")).thenReturn(user);
        when(roleService.findRoleByName(RoleType.ADMIN)).thenReturn(new Role(null, RoleType.ADMIN));
        when(userRepository.save(user)).thenReturn(user);

        // when
        SampleUser updatedUser = userService.addRoleToUser("username", RoleType.ADMIN);

        // then
        Assertions.assertThat(updatedUser.getRoles()).hasSize(2);
        assertThat(updatedUser.getRoles().get(1).getName()).isEqualTo(RoleType.ADMIN);
        verify(userRepository).findByUsername("username");
        verify(roleService).findRoleByName(RoleType.ADMIN);
        verify(userRepository).save(user);
    }
}