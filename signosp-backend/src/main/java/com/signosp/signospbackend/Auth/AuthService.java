package com.signosp.signospbackend.Auth;

import com.signosp.signospbackend.JWT.JwtService;
import com.signosp.signospbackend.User.Role;
import com.signosp.signospbackend.User.User;
import com.signosp.signospbackend.User.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    public final UserRepository userRepository;
    public final JwtService jwtService;
    public final PasswordEncoder passwordEncoder;
    public final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        System.out.println("2");
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow(()-> new EntityNotFoundException("No se encontro"));
        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .nomyape(request.getNomyape())
                .role(Role.USER)
                .build();
        userRepository.save(user);
        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();

    }

    public ResponseEntity<String> isValid(String token) {
        final UserDetails usuario = userRepository.findByUsername(jwtService.getUserNameFromToken(token))
                .orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"));
        if(jwtService.ifTokenValid(token, usuario)){
            return ResponseEntity.ok("Token valido");
        } else {
            return ResponseEntity.badRequest().body("Token no valido");
        }
    }
}
