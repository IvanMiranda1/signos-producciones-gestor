package com.signosp.signospbackend.Auth;

import com.signosp.signospbackend.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    public final AuthService authService;

    public final UserRepository userRepository;

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }
    @PostMapping(value = "validate")
    public ResponseEntity<ResponseEntity<String>> isValid(@RequestBody AuthResponse request){
        return ResponseEntity.ok(authService.isValid(request.getToken()));
    }
}
