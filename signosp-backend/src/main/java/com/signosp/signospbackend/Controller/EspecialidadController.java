package com.signosp.signospbackend.Controller;

import com.signosp.signospbackend.Models.especialidad.Especialidad;
import com.signosp.signospbackend.Models.especialidad.EspecialidadDTO;
import com.signosp.signospbackend.Service.EspecialidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/especialidad")
@RequiredArgsConstructor
public class EspecialidadController {
    public final EspecialidadService especialidadService;
    @GetMapping
        public List<EspecialidadDTO> getAllEspecialidads(){
            return especialidadService.findAll();
        }
        @GetMapping({"id"})
        public EspecialidadDTO especialidadById(@PathVariable Long id){
            return especialidadService.byId(id);
        }
        @PostMapping
        public void crearEspecialidad(@RequestBody EspecialidadDTO especialidadDTO){
            especialidadService.crearEspecialidad(especialidadDTO);
        }
        @PutMapping
        public void modificarEspecialidad(@RequestBody EspecialidadDTO especialidadDTO){
            especialidadService.modificarEspecialidad(especialidadDTO);
        }
        @DeleteMapping
        public void eliminarEspecialidad(@PathVariable Long id){
            especialidadService.eliminarEspecialidad(id);
        }
}
