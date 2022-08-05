package kg.airbnb.airbnb.apis;

import com.google.firebase.auth.FirebaseAuthException;
import kg.airbnb.airbnb.dto.JwtResponse;
import kg.airbnb.airbnb.dto.LoginRequest;
import kg.airbnb.airbnb.dto.UserRegisterRequest;
import kg.airbnb.airbnb.security.LoginService;
import kg.airbnb.airbnb.services.impl.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthApi {

    private final AuthService authService;
    private final LoginService loginService;

    @PostMapping("/user/register")
    public JwtResponse registrationPerson(@RequestBody UserRegisterRequest userRegisterRequest) {
        return authService.registerUser(userRegisterRequest);
    }

    @PostMapping("/login")
    public JwtResponse performLogin(@RequestBody LoginRequest loginResponse) {
        return loginService.authenticate(loginResponse);
    }

    @PostMapping("/login/google")
    public JwtResponse loginWithGoogle(@RequestParam String token) throws FirebaseAuthException {
        return loginService.authenticateWithGoogle(token);
    }

}
