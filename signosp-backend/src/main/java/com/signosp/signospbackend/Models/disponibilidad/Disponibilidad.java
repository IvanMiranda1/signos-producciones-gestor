package com.signosp.signospbackend.Models.disponibilidad;

import com.signosp.signospbackend.Models.empleado.Empleado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "disponibilidad")
public class Disponibilidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_disponibilidad;
    @ManyToOne
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;
    @Column(name = "dia")
    private String dia;
    @Column(name = "horario")
    private Integer horario; // Representa la hora en formato militar (ej. "0930" para las 09:30 AM)
}
