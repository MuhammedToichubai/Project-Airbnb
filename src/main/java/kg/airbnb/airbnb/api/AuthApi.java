package kg.airbnb.airbnb.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.airbnb.airbnb.dto.requests.PhoneNumberRequest;
import kg.airbnb.airbnb.dto.responses.JwtResponse;
import com.google.firebase.auth.FirebaseAuthException;
import kg.airbnb.airbnb.dto.requests.LoginRequest;
import kg.airbnb.airbnb.dto.requests.UserRegisterRequest;
import kg.airbnb.airbnb.dto.responses.SimpleResponse;
import kg.airbnb.airbnb.services.impl.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/public")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Authentication API", description = "Registration and authentication")
public class AuthApi {

    private final AuthService authService;
    private final AuthService loginService;

    @Operation(summary = "Registration", description = "Any user can register")
    @PostMapping("register")
    public JwtResponse registrationPerson(@RequestBody UserRegisterRequest userRegisterRequest) {
        return authService.registerUser(userRegisterRequest);
    }

    @Operation(summary = "Sign in", description = "Any registered user can login")
    @PostMapping("login")
    public JwtResponse performLogin(@RequestBody LoginRequest loginResponse) {
        return loginService.authenticate(loginResponse);
    }

    @Operation(summary = "Sign in with Google", description = "Any user can login with google account")
    @PostMapping("auth-google")
    public JwtResponse loginWithGoogle(@RequestParam String token) throws FirebaseAuthException {
        return loginService.authenticateWithGoogle(token);
    }

    @Operation(summary = "Add phone number", description = "Registered user must add phone number")
    @PostMapping("phone-number")
    public SimpleResponse addPhoneNumber(@RequestBody PhoneNumberRequest request) {
        return loginService.addPhoneNumber(request);
    }

}
