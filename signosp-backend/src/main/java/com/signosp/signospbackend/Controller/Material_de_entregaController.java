package com.signosp.signospbackend.Controller;

import com.signosp.signospbackend.Models.material_de_entrega.Material_de_entregaDTO;
import com.signosp.signospbackend.Service.Material_de_entregaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/material")
@RequiredArgsConstructor
public class  Material_de_entregaController {
    public final Material_de_entregaService material_de_entregaService;
    @GetMapping
        public List<Material_de_entregaDTO> getAllMaterial_de_entregas(){
            return material_de_entregaService.findAll();
        }
        @GetMapping("/{id}")
        public Material_de_entregaDTO material_de_entregaById(@PathVariable Long id){
            return material_de_entregaService.byId(id);
        }
        @PostMapping
        public void crearMaterial_de_entrega(@RequestBody Material_de_entregaDTO material_de_entregaDTO){
            material_de_entregaService.crearMaterialDeEntrega(material_de_entregaDTO);
        }
        @PutMapping
        public void modificarMaterial_de_entrega(@RequestBody Material_de_entregaDTO material_de_entregaDTO){
            material_de_entregaService.modificarMaterialDeEntrega(material_de_entregaDTO);
        }
        @DeleteMapping
        public void eliminarMaterial_de_entrega(@PathVariable Long id){
            material_de_entregaService.eliminarMDE(id);
        }
}
