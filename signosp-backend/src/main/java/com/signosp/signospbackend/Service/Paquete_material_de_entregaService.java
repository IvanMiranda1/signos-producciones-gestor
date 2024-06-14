package com.signosp.signospbackend.Service;

import com.signosp.signospbackend.Models.material_de_entrega.Material_de_entrega;
import com.signosp.signospbackend.Models.material_de_entrega.Material_de_entregaRepository;
import com.signosp.signospbackend.Models.paquete.Paquete;
import com.signosp.signospbackend.Models.paquete.PaqueteRepository;
import com.signosp.signospbackend.Models.paquete_material_de_entrega.Paquete_material_de_entrega;
import com.signosp.signospbackend.Models.paquete_material_de_entrega.Paquete_material_de_entregaDTO;
import com.signosp.signospbackend.Models.paquete_material_de_entrega.Paquete_material_de_entregaDTO2;
import com.signosp.signospbackend.Models.paquete_material_de_entrega.Paquete_material_de_entregaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Paquete_material_de_entregaService {
    public final Paquete_material_de_entregaRepository repository;
    public final PaqueteRepository paqueteRepository;
    public final Material_de_entregaRepository materialDeEntregaRepository;
    public final Material_de_entregaService materialDeEntregaService;

    public void crearPaqueteMaterial(Paquete_material_de_entregaDTO paquetematerial){
        Paquete paquete = paqueteRepository.findById(paquetematerial.getId_paquete())
                .orElseThrow(()-> new EntityNotFoundException("No se encontro el paquete"));
        Material_de_entrega material = materialDeEntregaRepository.findById(paquetematerial.getId_material_de_entrega())
                .orElseThrow(()-> new EntityNotFoundException("No se encontro el material entrega"));
        Paquete_material_de_entrega existeRelacionrepository = repository.existeRelacion(paquetematerial.getId_paquete(),paquetematerial.getId_material_de_entrega());
        if(existeRelacionrepository == null){
            Paquete_material_de_entrega nuevoPaqueteMaterial = Paquete_material_de_entrega.builder()
                    .paquete(paquete)
                    .material_de_entrega(material)
                    .cantidad(paquetematerial.getCantidad())
                    .build();
            repository.save(nuevoPaqueteMaterial);
        } else {
            System.out.print("No se puede crear una relacion igual");
        }

    }
    public Paquete_material_de_entregaDTO convertirRelacionDTO(Paquete_material_de_entrega paqueteMaterial){
        return Paquete_material_de_entregaDTO.builder()
                .id_paquete_material_de_entrega(paqueteMaterial.getId_paquete_material_de_entrega())
                .id_paquete(paqueteMaterial.getPaquete().getId_paquete())
                .id_material_de_entrega(paqueteMaterial.getMaterial_de_entrega().getId_material_de_entrega())
                .cantidad(paqueteMaterial.getCantidad())
                .build();
    }

    public Paquete_material_de_entregaDTO2 convertirRelacionDTO2(Paquete_material_de_entrega paqueteMaterial){
        return Paquete_material_de_entregaDTO2.builder()
                .id_paquete_material_de_entrega(paqueteMaterial.getId_paquete_material_de_entrega())
                .id_paquete(paqueteMaterial.getPaquete().getId_paquete())
                .material_de_entrega(materialDeEntregaService.convertirMDEDTO(paqueteMaterial.getMaterial_de_entrega()))
                .cantidad(paqueteMaterial.getCantidad())
                .build();
    } // Este DTO2 lo que hace es retornar el la relacion completa para asi tener el material de entrega con su nombre y la cantidad (en el otro solo se tiene la ID )

    public ResponseEntity<String> modificarRelacion(Paquete_material_de_entregaDTO materialDeEntrega){
        Paquete_material_de_entrega relacion = repository.findById(materialDeEntrega.getId_paquete_material_de_entrega())
                .orElse(null);
        if(relacion == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la relacion");
        }
        Paquete_material_de_entrega relacionNueva = Paquete_material_de_entrega.builder()
                .id_paquete_material_de_entrega(relacion.getId_paquete_material_de_entrega())
                .paquete(relacion.getPaquete())
                .material_de_entrega(relacion.getMaterial_de_entrega())
                .cantidad(materialDeEntrega.getCantidad() != null? materialDeEntrega.getCantidad():relacion.getCantidad())
                .build();
        repository.save(relacionNueva);
        return ResponseEntity.ok("Cantidad modificada");
    }

    public List<Paquete_material_de_entregaDTO2> findRelacXIdPaquete(Long idPaquete) {
        List<Paquete_material_de_entrega> list = repository.findRelaXidPaquete(idPaquete);
        List<Paquete_material_de_entregaDTO2> listDTO = new ArrayList<>();
        for (Paquete_material_de_entrega pmde : list){
            Paquete_material_de_entregaDTO2 p = Paquete_material_de_entregaDTO2.builder()
                            .id_paquete_material_de_entrega(pmde.getId_paquete_material_de_entrega())
                            .id_paquete(pmde.getPaquete().getId_paquete())
                            .material_de_entrega(materialDeEntregaService.convertirMDEDTO(pmde.getMaterial_de_entrega()))
                            .cantidad(pmde.getCantidad())
                            .build();
            listDTO.add(p);
        }
        return listDTO;
    }

}
