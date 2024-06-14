package com.signosp.signospbackend.Controller;

import com.signosp.signospbackend.Models.comentario.ComentarioDTO;
import com.signosp.signospbackend.Models.empleado.EmpleadoDTO;
import com.signosp.signospbackend.Models.evento.Evento;
import com.signosp.signospbackend.Models.evento.EventoDTO;
import com.signosp.signospbackend.Models.evento.EventoRepository;
import com.signosp.signospbackend.Models.evento_empleado.Evento_empleado;
import com.signosp.signospbackend.Models.evento_empleado.Evento_empleadoDTO;
import com.signosp.signospbackend.Models.pago.PagoConCuotasDTO;
import com.signosp.signospbackend.Models.pago.PagoDTO;
import com.signosp.signospbackend.Models.subtarea.SubtareaDTO;
import com.signosp.signospbackend.Service.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evento")
@RequiredArgsConstructor
public class EventoController {
    public final EventoRepository eventoRepository;
    public final EventoService eventoService;

    @GetMapping
    public List<EventoDTO> getAllEventos(){
        return eventoService.findAll();
    }

    @GetMapping("/{id}")
    public EventoDTO eventoById(@PathVariable Long id){
        return eventoService.byId(id);
    }

    @PostMapping
    public void crearEvento(@RequestBody EventoDTO eventoDTO){
        eventoService.crearEvento(eventoDTO);
    }

    @PutMapping
    public void modificarEvento(@RequestBody EventoDTO eventoDTO){
        eventoService.modificarEvento(eventoDTO);
    }

    @DeleteMapping
    public void eliminarEvento(@PathVariable Long id){
        eventoService.eliminarEvento(id);
        }

        @GetMapping("/busqueda/categoria/{categoria}")
    public List<Evento> busquedaPorCategoria(@PathVariable String categoria){
        return eventoService.eventosPorCategoria(categoria);
    }
    @GetMapping("/empleados/{id}")
    public List<Evento_empleadoDTO> obtenerRelacionEmpleados(@PathVariable Long id){
        return eventoService.obtenerEmpleados(id);
    }
    @GetMapping("/subtareas/{id}")
    public List<SubtareaDTO> obtenerSubtareasDelEvento(@PathVariable Long id){
        return eventoService.obtenerSubtareas(id);
    }
    @GetMapping("/pago/{id}")
    public PagoConCuotasDTO obtenerPagoDTO(@PathVariable Long id){
        return eventoService.obtenerPagoDeEvento(id);
    }

    @GetMapping("/comentarios/{id}")
    public List<ComentarioDTO> getComentariosDeEvento(@PathVariable Long id){
        return eventoService.comentariosDeEvento(id);
    }
}
