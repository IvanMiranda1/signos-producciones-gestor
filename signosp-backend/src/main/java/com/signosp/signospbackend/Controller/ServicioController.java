package com.signosp.signospbackend.Controller;

import com.signosp.signospbackend.Models.servicio.Servicio;
import com.signosp.signospbackend.Models.servicio.ServicioDTO;
import com.signosp.signospbackend.Service.ServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/servicio")
@RequiredArgsConstructor
public class ServicioController {
    public final ServicioService servicioService;
    @GetMapping
        public List<ServicioDTO> getAllServicios(){
            return servicioService.findAll();
        }
        @GetMapping("/{id}")
        public ServicioDTO servicioById(@PathVariable Long id){
            return servicioService.byId(id);
        }
        @PostMapping
        public void crearServicio(@RequestBody ServicioDTO servicioDTO){
            servicioService.crearServicio(servicioDTO);
        }
        @PutMapping
        public void modificarServicio(@RequestBody ServicioDTO servicioDTO){
            servicioService.modificarServicio(servicioDTO);
        }
        @DeleteMapping
        public void eliminarServicio(@PathVariable Long id){
            servicioService.eliminarServicio(id);
        }
}
