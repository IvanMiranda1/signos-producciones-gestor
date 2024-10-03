package com.signosp.signospbackend.Models.disponibilidad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisponibilidadDTO {

    private Long id_disponibilidad;
    private Long id_empleado;
    private String dia;
    private Integer desde;
    private Integer hasta;

}
