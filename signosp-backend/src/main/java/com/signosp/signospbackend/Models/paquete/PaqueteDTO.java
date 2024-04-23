package com.signosp.signospbackend.Models.paquete;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaqueteDTO {
    private Long id_paquete;
    private String nombre;
    private Integer precio;
    private String detalles;
}
