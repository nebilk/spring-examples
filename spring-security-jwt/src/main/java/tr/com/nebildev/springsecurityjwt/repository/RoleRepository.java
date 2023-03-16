package tr.com.nebildev.springsecurityjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.nebildev.springsecurityjwt.entity.Role;
import tr.com.nebildev.springsecurityjwt.enums.RoleType;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleType name);
}
