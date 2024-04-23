package com.signosp.signospbackend.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data //Agrega metodos como tostring, equal, getters y setters (forma conveniente de ahorrar tiempo escribiendo el cod repetitivo
@Builder //Permite usar la sintaxis (patron de diseño) builder() para crear los objetos ej: user.builder().username('nombre').build()
@NoArgsConstructor //Genera un constructor sin argumentos user()
@AllArgsConstructor //el constructor de siempre con todos sus parametros User(Long id_usuario, String username...);
@Entity
@Table(name="usuario", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id_usuario;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "nomyape")
    private String nomyape;
    @Column(name = "password", nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((role.name())));
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
