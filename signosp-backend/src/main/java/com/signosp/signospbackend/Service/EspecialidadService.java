package com.signosp.signospbackend.Service;

import com.signosp.signospbackend.Models.especialidad.Especialidad;
import com.signosp.signospbackend.Models.especialidad.EspecialidadDTO;
import com.signosp.signospbackend.Models.especialidad.EspecialidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EspecialidadService {
    public final EspecialidadRepository especialidadRepository;

    public void crearEspecialidad(EspecialidadDTO especialidadDTO) {
        Especialidad nuevaEspecialidad = Especialidad.builder()
                .nombre(especialidadDTO.getNombre())
                .build();
        especialidadRepository.save(nuevaEspecialidad);
    }
    public ResponseEntity<String> modificarEspecialidad(EspecialidadDTO especialidadDTO){
        Especialidad especialidad = especialidadRepository.findById(especialidadDTO.getId_especialidad()).orElse(null);
        if(especialidad == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la especialidad");
        }
        Especialidad especialidadMod = Especialidad.builder()
                .id_especialidad(especialidad.getId_especialidad())
                .nombre(especialidadDTO.getNombre())
                .build();
        especialidadRepository.save(especialidadMod);
        return ResponseEntity.ok("Especialidad modificada");
    }
    public EspecialidadDTO convertirEspecialidadDTO(Especialidad especialidad) {
        return EspecialidadDTO.builder()
                .id_especialidad(especialidad.getId_especialidad())
                .nombre(especialidad.getNombre())
                .build();
    }

    public void eliminarEspecialidad(Long id_especialidad){
        especialidadRepository.deleteById(id_especialidad);
    }
}