package com.signosp.signospbackend.Models.paquete;

import com.signosp.signospbackend.Models.paquete_material_de_entrega.Paquete_material_de_entregaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaqueteDTO {
    private Long id_paquete;
    private String nombre;
    private Integer precio;
    private String detalles;
    private List<Long> servicios;
    private List<Paquete_material_de_entregaDTO> materiales;
}
