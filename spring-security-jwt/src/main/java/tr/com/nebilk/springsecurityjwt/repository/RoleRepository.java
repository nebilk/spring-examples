package tr.com.nebilk.springsecurityjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.nebilk.springsecurityjwt.entity.Role;
import tr.com.nebilk.springsecurityjwt.enums.RoleType;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleType name);
}
