package kg.airbnb.airbnb.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class UserRegisterRequest {

//    private String fullName;

    private String email;

    private String password;
}
