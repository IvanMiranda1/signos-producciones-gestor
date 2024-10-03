package com.signosp.signospbackend.Controller;

import com.signosp.signospbackend.Models.empleado.Empleado;
import com.signosp.signospbackend.Models.empleado.EmpleadoDTO;
import com.signosp.signospbackend.Models.empleado.EmpleadoFull;
import com.signosp.signospbackend.Models.empleado.EmpleadoxDispo;
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
    @GetMapping("/completo/{id}")
    public EmpleadoFull empleadocompleto(@PathVariable Long id){
        return empleadoService.byIdFull(id);
    }
    @PostMapping
    public void crearEmpleado(@RequestBody EmpleadoxDispo empleadoDTO){
        empleadoService.crearEmpleado(empleadoDTO);
    }
    @PutMapping
    public void modificarEmpleado(@RequestBody EmpleadoFull empleadoDTO){
        empleadoService.modificarEmpleado(empleadoDTO);
    }
    @DeleteMapping("/{id}")
    public void eliminarEmpleado(@PathVariable Long id){
            empleadoService.eliminarEmpleado(id);
        }
}
