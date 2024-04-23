package com.signosp.signospbackend.Service;

import com.signosp.signospbackend.Models.empleado.Empleado;
import com.signosp.signospbackend.Models.empleado.EmpleadoDTO;
import com.signosp.signospbackend.Models.empleado.EmpleadoRepository;
import com.signosp.signospbackend.Models.evento.Evento;
import com.signosp.signospbackend.Models.evento.EventoRepository;
import com.signosp.signospbackend.Models.evento_empleado.Evento_empleado;
import com.signosp.signospbackend.Models.evento_empleado.Evento_empleadoDTO;
import com.signosp.signospbackend.Models.evento_empleado.Evento_empleadoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Evento_empleadoService {
    public final Evento_empleadoRepository eventoEmpleadoRepository;
    public final EventoRepository eventoRepository;
    public final EmpleadoRepository empleadoRepository;
    public void crearEvento_empleado(Evento_empleadoDTO evento_empleadoDTO) {
        Evento evento = eventoRepository.findById(evento_empleadoDTO.getId_evento())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el evento con ID: " + evento_empleadoDTO.getId_evento()));

        Empleado empleado = empleadoRepository.findById(evento_empleadoDTO.getId_empleado())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el empleado con ID: " + evento_empleadoDTO.getId_empleado()));

        Evento_empleado nuevoEventoEmpleado = Evento_empleado.builder()
            .evento(evento)
            .empleado(empleado)
            .fecha_trabajada(evento_empleadoDTO.getFecha_trabajada())
            .build();
        eventoEmpleadoRepository.save(nuevoEventoEmpleado);
    }

    public Evento_empleadoDTO convertirRelacionDTO(Evento_empleado evento_empleado){
        return Evento_empleadoDTO.builder()
                .id_evento_empleado(evento_empleado.getId_evento_empleado())
                .id_evento(evento_empleado.getEvento().getId_evento())
                .id_empleado(evento_empleado.getEmpleado().getId_empleado())
                .fecha_trabajada(evento_empleado.getFecha_trabajada())
                .build();
    }

    public ResponseEntity<String> modificarFechaTrabajada(Evento_empleadoDTO evento_empleadoDTO){
        Evento_empleado evento_empleado = eventoEmpleadoRepository.findById(evento_empleadoDTO.getId_evento_empleado()).orElse(null);
        if( evento_empleado == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la relacion");
        }
        Evento_empleado fechaNueva = Evento_empleado.builder()
                .fecha_trabajada(evento_empleadoDTO.getFecha_trabajada() != null ? evento_empleadoDTO.getFecha_trabajada() : evento_empleado.getFecha_trabajada())
                .build();
        eventoEmpleadoRepository.save(fechaNueva);
        return ResponseEntity.ok("Fecha modificada");
    }

    //La relacion deberia eliminarse cuando elimino el evento o empleado....


}
