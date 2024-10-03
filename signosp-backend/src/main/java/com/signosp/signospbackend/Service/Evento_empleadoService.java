package com.signosp.signospbackend.Service;

import com.signosp.signospbackend.Models.empleado.Empleado;
import com.signosp.signospbackend.Models.empleado.EmpleadoDTO;
import com.signosp.signospbackend.Models.empleado.EmpleadoRepository;
import com.signosp.signospbackend.Models.evento.Evento;
import com.signosp.signospbackend.Models.evento.EventoRepository;
import com.signosp.signospbackend.Models.evento_empleado.Evento_empleado;
import com.signosp.signospbackend.Models.evento_empleado.Evento_empleadoDTO;
import com.signosp.signospbackend.Models.evento_empleado.Evento_empleadoRepository;
import com.signosp.signospbackend.Models.fecha_trabajada.Fecha_trabajada;
import com.signosp.signospbackend.Models.fecha_trabajada.Fecha_trabajadaDTO;
import com.signosp.signospbackend.Models.fecha_trabajada.Fecha_trabajadaRepository;
import com.signosp.signospbackend.Models.pago.PagoDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Evento_empleadoService {
    public final Evento_empleadoRepository eventoEmpleadoRepository;
    public final EventoRepository eventoRepository;
    public final EmpleadoRepository empleadoRepository;
    public final EmpleadoService empleadoService;
    public final Fecha_trabajadaRepository fechaTrabajadaRepository;
    public final Fecha_trabajadaService fechaTrabajadaService;

    public void crearEvento_empleado(Evento_empleadoDTO evento_empleadoDTO) {
        Evento evento = eventoRepository.findById(evento_empleadoDTO.getId_evento())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el evento con ID: " + evento_empleadoDTO.getId_evento()));

        Empleado empleado = empleadoRepository.findById(evento_empleadoDTO.getId_empleado())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el empleado con ID: " + evento_empleadoDTO.getId_empleado()));

        Evento_empleado nuevoEventoEmpleado = Evento_empleado.builder()
            .evento(evento)
            .empleado(empleado)
            .build();
        eventoEmpleadoRepository.save(nuevoEventoEmpleado);
    }

    public Evento_empleadoDTO convertirRelacionDTO(Evento_empleado evento_empleado){
        return Evento_empleadoDTO.builder()
                .id_evento_empleado(evento_empleado.getId_evento_empleado())
                .id_evento(evento_empleado.getEvento().getId_evento())
                .id_empleado(evento_empleado.getEmpleado().getId_empleado())
                .build();
    }

    public List<Evento_empleadoDTO> findEmpleadosxEvento(Long id_evento){
         List<Evento_empleado> recs = eventoEmpleadoRepository.findEmpleadosxEvento(id_evento);
         List<Evento_empleadoDTO> recsDTO = new ArrayList<>();
         for(Evento_empleado r : recs){
             recsDTO.add(convertirRelacionDTO(r));
         }
         return recsDTO;
    }

    public List<Fecha_trabajadaDTO> getDiasTrabajados(Evento_empleadoDTO evem) {
        Evento_empleado relacion = eventoEmpleadoRepository.findbyIds(evem.getId_evento(),evem.getId_empleado());
        List<Fecha_trabajada> list = fechaTrabajadaRepository.findxIdRelacion(relacion.getId_evento_empleado());
        List<Fecha_trabajadaDTO> listDTO = new ArrayList<>();
        for (Fecha_trabajada f : list){
            listDTO.add(fechaTrabajadaService.convertirFechaDTO(f));
        }
        return listDTO;
    }
    //La relacion deberia eliminarse cuando elimino el evento o empleado....


}
