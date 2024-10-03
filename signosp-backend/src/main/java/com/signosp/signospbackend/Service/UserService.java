package com.signosp.signospbackend.Service;

import com.signosp.signospbackend.User.User;
import com.signosp.signospbackend.User.UserDTO;
import com.signosp.signospbackend.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    public final UserRepository userRepository;

    public List<UserDTO> getAll(){
        List<User> usuarios = userRepository.findAll();
        List<UserDTO> usuariosDTOS = new ArrayList<>();
        for(User u : usuarios){
            UserDTO usuarioDTO = UserDTO.builder()
                    .id_usuario(u.getId_usuario())
                    .username(u.getUsername())
                    .build();
            usuariosDTOS.add(usuarioDTO);
        }
        return usuariosDTOS;
    }
}
