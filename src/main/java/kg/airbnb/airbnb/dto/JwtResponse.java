package kg.airbnb.airbnb.dto;


import kg.airbnb.airbnb.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class JwtResponse {

    private Long userId;
    private String email;
    private String jwt;
    private Role role;

    public JwtResponse(Long id, String jwt, Role role) {
        this.userId = id;
        this.jwt = jwt;
        this.role = role;
    }
}
