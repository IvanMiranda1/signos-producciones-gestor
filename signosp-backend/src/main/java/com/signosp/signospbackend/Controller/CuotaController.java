package com.signosp.signospbackend.Controller;

import com.signosp.signospbackend.Models.comentario.ComentarioDTO;
import com.signosp.signospbackend.Models.cuota.CuotaDTO;
import com.signosp.signospbackend.Service.CuotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuota")
@RequiredArgsConstructor
public class CuotaController {
    public final CuotaService cuotaService;

    @GetMapping
    public List<CuotaDTO> getAllcuotas(){
        return cuotaService.findAll();
    }
    @GetMapping("/{id}")
    public CuotaDTO cuotaById(@PathVariable Long id){
        return cuotaService.byId(id);
    }
    @PostMapping
    public void crearcuota(@RequestBody CuotaDTO cuotaDTO){
        cuotaService.crearCuota(cuotaDTO);
    }
    @PutMapping
    public void modificarcuota(@RequestBody CuotaDTO cuotaDTO){
        cuotaService.modificarMontoCuota(cuotaDTO);
    }
    @DeleteMapping
    public void eliminarcuota(@PathVariable Long id){
        cuotaService.eliminarCuota(id);
    }


}
