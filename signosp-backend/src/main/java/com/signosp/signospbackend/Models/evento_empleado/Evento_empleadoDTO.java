package com.signosp.signospbackend.Models.evento_empleado;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Evento_empleadoDTO {
    private Long id_evento_empleado;
    private Long id_evento;
    private Long id_empleado;
    private Date fecha_trabajada;
}
