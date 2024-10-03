package com.signosp.signospbackend.Models.empleado;

import com.signosp.signospbackend.Models.disponibilidad.DisponibilidadDTO;
import com.signosp.signospbackend.Models.especialidad.EspecialidadDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoxDispo {
    private Long id_empleado;
    private String nomyape;
    private String correo;
    private String telefono;
    private List<Long> especialidad;
    private List<DisponibilidadDTO> disponibilidades;
}
