package com.signosp.signospbackend.Service;

import com.signosp.signospbackend.Models.subtarea.Subtarea;
import com.signosp.signospbackend.Models.subtarea.SubtareaDTO;
import com.signosp.signospbackend.Models.subtarea.SubtareaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubtareaService {
    public  final SubtareaRepository subtareaRepository;
    public void crearSubtarea(SubtareaDTO subtareaDTO){
        Subtarea subtarea = Subtarea.builder()
                .nombre(subtareaDTO.getNombre())
                .estado(subtareaDTO.getEstado())
                .build();
        subtareaRepository.save(subtarea);
    }
    public SubtareaDTO convertirSubtareaDTO(Subtarea subtarea){
        return SubtareaDTO.builder()
                .id_subtarea(subtarea.getId_subtarea())
                .nombre(subtarea.getNombre())
                .estado(subtarea.getEstado())
                .build();
    }
    public ResponseEntity<String> modificarSubtarea(SubtareaDTO subtareaDTO){
        Subtarea subtarea = subtareaRepository.findById(subtareaDTO.getId_subtarea()).orElse(null);
        if(subtarea==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la subtarea");
        }
        Subtarea subtareaMod = Subtarea.builder()
                .id_subtarea(subtarea.getId_subtarea())
                .nombre(subtareaDTO.getNombre()!=null? subtareaDTO.getNombre() : subtarea.getNombre())
                .estado(subtareaDTO.getEstado()!= null? subtareaDTO.getEstado() : subtarea.getEstado())
                .build();
        subtareaRepository.save(subtareaMod);
        return ResponseEntity.ok("Subtarea modificada");
    }
    public void eliminarSubtarea(Long id_subtarea){
        subtareaRepository.deleteById(id_subtarea);
    }

}
