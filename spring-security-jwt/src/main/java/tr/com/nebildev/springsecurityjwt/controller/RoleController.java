package tr.com.nebildev.springsecurityjwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tr.com.nebildev.springsecurityjwt.entity.Role;
import tr.com.nebildev.springsecurityjwt.service.RoleService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        URI location = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        return ResponseEntity.created(location).body(roleService.saveRole(role));
    }

    @GetMapping
    public ResponseEntity<List<Role>> getRoles(){
        return ResponseEntity.ok(roleService.getRoles());
    }

}
