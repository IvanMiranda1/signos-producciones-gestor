package com.signosp.signospbackend.Models.material_de_entrega;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Material_de_entregaDTO {
    private Long id_material_de_entrega;
    private String nombre;

}
