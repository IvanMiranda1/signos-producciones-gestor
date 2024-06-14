package com.signosp.signospbackend.Models.paquete_material_de_entrega;

import com.signosp.signospbackend.Models.material_de_entrega.Material_de_entregaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Paquete_material_de_entregaDTO2 {
    private Long id_paquete_material_de_entrega;
    private Long id_paquete;
    private Material_de_entregaDTO material_de_entrega;
    private Integer cantidad;
}
