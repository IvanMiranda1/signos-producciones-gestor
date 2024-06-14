package com.signosp.signospbackend.Controller;

import com.signosp.signospbackend.Models.cuota.CuotaDTO;
import com.signosp.signospbackend.Models.pago.PagoDTO;
import com.signosp.signospbackend.Service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pago")
@RequiredArgsConstructor
public class PagoController {
    public final PagoService pagoService;

    @GetMapping
    public List<PagoDTO> getAllPagos(){
        return pagoService.findAll();
    }
    @GetMapping("/{id}")
    public PagoDTO pagoById(@PathVariable Long id){
        return pagoService.byId(id);
    }

    @PostMapping
    public void crearPago(@RequestBody PagoDTO pagoDTO){
        pagoService.crearPago(pagoDTO);
    }

    @PutMapping
    public void modificarPago(@RequestBody PagoDTO pagoDTO){
        pagoService.modificarPago(pagoDTO);
    }

    @DeleteMapping
    public void eliminarPago(@PathVariable Long id){
            pagoService.eliminarPago(id);
        }
}
