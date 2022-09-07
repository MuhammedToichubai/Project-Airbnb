package kg.airbnb.airbnb.apis;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.airbnb.airbnb.dto.responses.JwtResponse;
import kg.airbnb.airbnb.dto.requests.LoginRequest;
import kg.airbnb.airbnb.dto.requests.UserRegisterRequest;
import com.google.firebase.auth.FirebaseAuthException;
import kg.airbnb.airbnb.security.LoginService;
import kg.airbnb.airbnb.services.impl.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Authentication API", description = "Users with role\"Admin\",\"User\" can authenticate.")
public class AuthApi {

    private final AuthService authService;
    private final LoginService loginService;

    @Operation(summary = "Registration", description = "Any user can register.")
    @PostMapping("/user/register")
    public JwtResponse registrationPerson(@RequestBody UserRegisterRequest userRegisterRequest) {
        return authService.registerUser(userRegisterRequest);
    }

    @Operation(summary = "Sign in", description = "Any registered user can login.")
    @PostMapping("/login")
    public JwtResponse performLogin(@RequestBody LoginRequest loginResponse) {
        return loginService.authenticate(loginResponse);
    }

    @Operation(summary = "Sign in with Google", description = "Any user can login with google account.")
    @PostMapping("/login/google")
    public JwtResponse loginWithGoogle(@RequestParam String token) throws FirebaseAuthException {
        return loginService.authenticateWithGoogle(token);
    }

}
