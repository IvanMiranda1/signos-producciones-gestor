package com.signosp.signospbackend.Controller;

import com.signosp.signospbackend.Models.paquete.*;
import com.signosp.signospbackend.Service.PaqueteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paquete")
@RequiredArgsConstructor
public class PaqueteController {
    public final PaqueteRepository paqueteRepository;
    public final PaqueteService paqueteService;

    @GetMapping
        public List<PaqueteDTO> getAlls(){
            return paqueteService.findAll();
        }
    @GetMapping("/bxn/paquete")
    public PaqueteDTO getPaqueteNombre(@PathVariable String nombre){
        return paqueteService.getPaquetePorNombre(nombre);
    }

    @GetMapping("/{id}")
    public PaqueteDTO getPaqueteById(@PathVariable Long id){
        return paqueteService.getPaqueteById(id);
    }
    @PostMapping
    public void crearPaquete(@RequestBody PaqueteDTO nuevoPaquete){
        paqueteService.crearPaquete(nuevoPaquete);
    }

    @PutMapping
    public void modificarPaquete(@RequestBody PaqueteDTO paquete){
        paqueteService.modificarPaquete(paquete);
    }

    @DeleteMapping
    public void eliminarCliente(@PathVariable Long id_paquete){
        paqueteService.eliminarPaquete(id_paquete);
    }
    @GetMapping("/completo/{id_paquete}")
    public PaqueteMDEyServiciosDTO paqueteCompleto(@PathVariable Long id_paquete){
        return paqueteService.paqueteCompleto(id_paquete);
    }
    @PostMapping("/asignar")
    public void asignarServicios(@RequestBody ServicioXPaqueteDTO servicioXPaqueteDTO){
        paqueteService.asignarServicio(servicioXPaqueteDTO);
    }

    @DeleteMapping("/{paqueteId}/servicio/{servicioId}")
    public void eliminarRelacion(@PathVariable Long paqueteId, @PathVariable Long servicioId) {
        paqueteService.eliminarRelacion(paqueteId, servicioId);
    }
}