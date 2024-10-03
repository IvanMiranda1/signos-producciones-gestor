package com.signosp.signospbackend.Controller;

import com.signosp.signospbackend.Models.cliente.ClienteRepository;
import com.signosp.signospbackend.Models.comentario.ComentarioDTO;
import com.signosp.signospbackend.Models.empleado.EmpleadoDTO;
import com.signosp.signospbackend.Models.evento.*;
import com.signosp.signospbackend.Models.evento_empleado.Evento_empleadoDTO;
import com.signosp.signospbackend.Models.pago.PagoConCuotasDTO;
import com.signosp.signospbackend.Models.subtarea.SubtareaDTO;
import com.signosp.signospbackend.Service.EventoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evento")
@RequiredArgsConstructor
public class EventoController {
    public final EventoRepository eventoRepository;
    public final EventoService eventoService;
    public final ClienteRepository clienteRepository;

    @GetMapping
    public List<EventoDTO> getAllEventos(){
        return eventoService.findAll();
    }

    @GetMapping("/{id}")
    public EventoFull eventoById(@PathVariable Long id){
        return eventoService.byId(id);
    }

    @PostMapping
    public void crearEvento(@RequestBody EventoFull eventoDTO){
        eventoService.crearEvento(eventoDTO);
    }

    @PutMapping
    public void modificarEvento(@RequestBody EventoFull eventoDTO){
        eventoService.modificarEvento(eventoDTO);
    }

    @Transactional
    @PostMapping("/eliminar")
    public ResponseEntity<String>eliminarEvento(@RequestParam Long id_evento, @RequestParam(required = false, defaultValue = "false") Boolean eliminarCliente, @RequestParam(required = false, defaultValue = "false") Boolean soloEvento) {
        Evento evento = eventoRepository.findById(id_evento)
                .orElseThrow(() -> new EntityNotFoundException("No se encuentra el evento"));
        List<Evento> eventosCliente = eventoRepository.findById_cliente(evento.getCliente().getId_cliente());

        //El primer mensaje que se ve es este, una vez comprobado que el cliente esta asociado a un solo evento,
        //se decide si eliminar cliente o solo el evento...
        if (eventosCliente.size() == 1) {
            if (eliminarCliente == true) {
                eventoRepository.deleteRelacionEmpleado(id_evento);
                eventoRepository.deleteRelacionComentarios(id_evento);
                eventoRepository.deleteRelacionCuotas(id_evento);
                eventoRepository.deleteRelacionPago(id_evento);
                eventoRepository.deleteRelacionSubtareas(id_evento);
                eventoRepository.deleteRelacionComentarios(id_evento);
                eventoRepository.deleteById(id_evento);
                clienteRepository.deleteById(evento.getCliente().getId_cliente());
                return ResponseEntity.ok("Evento eliminnado junto al cliente.");
            } else if (soloEvento == true) {
                eventoRepository.deleteRelacionEmpleado(id_evento);
                eventoRepository.deleteRelacionComentarios(id_evento);
                eventoRepository.deleteRelacionCuotas(id_evento);
                eventoRepository.deleteRelacionPago(id_evento);
                eventoRepository.deleteRelacionSubtareas(id_evento);
                eventoRepository.deleteRelacionComentarios(id_evento);
                eventoRepository.deleteById(id_evento);
                return ResponseEntity.ok("Evento eliminado.\nEl cliente sigue disponible.");
            }

            return ResponseEntity.ok("El cliente esta asociado solo a este evento.\n¿Desea eliminar el cliente?");
        }

        //Si cuando se comprobo, el cliente asociado al evento estaba en mas de un evento.
        // Entonces se pregunta, si esta seguro de eliminar el evento.
         if (eventosCliente.size() > 1) {
             if (soloEvento == true) {
                 eventoRepository.deleteRelacionEmpleado(id_evento);
                 eventoRepository.deleteRelacionComentarios(id_evento);
                 eventoRepository.deleteRelacionCuotas(id_evento);
                 eventoRepository.deleteRelacionPago(id_evento);
                 eventoRepository.deleteRelacionSubtareas(id_evento);
                 eventoRepository.deleteRelacionComentarios(id_evento);
                 eventoRepository.deleteById(id_evento);
                 ResponseEntity.ok("Evento eliminado.\nEl cliente sigue disponible.");
             }
            return ResponseEntity.ok("El cliente esta asociado a mas de un evento.\n¿Desea eliminar solo el evento?");// si el cliente tiene mas de un evento se muestra este mensaje primero
        }
        return ResponseEntity.ok("Operacion cancelada...");
    }


    @GetMapping("/busqueda/categoria/{categoria}")
    public List<EventoDTO> busquedaPorCategoria(@PathVariable String categoria){
        return eventoService.eventosPorCategoria(categoria);
    }

    @GetMapping("/empleados/{id}")
    public List<Evento_empleadoDTO> obtenerRelacionEmpleados(@PathVariable Long id){
        return eventoService.obtenerEmpleados(id);
    }

    @GetMapping("/empleadosfull/{id}")
    public List<EmpleadoDTO> obtenerEmpleadosFull(@PathVariable Long id){
        return eventoService.obtenerEmpleadosFull(id);
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

    @PutMapping("/estado/{id_evento}")
    public ResponseEntity<String> cambiarEstado(@PathVariable Long id_evento){
        return eventoService.cambiarEstado(id_evento);
    }

    @PostMapping("/listado")
    public List<EventoCPagos> listadoxfechas(@RequestBody DatesRequest datesRequest) {
        return eventoService.busqEventosXfechas(datesRequest);
    }

    /*
    @GetMapping("/{id}/completo")
    public EventoFull getEventoFull(@PathVariable Long id){
        return eventoService.getEventoFull(id);
    }
    */
}
