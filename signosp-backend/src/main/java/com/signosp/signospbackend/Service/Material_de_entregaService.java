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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Material_de_entregaService {
    public final Material_de_entregaRepository materialDeEntregaRepository;
    public final PaqueteRepository paqueteRepository;

    public void crearMaterialDeEntrega(Material_de_entregaDTO materialDeEntregaDTO){
        Material_de_entrega materialDeEntrega = Material_de_entrega.builder()
                .nombre(materialDeEntregaDTO.getNombre())
                .build();
        materialDeEntregaRepository.save(materialDeEntrega);
    }

    public Material_de_entregaDTO convertirMDEDTO(Material_de_entrega materialDeEntrega){
        return Material_de_entregaDTO.builder()
                .id_material_de_entrega(materialDeEntrega.getId_material_de_entrega())
                .nombre(materialDeEntrega.getNombre())
                .build();
    }

    public ResponseEntity<String> modificarMaterialDeEntrega(Material_de_entregaDTO materialDeEntregaDTO){
        Optional<Material_de_entrega> optionalMaterialDeEntrega = materialDeEntregaRepository.findById(materialDeEntregaDTO.getId_material_de_entrega());
        if(!optionalMaterialDeEntrega.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el material de entrega");
        }
        Material_de_entrega materialDeEntrega = optionalMaterialDeEntrega.get();

        Material_de_entrega materialMod = Material_de_entrega.builder()
                .id_material_de_entrega(materialDeEntrega.getId_material_de_entrega())
                .nombre(materialDeEntregaDTO.getNombre() != null ? materialDeEntregaDTO.getNombre() : materialDeEntrega.getNombre())
                .build();
        materialDeEntregaRepository.save(materialMod);
        return ResponseEntity.ok("Material de entrega modificado");
    }

    public ResponseEntity<String> eliminarMDE(Long id_material){
        List<Object> listMDE = paqueteRepository.findByIdMaterial(id_material);
        if (listMDE.isEmpty()){
            materialDeEntregaRepository.deleteById(id_material);
            return ResponseEntity.ok("Material de entrega eliminado exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se elimino.\nMaterial asociado a un paquete.");
        }
    }


    public List<Material_de_entregaDTO> findAll() {
            List<Material_de_entrega> a = materialDeEntregaRepository.findAll();
            List<Material_de_entregaDTO> b = new ArrayList<>();
            for(Material_de_entrega c : a){
                b.add(convertirMDEDTO(c));
            }
            return b;
        }
    public Material_de_entregaDTO byId(Long id) {
            Material_de_entrega a = materialDeEntregaRepository.findById(id)
                    .orElseThrow(()-> new EntityNotFoundException("Material_de_entrega"));
            return convertirMDEDTO(a);
        }
}
