package com.signosp.signospbackend.Service;

import com.signosp.signospbackend.Models.subtarea.Subtarea;
import com.signosp.signospbackend.Models.subtarea.SubtareaDTO;
import com.signosp.signospbackend.Models.subtarea.SubtareaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubtareaService {
    public final SubtareaRepository subtareaRepository;

    public void crearSubtarea(SubtareaDTO subtareaDTO){
        Subtarea subtarea = Subtarea.builder()
                .id_evento(subtareaDTO.getId_evento())
                .nombre(subtareaDTO.getNombre())
                .estado(false)
                .build();
        subtareaRepository.save(subtarea);
    }

    public SubtareaDTO convertirSubtareaDTO(Subtarea subtarea){
        return SubtareaDTO.builder()
                .id_subtarea(subtarea.getId_subtarea())
                .id_evento(subtarea.getId_evento())
                .nombre(subtarea.getNombre())
                .estado(subtarea.getEstado())
                .build();
    }
    public ResponseEntity<String> modificarSubtarea(SubtareaDTO subtareaDTO){
        Optional<Subtarea> optionalSubtarea = subtareaRepository.findById(subtareaDTO.getId_subtarea());
        if(!optionalSubtarea.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la subtarea");
        }
        Subtarea subtarea = optionalSubtarea.get();

        Subtarea subtareaMod = Subtarea.builder()
                .id_subtarea(subtarea.getId_subtarea())
                .id_evento(subtarea.getId_evento())
                .nombre(subtareaDTO.getNombre()!=null? subtareaDTO.getNombre() : subtarea.getNombre())
                .estado(subtareaDTO.getEstado()!= null? subtareaDTO.getEstado() : subtarea.getEstado())
                .build();
        subtareaRepository.save(subtareaMod);
        return ResponseEntity.ok("Subtarea modificada");
    }
    public void eliminarSubtarea(Long id_subtarea){
        subtareaRepository.deleteById(id_subtarea);
    }

    public List<SubtareaDTO> findAll() {
            List<Subtarea> a = subtareaRepository.findAll();
            List<SubtareaDTO> b = new ArrayList<>();
            for(Subtarea c : a){
                b.add(convertirSubtareaDTO(c));
            }
            return b;
        }

    public SubtareaDTO byId(Long id) {
            Subtarea a = subtareaRepository.findById(id)
                    .orElseThrow(()-> new EntityNotFoundException("Subtarea"));
            return convertirSubtareaDTO(a);
        }

    public List<SubtareaDTO> findSubtareaXEvento(Long idEvento) {
        List<Subtarea> list = subtareaRepository.findByIdEvento(idEvento);
        List<SubtareaDTO> listDTO = new ArrayList<>();
        for(Subtarea subtarea : list){
            listDTO.add(convertirSubtareaDTO(subtarea));
        }
        return listDTO;
    }

    public ResponseEntity<String> terminarSubtarea(Long id) {
        Optional<Subtarea> optionalSubtarea = subtareaRepository.findById(id);
        if(!optionalSubtarea.isPresent()){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra subtarea.");
        }
        Subtarea subtarea = optionalSubtarea.get();
        subtarea.setEstado(!subtarea.getEstado());
        subtareaRepository.save(subtarea);
        return ResponseEntity.ok("Subtarea modificada.");
    }
}
