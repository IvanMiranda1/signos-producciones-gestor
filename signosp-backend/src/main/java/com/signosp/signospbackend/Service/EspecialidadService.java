package com.signosp.signospbackend.Service;

import com.signosp.signospbackend.Models.empleado.Empleado;
import com.signosp.signospbackend.Models.empleado.EmpleadoRepository;
import com.signosp.signospbackend.Models.especialidad.Especialidad;
import com.signosp.signospbackend.Models.especialidad.EspecialidadDTO;
import com.signosp.signospbackend.Models.especialidad.EspecialidadRepository;
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
public class EspecialidadService {
    public final EspecialidadRepository especialidadRepository;
    public final EmpleadoRepository empleadoRepository;

    public void crearEspecialidad(EspecialidadDTO especialidadDTO) {
        Especialidad nuevaEspecialidad = Especialidad.builder()
                .nombre(especialidadDTO.getNombre())
                .build();
        especialidadRepository.save(nuevaEspecialidad);
    }

    public ResponseEntity<String> modificarEspecialidad(EspecialidadDTO especialidadDTO){
        Optional<Especialidad> optionalEspecialidad = especialidadRepository.findById(especialidadDTO.getId_especialidad());
        if(!optionalEspecialidad.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la especialidad");
        }
        Especialidad especialidad = optionalEspecialidad.get();

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

    public ResponseEntity<String> eliminarEspecialidad(Long id_especialidad){
        List<Long> id_empleados = empleadoRepository.findByIdEspecialidad(id_especialidad);
        if(id_empleados.isEmpty()){
            especialidadRepository.deleteById(id_especialidad);
            return ResponseEntity.ok("Especialidad eliminada exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se puede eliminar la especialidad porque está asociado a uno o más empleados.");
        }
    }

    public List<EspecialidadDTO> findAll() {
            List<Especialidad> a = especialidadRepository.findAll();
            List<EspecialidadDTO> b = new ArrayList<>();
            for(Especialidad c : a){
                b.add(convertirEspecialidadDTO(c));
            }
            return b;
        }

    public EspecialidadDTO byId(Long id) {
            Especialidad a = especialidadRepository.findById(id)
                    .orElseThrow(()-> new EntityNotFoundException("Especialidad"));
            return convertirEspecialidadDTO(a);
        }
}
