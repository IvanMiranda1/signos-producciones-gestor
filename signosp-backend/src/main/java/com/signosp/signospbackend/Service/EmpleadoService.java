package com.signosp.signospbackend.Service;

import com.signosp.signospbackend.Models.disponibilidad.Disponibilidad;
import com.signosp.signospbackend.Models.disponibilidad.DisponibilidadDTO;
import com.signosp.signospbackend.Models.disponibilidad.DisponibilidadRepository;
import com.signosp.signospbackend.Models.empleado.*;
import com.signosp.signospbackend.Models.especialidad.Especialidad;
import com.signosp.signospbackend.Models.especialidad.EspecialidadDTO;
import com.signosp.signospbackend.Models.especialidad.EspecialidadRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class EmpleadoService {

    public final EmpleadoRepository empleadoRepository;
    public final EspecialidadRepository especialidadRepository;
    public final EspecialidadService especialidadService;
    public final DisponibilidadRepository disponibilidadRepository;

    public void crearEmpleado(EmpleadoxDispo empleadoDTO) {

        Set<Especialidad> especialidades = new HashSet<>();
        for (Long id_especialidad : empleadoDTO.getEspecialidad()) {
            Especialidad especialidad = especialidadRepository.findById(id_especialidad)
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró la especialidad con ID: " + id_especialidad));
            especialidades.add(especialidad);
        }

        Empleado nuevoEmpleado = Empleado.builder()
                .nomyape(empleadoDTO.getNomyape())
                .correo(empleadoDTO.getCorreo())
                .telefono(empleadoDTO.getTelefono())
                .especialidad(especialidades)
                .build();

        Empleado newEmpleado = empleadoRepository.save(nuevoEmpleado);
        for (DisponibilidadDTO disponibilidad : empleadoDTO.getDisponibilidades()){
            Disponibilidad disp = Disponibilidad.builder()
                    .empleado(newEmpleado)
                    .dia(disponibilidad.getDia())
                    .desde(disponibilidad.getDesde())
                    .hasta(disponibilidad.getHasta())
                    .build();
            disponibilidadRepository.save(disp);
        }
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

    @Modifying
    @Transactional
    public ResponseEntity<String> modificarEmpleado(EmpleadoFull empleadoDTO){

        Optional<Empleado> optionalEmpleado = empleadoRepository.findById(empleadoDTO.getId_empleado());
        if(!optionalEmpleado.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro al empleado");
        }
        Empleado empleado = optionalEmpleado.get();
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
        Empleado empleadoModificado = empleadoRepository.save(empleadoMod);

        disponibilidadRepository.deleteByIdEmpleado(empleadoDTO.getId_empleado());

        for (DisponibilidadDTO disponibilidad : empleadoDTO.getDisponibilidadDTO()){
            Disponibilidad disp = Disponibilidad.builder()
                    .empleado(empleadoModificado)
                    .dia(disponibilidad.getDia())
                    .desde(disponibilidad.getDesde())
                    .hasta(disponibilidad.getHasta())
                    .build();
            disponibilidadRepository.save(disp);
        }

        return ResponseEntity.ok("Empleado modificado");
    }

    public void eliminarEmpleado(Long id_empleado){
        // eliminar la relacion empleado evento y empleado disponiblidad

        disponibilidadRepository.deleteByIdEmpleado(id_empleado);
        empleadoRepository.deleteById(id_empleado);
    }

    public EmpleadoDTO byId(Long id) {
            Empleado a = empleadoRepository.findById(id)
                    .orElseThrow(()-> new EntityNotFoundException("Empleado"));

            return convertirEmpleadoDTO(a);
    }

    public EmpleadoFull byIdFull(Long id) {
        Empleado a = empleadoRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Empleado"));
        List<Disponibilidad> listdispo = disponibilidadRepository.findByIdEmpleado(a.getId_empleado());
        List<DisponibilidadDTO> disponibilidadesDTO = new ArrayList<>();
        for(Disponibilidad d : listdispo){
            DisponibilidadDTO dispo = DisponibilidadDTO.builder()
                    .id_disponibilidad(d.getId_disponibilidad())
                    .id_empleado(d.getEmpleado().getId_empleado())
                    .dia(d.getDia())
                    .desde(d.getDesde())
                    .hasta(d.getHasta())
                    .build();
            disponibilidadesDTO.add(dispo);
        }
        EmpleadoDTO empleadoDTO = convertirEmpleadoDTO(a);
        EmpleadoFull empleadoFull = EmpleadoFull.builder()
                .id_empleado(empleadoDTO.getId_empleado())
                .nomyape(empleadoDTO.getNomyape())
                .correo(empleadoDTO.getCorreo())
                .telefono(empleadoDTO.getTelefono())
                .especialidadDTO(empleadoDTO.getEspecialidadDTO())
                .disponibilidadDTO(disponibilidadesDTO)
                .build();
        return empleadoFull;
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
