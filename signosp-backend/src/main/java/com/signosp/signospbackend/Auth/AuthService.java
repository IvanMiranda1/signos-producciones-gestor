package com.signosp.signospbackend.Auth;

import com.signosp.signospbackend.JWT.JwtService;
import com.signosp.signospbackend.User.Role;
import com.signosp.signospbackend.User.User;
import com.signosp.signospbackend.User.UserDTO;
import com.signosp.signospbackend.User.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
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

    public AuthResponse changePassword(LoginRequest request){
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new EntityNotFoundException("No se encontro el usuario."));
        System.out.println("Usuario: " + request.getUsername() + ", Contraseña: " + request.getPassword());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        return login(request);
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

    public ResponseEntity<UserDTO> userFromToken(String token) {
        return userRepository.findByUsername(jwtService.getUserNameFromToken(token))
                .filter(usuario -> jwtService.ifTokenValid(token, usuario))
                .map(usuario -> UserDTO.builder()
                        .id_usuario(usuario.getId_usuario())
                        .username(usuario.getUsername())
                        .build()
                )
                .map(username -> ResponseEntity.ok().body(username))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));
    }
    public ResponseEntity<String> getRole(String token) {
        // Obtén el nombre de usuario a partir del token usando jwtService
        return userRepository.findByUsername(jwtService.getUserNameFromToken(token))
                .filter(usuario -> jwtService.ifTokenValid(token, usuario)) // Verifica si el token es válido
                .map(usuario -> {
                    // Obtiene el rol del usuario y devuelve una respuesta con el rol
                    String role = usuario.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .findFirst()
                            .orElse("ROLE_USER"); // O usa un rol por defecto si no se encuentra ninguno
                    return ResponseEntity.ok().body(role);
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido"));
    }
}
