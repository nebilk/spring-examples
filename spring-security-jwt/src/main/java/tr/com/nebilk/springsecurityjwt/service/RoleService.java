package tr.com.nebilk.springsecurityjwt.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tr.com.nebilk.springsecurityjwt.entity.Role;
import tr.com.nebilk.springsecurityjwt.enums.RoleType;
import tr.com.nebilk.springsecurityjwt.repository.RoleRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role saveRole(Role role){
        return roleRepository.save(role);
    }

    public Role findRoleByName(RoleType roleName){
        return roleRepository.findByName(roleName);
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
