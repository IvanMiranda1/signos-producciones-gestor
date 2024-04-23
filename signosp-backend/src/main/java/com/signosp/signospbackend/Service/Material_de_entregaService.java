package com.signosp.signospbackend.Service;

import com.signosp.signospbackend.Models.material_de_entrega.Material_de_entrega;
import com.signosp.signospbackend.Models.material_de_entrega.Material_de_entregaDTO;
import com.signosp.signospbackend.Models.material_de_entrega.Material_de_entregaRepository;
import com.signosp.signospbackend.Models.paquete.Paquete;
import com.signosp.signospbackend.Models.paquete.PaqueteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Material_de_entregaService {
    public final Material_de_entregaRepository materialDeEntregaRepository;
    public final PaqueteRepository paqueteRepository;

    public void crearMaterialDeEntrega(Material_de_entregaDTO materialDeEntregaDTO){
        Paquete paquete = paqueteRepository.findById(materialDeEntregaDTO.getId_paquete())
                .orElseThrow(()-> new EntityNotFoundException("No se encontro el paquete"));
        Material_de_entrega materialDeEntrega = Material_de_entrega.builder()
                .paquete(paquete)
                .nombre(materialDeEntregaDTO.getNombre())
                .cantidad(materialDeEntregaDTO.getCantidad())
                .build();
        materialDeEntregaRepository.save(materialDeEntrega);
    }

    public Material_de_entregaDTO convertirMDEDTO(Material_de_entrega materialDeEntrega){
        return Material_de_entregaDTO.builder()
                .id_paquete(materialDeEntrega.getPaquete().getId_paquete())
                .nombre(materialDeEntrega.getNombre())
                .cantidad(materialDeEntrega.getCantidad())
                .build();
    }

    public ResponseEntity<String> modificarMaterialDeEntrega(Material_de_entregaDTO materialDeEntregaDTO){
        Material_de_entrega materialDeEntrega = materialDeEntregaRepository.findById(materialDeEntregaDTO.getId_material_de_entrega()).orElse(null);
        if(materialDeEntrega==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el material de entrega");
        }
        Paquete paquete = paqueteRepository.findById(materialDeEntregaDTO.getId_paquete())
                .orElseThrow(()-> new EntityNotFoundException("No se encontro el paquete"));
        Material_de_entrega materialMod = Material_de_entrega.builder()
                .id_material_de_entrega(materialDeEntrega.getId_material_de_entrega())
                .paquete(materialDeEntregaDTO.getId_paquete() != null ? paquete : materialDeEntrega.getPaquete())
                .nombre(materialDeEntregaDTO.getNombre() != null ? materialDeEntregaDTO.getNombre() : materialDeEntrega.getNombre())
                .cantidad(materialDeEntregaDTO.getCantidad() != null? materialDeEntregaDTO.getCantidad() : materialDeEntrega.getCantidad())
                .build();
        materialDeEntregaRepository.save(materialMod);
        return ResponseEntity.ok("Material de entrega modificado");
    }

    public void eliminarMDE(Long id_material){
        materialDeEntregaRepository.deleteById(id_material);
    }


}
