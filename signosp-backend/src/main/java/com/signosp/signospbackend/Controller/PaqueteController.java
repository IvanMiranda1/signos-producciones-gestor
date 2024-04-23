package com.signosp.signospbackend.Controller;

import com.signosp.signospbackend.Models.paquete.Paquete;
import com.signosp.signospbackend.Models.paquete.PaqueteDTO;
import com.signosp.signospbackend.Models.paquete.PaqueteRepository;
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
    public List<Paquete> getAllPaquetes(){
        return paqueteRepository.findAll();
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
}
