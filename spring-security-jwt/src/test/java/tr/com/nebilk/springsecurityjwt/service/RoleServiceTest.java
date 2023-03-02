package tr.com.nebilk.springsecurityjwt.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tr.com.nebilk.springsecurityjwt.entity.Role;
import tr.com.nebilk.springsecurityjwt.enums.RoleType;
import tr.com.nebilk.springsecurityjwt.repository.RoleRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        roleService = new RoleService(roleRepository);
    }

    @Test
    void testSaveRole_ShouldSaveRole() {
        // given
        Role role = new Role(null, RoleType.USER);

        // when
        when(roleRepository.save(role)).thenReturn(role);
        Role savedRole = roleService.saveRole(role);

        // then
        assertThat(savedRole).isNotNull();
        assertThat(savedRole.getName()).isEqualTo(RoleType.USER);
    }

    @Test
    void shouldFindRoleByName() {
        // given
        Role role = new Role(1L, RoleType.ADMIN);

        // when
        when(roleRepository.findByName(RoleType.ADMIN)).thenReturn(role);
        Role foundRole = roleService.findRoleByName(RoleType.ADMIN);

        // then
        assertThat(foundRole).isNotNull();
        assertThat(foundRole.getName()).isEqualTo(RoleType.ADMIN);
    }

    @Test
    void shouldGetRoles() {
        // given
        Role role1 = new Role(1L, RoleType.USER);
        Role role2 = new Role(2L, RoleType.ADMIN);

        // when
        when(roleRepository.findAll()).thenReturn(Arrays.asList(role1, role2));
        List<Role> roles = roleService.getRoles();

        // then
        assertThat(roles).hasSize(2);
        assertThat(roles).extracting(Role::getName).contains(RoleType.USER, RoleType.ADMIN);
    }

}