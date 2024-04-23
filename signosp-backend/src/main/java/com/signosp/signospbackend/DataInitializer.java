package com.signosp.signospbackend;

import com.signosp.signospbackend.User.Role;
import com.signosp.signospbackend.User.User;
import com.signosp.signospbackend.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    public final UserRepository userRepository;
    public final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception{
        Optional<User> admin = userRepository.findByUsername("admin");
        if(admin.isPresent()){
            System.out.println("Ya hay ADMIN creado en BD");
        } else {
            User user = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .nomyape("admin")
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(user);
            System.out.println("ADMIN Creado");
        }
    }


}
