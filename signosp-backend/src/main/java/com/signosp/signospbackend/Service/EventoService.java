package com.signosp.signospbackend.Service;

import com.signosp.signospbackend.Models.categoria.Categoria;
import com.signosp.signospbackend.Models.categoria.CategoriaRepository;
import com.signosp.signospbackend.Models.cliente.Cliente;
import com.signosp.signospbackend.Models.cliente.ClienteRepository;
import com.signosp.signospbackend.Models.comentario.ComentarioDTO;
import com.signosp.signospbackend.Models.empleado.Empleado;
import com.signosp.signospbackend.Models.empleado.EmpleadoDTO;
import com.signosp.signospbackend.Models.empleado.EmpleadoRepository;
import com.signosp.signospbackend.Models.evento.*;
import com.signosp.signospbackend.Models.evento_empleado.Evento_empleado;
import com.signosp.signospbackend.Models.evento_empleado.Evento_empleadoDTO;
import com.signosp.signospbackend.Models.evento_empleado.Evento_empleadoRepository;
import com.signosp.signospbackend.Models.pago.Pago;
import com.signosp.signospbackend.Models.pago.PagoConCuotasDTO;
import com.signosp.signospbackend.Models.pago.PagoDTO;
import com.signosp.signospbackend.Models.pago.PagoRepository;
import com.signosp.signospbackend.Models.paquete.Paquete;
import com.signosp.signospbackend.Models.paquete.PaqueteRepository;
import com.signosp.signospbackend.Models.subtarea.SubtareaDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;


@Service
@RequiredArgsConstructor
public class EventoService {
    public final EventoRepository eventoRepository;
    public final ClienteRepository clienteRepository;
    public final ClienteService clienteService;
    public final PaqueteRepository paqueteRepository;
    public final CategoriaRepository categoriaRepository;
    public final Evento_empleadoService relacionService;
    public final Evento_empleadoRepository eventoEmpleadoRepository;
    public final SubtareaService subtareaService;
    public final PagoService pagoService;
    public final ComentarioService comentarioService;
    public final PagoRepository pagoRepository;
    public final EmpleadoRepository empleadoRepository;
    public final EmpleadoService empleadoService;

    public void crearEvento(EventoFull eventoDTO) {

        Paquete paquete = paqueteRepository.findById(eventoDTO.getId_paquete())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el paquete con ID: " + eventoDTO.getId_paquete()));
        Categoria categoria = categoriaRepository.findById(eventoDTO.getId_categoria())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la categoría con ID: " + eventoDTO.getId_categoria()));
        Evento nuevoEvento = Evento.builder()
                .estado(false)
                .titulo(eventoDTO.getTitulo())
                .fecha(eventoDTO.getFecha())
                .paquete(paquete)
                .categoria(categoria)
                .build();
        //cliente
        if (eventoDTO.getCliente().getId_cliente() != null) {
            Cliente cliente = clienteRepository.findById(eventoDTO.getCliente().getId_cliente())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
            nuevoEvento.setCliente(cliente);
        } else {
            Cliente clienteNuevo = Cliente.builder()
                    .nomyape(eventoDTO.getCliente().getNomyape())
                    .correo(eventoDTO.getCliente().getCorreo())
                    .telefono(eventoDTO.getCliente().getTelefono())
                    .build();
            clienteRepository.save(clienteNuevo);
            nuevoEvento.setCliente(clienteNuevo);
        }
        Evento createEvento = eventoRepository.save(nuevoEvento);

        Pago pago = Pago.builder()
                .forma_de_pago(eventoDTO.getPago().getForma_de_pago())
                .cant_cuotas(eventoDTO.getPago().getCant_cuotas())
                .evento(createEvento)
                .build();
        pagoRepository.save(pago);
        for (Long id_empleado : eventoDTO.getEmpleados()){
            Evento_empleadoDTO rec = Evento_empleadoDTO.builder()
                    .id_empleado(id_empleado)
                    .id_evento(createEvento.getId_evento())
                    .build();
            relacionService.crearEvento_empleado(rec);
        }
    }

    @Transactional
    public ResponseEntity<String> modificarEvento(EventoFull eventoDTO) {
        Evento evento = eventoRepository.findById(eventoDTO.getId_evento())
                .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado (modificar evento)"));

        Evento eventoModificado = Evento.builder()
                    .id_evento(evento.getId_evento())
                    .titulo(eventoDTO.getTitulo() != null ? eventoDTO.getTitulo() : evento.getTitulo())
                    .estado(eventoDTO.getEstado() != null ? eventoDTO.getEstado() : evento.getEstado())
                    .fecha(eventoDTO.getFecha() != null ? eventoDTO.getFecha() : evento.getFecha())
                    .categoria(eventoDTO.getId_categoria() != null ? categoriaRepository.findById(eventoDTO.getId_categoria()).orElseThrow(
                            () -> new EntityNotFoundException("Categoria no encontrada")
                    ) : evento.getCategoria())
                    .paquete(eventoDTO.getId_paquete() != null ? paqueteRepository.findById(eventoDTO.getId_paquete()).orElseThrow(
                            () -> new EntityNotFoundException("Paquete no encontrado")
                    ) : evento.getPaquete())
                    .build();

        //cliente
        if (eventoDTO.getCliente().getId_cliente() != null) {
            Cliente cliente = clienteRepository.findById(eventoDTO.getCliente().getId_cliente())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
            eventoModificado.setCliente(cliente);
        } else {
            Cliente clienteNuevo = Cliente.builder()
                    .nomyape(eventoDTO.getCliente().getNomyape())
                    .correo(eventoDTO.getCliente().getCorreo())
                    .telefono(eventoDTO.getCliente().getTelefono())
                    .build();
            clienteRepository.save(clienteNuevo);
            eventoModificado.setCliente(clienteNuevo);
        }

        eventoRepository.save(eventoModificado);

        Pago pago = pagoRepository.findByIdEvento(eventoDTO.getPago().getId_pago());
        if (pago != null) {
            pago.setCant_cuotas(eventoDTO.getPago().getCant_cuotas());
            pago.setForma_de_pago(eventoDTO.getPago().getForma_de_pago());
            pagoRepository.save(pago);
        }

        List<Evento_empleado> relacionesExistentes = eventoEmpleadoRepository.findEmpleadosxEvento(eventoDTO.getId_evento());
        Set<Long> idsNuevos = new HashSet<>(eventoDTO.getEmpleados());

        // Elimina las relaciones que ya no existen
        for (Evento_empleado rel : relacionesExistentes) {
            if (!idsNuevos.contains(rel.getEmpleado().getId_empleado())) {
                eventoEmpleadoRepository.delete(rel);
            } else {
                idsNuevos.remove(rel.getEmpleado().getId_empleado());
            }
        }

        for (Long idEmpleado : idsNuevos) {
            Evento_empleadoDTO nuevaRelacion = Evento_empleadoDTO.builder()
                    .id_empleado(idEmpleado)
                    .id_evento(eventoDTO.getId_evento())
                    .build();
            relacionService.crearEvento_empleado(nuevaRelacion);
        }

        return ResponseEntity.ok("Evento modificado");

    }


    public EventoDTO convertirEventoDTO(Evento evento) {
        return EventoDTO.builder()
                .id_evento(evento.getId_evento())
                .titulo(evento.getTitulo())
                .estado(evento.getEstado())
                .fecha(evento.getFecha())
                .id_cliente(evento.getCliente().getId_cliente())
                .id_paquete(evento.getPaquete().getId_paquete())
                .id_categoria(evento.getCategoria().getId_categoria())
                .build();

    }



    public ResponseEntity<String> eliminarEvento(Long id_evento){
        Evento evento = eventoRepository.findById(id_evento).orElseThrow(()-> new EntityNotFoundException("No se encuetra el evento"));
        List<Evento> listEventos /* tiene que ser uno */ = eventoRepository.findById_cliente(evento.getCliente().getId_cliente());
        if (listEventos.size() == 1) {
            // Solo hay un evento asociado al cliente
            // Opción: Eliminar el cliente también junto con el evento
            eventoRepository.deleteById(id_evento);
            clienteRepository.deleteById(evento.getCliente().getId_cliente());
            return ResponseEntity.ok("Evento y cliente eliminados, ya que no tenía más eventos asociados.");
        } else if (listEventos.size() > 1) {
            // Hay más de un evento asociado al cliente
            // Solo eliminar el evento y avisar al usuario que el cliente sigue existiendo
            eventoRepository.deleteById(id_evento);
            return ResponseEntity.ok("Evento eliminado. El cliente sigue teniendo otros eventos.");
        }

        // Caso de error o por si no ocurre ninguna de las condiciones anteriores
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el evento.");

    }

    public List<EventoDTO> eventosPorCategoria(String categoria){
        List<Evento> eventos = eventoRepository.findPorCategoria(categoria);
        eventos.sort(Comparator.comparing(Evento::getFecha));
        List<EventoDTO> list = new ArrayList<>();
        for (Evento evento: eventos){
            System.out.println(evento.getTitulo());
            System.out.println(evento.getFecha());
            System.out.println("\n\n");
            list.add(convertirEventoDTO(evento));
        }
        return list;
    }

    public List<EventoDTO> findAll() {
            List<Evento> a = eventoRepository.findAll();
            List<EventoDTO> b = new ArrayList<>();
            for(Evento c : a){
                b.add(convertirEventoDTO(c));
            }
            return b;
    }


    /*
    titulo: "",
    fecha: "",
    cliente: {
        id_cliente: null,
        nomyape: "",
        correo: "",
        telefono: ""
    },
    id_paquete: 0,
    id_categoria: 0,
    pago: {
        forma_de_pago: "",
        cant_cuotas: 0
    },
    empleados: []
    })
    * */

    public EventoFull byId(Long id) {
            Evento ev = eventoRepository.findById(id)
                    .orElseThrow(()-> new EntityNotFoundException("Evento"));
            PagoDTO pago = pagoService.findById(ev.getId_evento());
            List<Long> empleados = new ArrayList<>();
            for(Evento_empleadoDTO evem :obtenerEmpleados(ev.getId_evento())){
                empleados.add(evem.getId_empleado());
            }
            System.out.println(empleados);
            EventoFull eventoFull = EventoFull.builder()
                    .id_evento(ev.getId_evento())
                    .titulo(ev.getTitulo())
                    .fecha(ev.getFecha())
                    .estado(ev.getEstado())
                    .empleados(empleados)
                    .cliente(clienteService.convertirClienteDTO(ev.getCliente()))
                    .id_paquete(ev.getPaquete().getId_paquete())
                    .id_categoria(ev.getCategoria().getId_categoria())
                    .pago(pago)
                    .build();
            return eventoFull;
    }

    public List<Evento_empleadoDTO> obtenerEmpleados(Long idEvento) {
        return relacionService.findEmpleadosxEvento(idEvento);
    }
    /*public List<Evento_empleadoDTO> obtenerEmpleados(Long idEvento) {
        return relacionService.findEmpleadosxEvento(idEvento);
    }*/
    // Esta misma funcion tengo que hacerla con Paquete_material_de_entrega y con Evento_subtarea

    public List<SubtareaDTO> obtenerSubtareas(Long idEvento) {
        return subtareaService.findSubtareaXEvento(idEvento);
    }

    public PagoConCuotasDTO obtenerPagoDeEvento(Long idEvento) {
        return pagoService.obtenerPagoDeEvento(idEvento);
    }

    public List<ComentarioDTO> comentariosDeEvento(Long idEvento){
        return comentarioService.obtenerComentariosxIdEvento(idEvento);
    }

    @Modifying
    @Transactional
    public ResponseEntity<String> cambiarEstado(Long idEvento) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el evento"));

        evento.setEstado(!evento.getEstado());
        eventoRepository.save(evento);

        String mensaje = evento.getEstado()
                ? "El estado del evento es ahora Finalizado"
                : "El estado del evento es ahora Pendiente";

        return ResponseEntity.ok(mensaje);
    }

    public List<EmpleadoDTO> obtenerEmpleadosFull(Long idEvento) {
        Evento ev = eventoRepository.findById(idEvento)
                .orElseThrow(()-> new EntityNotFoundException("No se encontro un evento con esta id"));
        List<EmpleadoDTO> empleados = new ArrayList<>();
        for(Evento_empleadoDTO evem :obtenerEmpleados(ev.getId_evento())){
            Empleado busqEmpleado = empleadoRepository.findById(evem.getId_empleado()).orElse(null);
            if (busqEmpleado != null){
                empleados.add(empleadoService.convertirEmpleadoDTO(busqEmpleado));
            }
        }
        return empleados;
    }

    public List<EventoCPagos> busqEventosXfechas(DatesRequest datesRequest) {
        List<Evento> resultado = eventoRepository.findEventsXFechas(datesRequest.getDesde(),datesRequest.getHasta());
        List<EventoCPagos> listadoDTO = new ArrayList<>();
        for(Evento eventoR : resultado) {
            EventoDTO ev = convertirEventoDTO(eventoR);
            PagoConCuotasDTO pag = pagoService.obtenerPagoDeEvento(eventoR.getId_evento());
            EventoCPagos e = EventoCPagos.builder()
                    .evento(ev)
                    .pagoConCuotas(pag)
                    .build();
            listadoDTO.add(e);
        }
        return listadoDTO;
    }
}
