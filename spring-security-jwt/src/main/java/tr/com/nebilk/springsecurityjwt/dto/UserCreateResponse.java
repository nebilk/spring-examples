package tr.com.nebilk.springsecurityjwt.dto;

import lombok.*;
import tr.com.nebilk.springsecurityjwt.enums.RoleType;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateResponse {
    private String name;
    private String username;
    private List<RoleType> roles;
}
