package com.signosp.signospbackend.Service;

import com.signosp.signospbackend.Models.comentario.Comentario;
import com.signosp.signospbackend.Models.comentario.ComentarioDTO;
import com.signosp.signospbackend.Models.comentario.ComentarioRepository;
import com.signosp.signospbackend.Models.empleado.Empleado;
import com.signosp.signospbackend.Models.empleado.EmpleadoRepository;
import com.signosp.signospbackend.Models.evento.Evento;
import com.signosp.signospbackend.Models.evento.EventoRepository;
import com.signosp.signospbackend.User.User;
import com.signosp.signospbackend.User.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComentarioService {
    public final ComentarioRepository comentarioRepository;
    public final EventoRepository eventoRepository;
    public final UserRepository userRepository;

    public void crearComentario(ComentarioDTO comentarioDTO) {
        Evento evento = eventoRepository.findById(comentarioDTO.getId_evento())
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el evento"));
        User usuario = userRepository.findById(comentarioDTO.getId_usuario())
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el usuario"));
        Comentario nuevoComentario = Comentario.builder()
                        .contenido(comentarioDTO.getContenido())
                        .evento(evento)
                        .usuario(usuario)
                        .build();
        comentarioRepository.save(nuevoComentario);
    }

    public ComentarioDTO convertirComentarioDTO(Comentario comentario){
        return ComentarioDTO.builder()
                .id_comentario(comentario.getId_comentario())
                .contenido(comentario.getContenido())
                .id_evento(comentario.getEvento().getId_evento())
                .id_usuario(comentario.getUsuario().getId_usuario())
                .build();
    }

    public ResponseEntity<String> modificarcomentario(ComentarioDTO comentarioDTO){
        Optional<Comentario> optionalComentario = comentarioRepository.findById(comentarioDTO.getId_comentario());
        if(!optionalComentario.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID de comentario no encontrado");
        }
        Comentario comentario = optionalComentario.get();

        Comentario comentarioModificado = Comentario.builder()
                .id_comentario(comentario.getId_comentario())
                .contenido(comentarioDTO.getContenido() != null ? comentarioDTO.getContenido() : comentario.getContenido())
                .build();
        comentarioRepository.save(comentarioModificado);
        return ResponseEntity.ok("comentario modificado");
    }

    public ResponseEntity<String> eliminarComentario(Long id_comentario){
        comentarioRepository.deleteById(id_comentario);
        return ResponseEntity.ok("Comentario eliminado correctamente.");
    }

    public List<ComentarioDTO> findAll() {
        List<Comentario> a = comentarioRepository.findAll();
        List<ComentarioDTO> b = new ArrayList<>();
        for(Comentario c : a){
            b.add(convertirComentarioDTO(c));
        }
        return b;
    }
    public ComentarioDTO byId(Long id){
        return convertirComentarioDTO(comentarioRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("no encontrado")));
    }

    public List<ComentarioDTO> obtenerComentariosxIdEvento(Long idEvento) {
        List<ComentarioDTO> listDTO = new ArrayList<>();
        List<Comentario> list = comentarioRepository.findByIdEvento(idEvento);
        for (Comentario c : list){
            listDTO.add(convertirComentarioDTO(c));
        }
        return listDTO;
    }

}
