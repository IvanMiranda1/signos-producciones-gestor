package com.signosp.signospbackend.Models.servicio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServicioDTO {
    private Long id_servicio;
    private String nombre;

}
