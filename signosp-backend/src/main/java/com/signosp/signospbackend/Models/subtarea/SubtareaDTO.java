package com.signosp.signospbackend.Models.subtarea;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubtareaDTO {
    private Long id_subtarea;
    private String nombre;
    private Boolean estado;

}
