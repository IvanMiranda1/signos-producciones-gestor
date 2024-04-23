package com.signosp.signospbackend.Models.especialidad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EspecialidadDTO {
    private Long id_especialidad;
    private String nombre;

}
