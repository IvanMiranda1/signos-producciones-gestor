package com.signosp.signospbackend.Controller;

import com.signosp.signospbackend.Models.evento.Evento;
import com.signosp.signospbackend.Models.evento.EventoDTO;
import com.signosp.signospbackend.Models.evento.EventoRepository;
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
    public List<Evento> getAllCliente() {
        return eventoRepository.findAll();
    }

    @GetMapping("/{id}")
    public EventoDTO getEventoById(@PathVariable Long id){
        return eventoService.getEventoById(id);
    }

    @PostMapping
    public void CreateEvento(@RequestBody EventoDTO evento) {
        eventoService.crearEvento(evento);
    }

    @PutMapping
    public void modificarEvento(@RequestBody EventoDTO eventoDTO) {
     eventoService.modificarEvento(eventoDTO);
    }

    @GetMapping("/busqueda/categoria/{categoria}")
    public List<Evento> busquedaPorCategoria(@PathVariable String categoria){
        return eventoService.eventosPorCategoria(categoria);
    }

}
