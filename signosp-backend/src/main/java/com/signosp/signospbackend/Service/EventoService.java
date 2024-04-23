package com.signosp.signospbackend.Service;

import com.signosp.signospbackend.Models.categoria.Categoria;
import com.signosp.signospbackend.Models.categoria.CategoriaRepository;
import com.signosp.signospbackend.Models.cliente.Cliente;
import com.signosp.signospbackend.Models.cliente.ClienteRepository;
import com.signosp.signospbackend.Models.evento.Evento;
import com.signosp.signospbackend.Models.evento.EventoDTO;
import com.signosp.signospbackend.Models.evento.EventoRepository;
import com.signosp.signospbackend.Models.paquete.Paquete;
import com.signosp.signospbackend.Models.paquete.PaqueteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventoService {
    public final EventoRepository eventoRepository;
    public final ClienteRepository clienteRepository;
    public final PaqueteRepository paqueteRepository;
    public final CategoriaRepository categoriaRepository;
    public void crearEvento(EventoDTO eventoDTO) {
        Cliente cliente = clienteRepository.findById(eventoDTO.getId_cliente())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el cliente con ID: " + eventoDTO.getId_cliente()));
        Paquete paquete = paqueteRepository.findById(eventoDTO.getId_paquete())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el paquete con ID: " + eventoDTO.getId_paquete()));
        Categoria categoria = categoriaRepository.findById(eventoDTO.getId_categoria())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la categoría con ID: " + eventoDTO.getId_categoria()));
        Evento nuevoEvento = Evento.builder()
                .estado(eventoDTO.getEstado())
                .fecha(eventoDTO.getFecha())
                .cliente(cliente)
                .paquete(paquete)
                .categoria(categoria)
                .build();
        eventoRepository.save(nuevoEvento);
    }


    public EventoDTO getEventoById(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Error en getEventoById"));
        return convertirEventoDTO(evento);
    }

    public EventoDTO convertirEventoDTO(Evento evento) {
        return EventoDTO.builder()
                .id_evento(evento.getId_evento())
                .estado(evento.getEstado())
                .fecha(evento.getFecha())
                .id_cliente(evento.getCliente().getId_cliente())
                .id_paquete(evento.getPaquete().getId_paquete())
                .id_categoria(evento.getCategoria().getId_categoria())
                .build();

    }

    public ResponseEntity<String> modificarEvento(EventoDTO eventoDTO) {
        Evento evento = eventoRepository.findById(eventoDTO.getId_evento()).orElse(null);
        if(evento == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evento no encontrado (modificar evento)");
        }
        if(eventoDTO.getId_categoria() != null){
            Cliente cliente = clienteRepository.findById(eventoDTO.getId_cliente()).orElse(null);
        }
        Evento eventoModificado = Evento.builder()
                .id_evento(evento.getId_evento())
                .estado(eventoDTO.getEstado() != null ? eventoDTO.getEstado() : evento.getEstado())
                .fecha(eventoDTO.getFecha() != null ? eventoDTO.getFecha() : evento.getFecha())
                .cliente(eventoDTO.getId_cliente() != null ? clienteRepository.findById(eventoDTO.getId_cliente()).orElseThrow(
                        ()-> new EntityNotFoundException("Cliente no encontrado")
                ) : evento.getCliente() )
                .categoria(eventoDTO.getId_categoria() != null ? categoriaRepository.findById(eventoDTO.getId_categoria()).orElseThrow(
                        ()-> new EntityNotFoundException("Categoria no encontrada")
                ) : evento.getCategoria())
                .paquete(eventoDTO.getId_paquete() != null ? paqueteRepository.findById(eventoDTO.getId_paquete()).orElseThrow(
                        ()-> new EntityNotFoundException("Paquete no encontrado")
                ) : evento.getPaquete())
                .build();
        eventoRepository.save(eventoModificado);
        return ResponseEntity.ok("Evento modificado");
    }

    public ResponseEntity<String> eliminarEvento(Long id_evento){
        eventoRepository.deleteById(id_evento);
        return ResponseEntity.ok("Evento eliminado junto a los eventos");
    }

    public List<Evento> eventosPorCategoria(String categoria){
        List<Evento> eventos = eventoRepository.findPorCategoria(categoria);
        return eventos;
    }
}
