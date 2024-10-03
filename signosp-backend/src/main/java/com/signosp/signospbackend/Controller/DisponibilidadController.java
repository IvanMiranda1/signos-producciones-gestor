package com.signosp.signospbackend.Controller;

import com.signosp.signospbackend.Models.disponibilidad.DisponibilidadDTO;
import com.signosp.signospbackend.Models.disponibilidad.DisponibilidadRepository;
import com.signosp.signospbackend.Service.DisponibilidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/disponibilidad")
@RequiredArgsConstructor
public class DisponibilidadController {
    public final DisponibilidadService disponibilidadService;

    @GetMapping
    public List<DisponibilidadDTO> getAlldisponibilidads(){
        return disponibilidadService.findAll();
    }
    @GetMapping("/{id}")
    public DisponibilidadDTO disponibilidadById(@PathVariable Long id){

        return disponibilidadService.byId(id);
    }
    @GetMapping("/empleado/{id}")
    public List<DisponibilidadDTO> disponibilidadxEmpleado(@PathVariable Long id){
        return disponibilidadService.disponibilidadxidEmpleado(id);
    }


    @PostMapping
    public void creardisponibilidad(@RequestBody DisponibilidadDTO disponibilidadDTO){
        disponibilidadService.crearDisponibilidad(disponibilidadDTO);
    }
    @PutMapping
    public void modificardisponibilidad(@RequestBody DisponibilidadDTO disponibilidadDTO){
        disponibilidadService.modificarDisponibilidad(disponibilidadDTO);
    }
    @DeleteMapping
    public void eliminardisponibilidad(@PathVariable Long id){
        disponibilidadService.eliminarDisponibilidad(id);
    }


}
