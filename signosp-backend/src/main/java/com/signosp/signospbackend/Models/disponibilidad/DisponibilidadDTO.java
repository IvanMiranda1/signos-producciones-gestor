package com.signosp.signospbackend.Models.disponibilidad;

import com.signosp.signospbackend.Models.empleado.Empleado;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private Integer horario;
}
