package com.signosp.signospbackend.Service;

import com.signosp.signospbackend.Models.comentario.Comentario;
import com.signosp.signospbackend.Models.comentario.ComentarioDTO;
import com.signosp.signospbackend.Models.comentario.ComentarioRepository;
import com.signosp.signospbackend.Models.empleado.Empleado;
import com.signosp.signospbackend.Models.empleado.EmpleadoRepository;
import com.signosp.signospbackend.Models.evento.Evento;
import com.signosp.signospbackend.Models.evento.EventoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComentarioService {
    public final ComentarioRepository comentarioRepository;
    public final EventoRepository eventoRepository;
    public final EmpleadoRepository empleadoRepository;
    public void crearComentario(ComentarioDTO comentarioDTO) {
        Evento evento = eventoRepository.findById(comentarioDTO.getId_evento())
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el evento"));
        Empleado empleado = empleadoRepository.findById(comentarioDTO.getId_empleado())
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el empleado"));
        Comentario nuevoComentario = Comentario.builder()
                        .contenido(comentarioDTO.getContenido())
                        .evento(evento)
                        .empleado(empleado)
                        .build();
        comentarioRepository.save(nuevoComentario);
    }

    public ComentarioDTO convertirComentarioDTO(Comentario comentario){
        return ComentarioDTO.builder()
                .id_comentario(comentario.getId_comentario())
                .contenido(comentario.getContenido())
                .id_evento(comentario.getEvento().getId_evento())
                .id_empleado(comentario.getEmpleado().getId_empleado())
                .build();
    }

    public ResponseEntity<String> modificarcomentario(ComentarioDTO comentarioDTO){
        Comentario comentario = comentarioRepository.findById(comentarioDTO.getId_comentario()).orElse(null);
        if(comentario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID de comentario no encontrado");
        }
        Comentario comentarioModificado = Comentario.builder()
                .id_comentario(comentario.getId_comentario())
                .contenido(comentario.getContenido())
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
}
