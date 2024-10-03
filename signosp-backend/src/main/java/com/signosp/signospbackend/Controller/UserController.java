package com.signosp.signospbackend.Controller;

import com.signosp.signospbackend.Models.categoria.CategoriaDTO;
import com.signosp.signospbackend.Service.UserService;
import com.signosp.signospbackend.User.User;
import com.signosp.signospbackend.User.UserDTO;
import com.signosp.signospbackend.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UserController {
    public final UserService userService;
    @GetMapping
    public List<UserDTO> getAllUsers(){
        return userService.getAll();
    }

}
