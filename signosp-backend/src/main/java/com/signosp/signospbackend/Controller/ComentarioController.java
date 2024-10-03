package com.signosp.signospbackend.Controller;

import com.signosp.signospbackend.Models.comentario.ComentarioDTO;
import com.signosp.signospbackend.Service.ComentarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/comentario")
@RequiredArgsConstructor
public class ComentarioController {
    public final ComentarioService comentarioService;

    @GetMapping
    public List<ComentarioDTO> getAllComentarios(){
        return comentarioService.findAll();
    }
    @GetMapping("/{id}")
    public ComentarioDTO comentarioById(@PathVariable Long id){
        return comentarioService.byId(id);
    }

    @PostMapping
    public void crearComentario(@RequestBody ComentarioDTO comentarioDTO){
        comentarioService.crearComentario(comentarioDTO);
    }
    @PutMapping
    public void modificarComentario(@RequestBody ComentarioDTO comentarioDTO){
        comentarioService.modificarcomentario(comentarioDTO);
    }
    @DeleteMapping("/{id}")
    public void eliminarComentario(@PathVariable Long id){
        comentarioService.eliminarComentario(id);
    }
}
