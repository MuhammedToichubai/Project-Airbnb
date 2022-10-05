package kg.airbnb.airbnb.dto.responses;

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
    private String phoneNumber;

}
