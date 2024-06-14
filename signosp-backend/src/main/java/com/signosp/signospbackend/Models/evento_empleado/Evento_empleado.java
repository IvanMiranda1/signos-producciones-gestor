package com.signosp.signospbackend.Models.evento_empleado;

import com.signosp.signospbackend.Models.empleado.Empleado;
import com.signosp.signospbackend.Models.evento.Evento;
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
@Table(name="evento_empleado")
public class Evento_empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_evento_empleado;
    @ManyToOne
    @JoinColumn(name="id_evento")
    private Evento evento;
    @ManyToOne
    @JoinColumn(name="id_empleado")
    private Empleado empleado;
    @Column(name="fecha_trabajada")
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date fecha_trabajada;
}
