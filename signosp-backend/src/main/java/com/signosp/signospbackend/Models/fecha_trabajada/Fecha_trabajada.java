package com.signosp.signospbackend.Models.fecha_trabajada;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="fecha_trabajada")
public class Fecha_trabajada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_fecha_trabajada;
    @JoinColumn(name="id_evento_empleado")
    private Long id_evento_empleado;
    @JoinColumn(name="fecha_trabajada")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fecha_trabajada;

}
