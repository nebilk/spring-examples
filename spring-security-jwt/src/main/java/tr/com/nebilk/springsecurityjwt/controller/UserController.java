package tr.com.nebilk.springsecurityjwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tr.com.nebilk.springsecurityjwt.entity.SampleUser;
import tr.com.nebilk.springsecurityjwt.enums.RoleType;
import tr.com.nebilk.springsecurityjwt.service.UserService;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<SampleUser>> getUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/username")
    public ResponseEntity<String> getUserPrinciple(Principal principal) {
        return ResponseEntity.ok(principal.getName());
    }

    @PostMapping
    public ResponseEntity<SampleUser> saveUser(@RequestBody SampleUser user){
        URI location = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        return ResponseEntity.created(location).body(userService.saveUser(user));
    }

    @PostMapping("/{username}/roles")
    public ResponseEntity<SampleUser> addRoleToUser(@PathVariable("username") String username, @RequestBody RoleType roleName){
        return ResponseEntity.ok().body(userService.addRoleToUser(username, roleName));
    }
}
