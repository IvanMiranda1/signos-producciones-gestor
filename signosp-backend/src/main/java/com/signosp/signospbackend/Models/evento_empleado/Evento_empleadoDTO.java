package com.signosp.signospbackend.Models.evento_empleado;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Evento_empleadoDTO {
    private Long id_evento_empleado;
    private Long id_evento;
    private Long id_empleado;
}
