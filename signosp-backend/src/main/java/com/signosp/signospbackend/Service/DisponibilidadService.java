package com.signosp.signospbackend.Service;

import com.signosp.signospbackend.Models.disponibilidad.Disponibilidad;
import com.signosp.signospbackend.Models.disponibilidad.DisponibilidadDTO;
import com.signosp.signospbackend.Models.disponibilidad.DisponibilidadRepository;
import com.signosp.signospbackend.Models.empleado.Empleado;
import com.signosp.signospbackend.Models.empleado.EmpleadoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DisponibilidadService {
    public final EmpleadoRepository empleadoRepository;
    public final DisponibilidadRepository disponibilidadRepository;
    public void crearDisponibilidad(DisponibilidadDTO disponibilidadDTO) {
        Empleado empleado = empleadoRepository.findById(disponibilidadDTO.getId_empleado())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el empleado con ID: " + disponibilidadDTO.getId_empleado()));
        Disponibilidad nuevaDisponibilidad = Disponibilidad.builder()
                .dia(disponibilidadDTO.getDia())
                .horario(disponibilidadDTO.getHorario())
                .empleado(empleado)
                .build();
        disponibilidadRepository.save(nuevaDisponibilidad);
    }

    public DisponibilidadDTO convertirDisponibilidadDTO(Disponibilidad disponibilidad){
        return DisponibilidadDTO.builder()
                .id_disponibilidad(disponibilidad.getId_disponibilidad())
                .dia(disponibilidad.getDia())
                .horario(disponibilidad.getHorario())
                .id_empleado(disponibilidad.getEmpleado().getId_empleado())
                .build();
    }

    public ResponseEntity<String> modificarDisponibilidad(DisponibilidadDTO disponibilidadDTO){
        Disponibilidad disponibilidad = disponibilidadRepository.findById(disponibilidadDTO.getId_disponibilidad()).orElse(null);
        if(disponibilidad == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la disponibilidad");
        }
        Disponibilidad disponibilidadModificada = Disponibilidad.builder()
                .id_disponibilidad(disponibilidad.getId_disponibilidad())
                .dia(disponibilidadDTO.getDia() != null ? disponibilidadDTO.getDia() : disponibilidad.getDia())
                .horario(disponibilidadDTO.getHorario() != null ? disponibilidadDTO.getHorario() : disponibilidad.getHorario())
                .build();
        disponibilidadRepository.save(disponibilidadModificada);
        return ResponseEntity.ok("Disponibilidad modificada");
    }

    public void eliminarDisponibilidad(Long id_disponibilidad){
        disponibilidadRepository.deleteById(id_disponibilidad);
    }
}
