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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                .desde(disponibilidadDTO.getDesde())
                .hasta(disponibilidadDTO.getHasta())
                .empleado(empleado)
                .build();
        disponibilidadRepository.save(nuevaDisponibilidad);
    }

    public DisponibilidadDTO convertirDisponibilidadDTO(Disponibilidad disponibilidad){
        return DisponibilidadDTO.builder()
                .id_disponibilidad(disponibilidad.getId_disponibilidad())
                .dia(disponibilidad.getDia())
                .desde(disponibilidad.getDesde())
                .hasta(disponibilidad.getHasta())
                .id_empleado(disponibilidad.getEmpleado().getId_empleado())
                .build();
    }

    public ResponseEntity<String> modificarDisponibilidad(DisponibilidadDTO disponibilidadDTO){
        Optional<Disponibilidad> optionalDisponibilidad = disponibilidadRepository.findById(disponibilidadDTO.getId_disponibilidad());
        if(!optionalDisponibilidad.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la disponibilidad");
        }
        Disponibilidad disponibilidad = optionalDisponibilidad.get();

        Disponibilidad disponibilidadModificada = Disponibilidad.builder()
                .id_disponibilidad(disponibilidad.getId_disponibilidad())
                .dia(disponibilidadDTO.getDia() != null ? disponibilidadDTO.getDia() : disponibilidad.getDia())
                .desde(disponibilidadDTO.getDesde() != null ? disponibilidadDTO.getDesde() : disponibilidad.getDesde())
                .hasta(disponibilidad.getHasta() != null ? disponibilidadDTO.getHasta() : disponibilidad.getHasta())
                .build();
        disponibilidadRepository.save(disponibilidadModificada);
        return ResponseEntity.ok("Disponibilidad modificada");
    }

    public void eliminarDisponibilidad(Long id_disponibilidad){
        disponibilidadRepository.deleteById(id_disponibilidad);
    }

    public List<DisponibilidadDTO> findAll() {
        List<Disponibilidad> a = disponibilidadRepository.findAll();
        List<DisponibilidadDTO> b = new ArrayList<>();
        for(Disponibilidad c : a){
            b.add(convertirDisponibilidadDTO(c));
        }
        return b;
    }

    public DisponibilidadDTO byId(Long id) {
        Disponibilidad a = disponibilidadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Disponibilidad"));
        return convertirDisponibilidadDTO(a);
    }

    public List<DisponibilidadDTO> disponibilidadxidEmpleado(Long id) {
        List<Disponibilidad> dispXEmpleados = disponibilidadRepository.findByIdEmpleado(id);
        List<DisponibilidadDTO> list = new ArrayList<>();
        for(Disponibilidad d : dispXEmpleados){
            list.add(convertirDisponibilidadDTO(d));
        }
        return list;
    }
}
