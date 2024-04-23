package com.signosp.signospbackend.Models.empleado;

import com.signosp.signospbackend.Models.especialidad.EspecialidadDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoDTO {
    private Long id_empleado;
    private String nomyape;
    private String correo;
    private String telefono;
    private Set<EspecialidadDTO> especialidadDTO;

}
