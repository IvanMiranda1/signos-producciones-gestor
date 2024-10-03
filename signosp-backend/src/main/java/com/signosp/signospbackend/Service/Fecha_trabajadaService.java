package com.signosp.signospbackend.Service;

import com.signosp.signospbackend.Models.fecha_trabajada.Fecha_trabajada;
import com.signosp.signospbackend.Models.fecha_trabajada.Fecha_trabajadaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Fecha_trabajadaService {

    /**
    public ResponseEntity<String> modificarFechaTrabajada(Evento_empleadoDTO evento_empleadoDTO){
        Evento_empleado evento_empleado = eventoEmpleadoRepository.findById(evento_empleadoDTO.getId_evento_empleado()).orElse(null);
        if( evento_empleado == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la relacion");
        }

        Evento_empleado fechaNueva = Evento_empleado.builder()
                .build();
        eventoEmpleadoRepository.save(fechaNueva);
        return ResponseEntity.ok("Fecha modificada");
    }
     */

    public Fecha_trabajadaDTO convertirFechaDTO(Fecha_trabajada fecha_trabajada){
        return Fecha_trabajadaDTO.builder()
                .id_fecha_trabajada(fecha_trabajada.getId_fecha_trabajada())
                .id_evento_empleado(fecha_trabajada.getId_evento_empleado())
                .fecha_trabajada(fecha_trabajada.getFecha_trabajada())
                .build();
    }

}
