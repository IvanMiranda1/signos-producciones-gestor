package com.signosp.signospbackend.Controller;

import com.signosp.signospbackend.Models.evento_empleado.Evento_empleado;
import com.signosp.signospbackend.Models.evento_empleado.Evento_empleadoDTO;
import com.signosp.signospbackend.Models.fecha_trabajada.Fecha_trabajadaDTO;
import com.signosp.signospbackend.Service.Evento_empleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eve_emp")
@RequiredArgsConstructor
public class Evento_empleadoController {
    public final Evento_empleadoService service;

    @PostMapping
    public void crearRelacion(@RequestBody Evento_empleadoDTO rec) {
        service.crearEvento_empleado(rec);
    }

    @GetMapping
    public List<Fecha_trabajadaDTO> getDiasTrabajados(@RequestBody Evento_empleadoDTO evem){
        return service.getDiasTrabajados(evem);
    }
}
