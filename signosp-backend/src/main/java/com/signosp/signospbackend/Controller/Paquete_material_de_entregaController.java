package com.signosp.signospbackend.Controller;

import com.signosp.signospbackend.Models.paquete_material_de_entrega.Paquete_material_de_entregaDTO;
import com.signosp.signospbackend.Service.Paquete_material_de_entregaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/paq_mat")
@RequiredArgsConstructor
public class Paquete_material_de_entregaController {
    public final Paquete_material_de_entregaService service;
    @PostMapping
    public void crearrelacion (@RequestBody Paquete_material_de_entregaDTO rec){
        service.crearPaqueteMaterial(rec);
    }
    public void modificarCantidad(Paquete_material_de_entregaDTO recMod){
        service.modificarRelacion(recMod);
    }
}
