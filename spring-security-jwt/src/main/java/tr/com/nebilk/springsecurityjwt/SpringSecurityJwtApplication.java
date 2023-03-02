package tr.com.nebilk.springsecurityjwt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tr.com.nebilk.springsecurityjwt.entity.Role;
import tr.com.nebilk.springsecurityjwt.entity.SampleUser;
import tr.com.nebilk.springsecurityjwt.enums.RoleType;
import tr.com.nebilk.springsecurityjwt.service.RoleService;
import tr.com.nebilk.springsecurityjwt.service.UserService;

import java.util.Collections;

@SpringBootApplication
public class SpringSecurityJwtApplication {
//        implements CommandLineRunner {

//    public SpringSecurityJwtApplication(RoleService roleService, UserService userService) {
//        this.roleService = roleService;
//        this.userService = userService;
//    }

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityJwtApplication.class, args);
    }

//    private final RoleService roleService;
//    private final UserService userService;
//
//    @Override
//    public void run(String... args) throws Exception {
//        roleService.saveRole(new Role(null, RoleType.ADMIN));
//        roleService.saveRole(new Role(null, RoleType.MANAGEMENT));
//        roleService.saveRole(new Role(null, RoleType.USER));
//        roleService.saveRole(new Role(null, RoleType.GUEST));
//
//        userService.saveUser(new SampleUser(null, "admin", "admin_user", "admin_password", Collections.emptyList()));
//
//        userService.addRoleToUser("admin_user", RoleType.ADMIN);
//
//    }
}
