package com.signosp.signospbackend.Controller;

import com.signosp.signospbackend.Models.empleado.Empleado;
import com.signosp.signospbackend.Models.empleado.EmpleadoDTO;
import com.signosp.signospbackend.Service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/empleado")
@RequiredArgsConstructor
public class EmpleadoController {
    public final EmpleadoService empleadoService;
    @GetMapping
        public List<EmpleadoDTO> getAllEmpleados(){
            return empleadoService.findAll();
        }
        @GetMapping("/{id}")
        public EmpleadoDTO empleadoById(@PathVariable Long id){
            return empleadoService.byId(id);
        }
        @PostMapping
        public void crearEmpleado(@RequestBody EmpleadoDTO empleadoDTO){
            empleadoService.crearEmpleado(empleadoDTO);
        }
        @PutMapping
        public void modificarEmpleado(@RequestBody EmpleadoDTO empleadoDTO){
            empleadoService.modificarEmpleado(empleadoDTO);
        }
        @DeleteMapping
        public void eliminarEmpleado(@PathVariable Long id){
            empleadoService.eliminarEmpleado(id);
        }
}
