package com.signosp.signospbackend.Service;

import com.signosp.signospbackend.Models.empleado.Empleado;
import com.signosp.signospbackend.Models.empleado.EmpleadoRepository;
import com.signosp.signospbackend.Models.especialidad.Especialidad;
import com.signosp.signospbackend.Models.empleado.EmpleadoDTO;
import com.signosp.signospbackend.Models.especialidad.EspecialidadDTO;
import com.signosp.signospbackend.Models.especialidad.EspecialidadRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EmpleadoService {
    public final EmpleadoRepository empleadoRepository;
    public final EspecialidadRepository especialidadRepository;
    public final EspecialidadService especialidadService;
    public void crearEmpleado(EmpleadoDTO empleadoDTO) {
        Set<Especialidad> especialidades = new HashSet<>();
        for (EspecialidadDTO especialidadDTO : empleadoDTO.getEspecialidadDTO()) {
            Especialidad especialidad = especialidadRepository.findById(especialidadDTO.getId_especialidad())
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró la especialidad con ID: " + especialidadDTO.getId_especialidad()));
            especialidades.add(especialidad);
        }
        Empleado nuevoEmpleado = Empleado.builder()
                .nomyape(empleadoDTO.getNomyape())
                .correo(empleadoDTO.getCorreo())
                .telefono(empleadoDTO.getTelefono())
                .especialidad(especialidades)
                .build();
        empleadoRepository.save(nuevoEmpleado);
    }

    public EmpleadoDTO convertirEmpleadoDTO(Empleado empleado){
        Set<EspecialidadDTO> especialidadesDTO = new HashSet<>();
        for (Especialidad especialidad : empleado.getEspecialidad()){
            especialidadesDTO.add(especialidadService.convertirEspecialidadDTO(especialidad));
        }
        return EmpleadoDTO.builder()
                .id_empleado(empleado.getId_empleado())
                .nomyape(empleado.getNomyape())
                .correo(empleado.getCorreo())
                .telefono(empleado.getTelefono())
                .especialidadDTO(especialidadesDTO)
                .build();
    }

    public ResponseEntity<String> modificarEmpleado(EmpleadoDTO empleadoDTO){
        Empleado empleado = empleadoRepository.findById(empleadoDTO.getId_empleado()).orElse(null);
        if(empleado == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro al empleado");
        }

        Set<Especialidad> especialidades = new HashSet<>();
        for (EspecialidadDTO especialidadDTO : empleadoDTO.getEspecialidadDTO()) {
            Especialidad especialidad = especialidadRepository.findById(especialidadDTO.getId_especialidad())
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró la especialidad con ID: " + especialidadDTO.getId_especialidad()));
            especialidades.add(especialidad);
        }

        Empleado empleadoMod = Empleado.builder()
                .id_empleado(empleado.getId_empleado())
                .nomyape(empleadoDTO.getNomyape() != null ? empleadoDTO.getNomyape() : empleado.getNomyape())
                .correo(empleadoDTO.getCorreo() != null ? empleadoDTO.getCorreo() : empleado.getCorreo())
                .telefono(empleadoDTO.getTelefono() != null ? empleadoDTO.getTelefono() : empleado.getTelefono())
                .especialidad(!especialidades.isEmpty()? especialidades : empleado.getEspecialidad())
                .build();
        empleadoRepository.save(empleadoMod);
        return ResponseEntity.ok("Empleado modificado");
    }

    public void eliminarEmpleado(Long id_empleado){
        empleadoRepository.deleteById(id_empleado);
    }

    public EmpleadoDTO byId(Long id) {
            Empleado a = empleadoRepository.findById(id)
                    .orElseThrow(()-> new EntityNotFoundException("Empleado"));
            return convertirEmpleadoDTO(a);
    }
    public List<EmpleadoDTO> findAll() {
            List<Empleado> a = empleadoRepository.findAll();
            List<EmpleadoDTO> b = new ArrayList<>();
            for(Empleado c : a){
                b.add(convertirEmpleadoDTO(c));
            }
            return b;
        }
}
