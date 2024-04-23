package com.signosp.signospbackend.Models.empleado;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.signosp.signospbackend.Models.especialidad.Especialidad;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "empleado")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_empleado;
    @Column(name = "nomyape")
    private String nomyape;
    @Column(name = "correo")
    private String correo;
    @Column(name = "telefono")
    private String telefono;
    @ManyToMany
    @JoinTable(
            name = "empleado_especialidad",
            joinColumns = @JoinColumn(name = "id_empleado"),
            inverseJoinColumns = @JoinColumn(name = "id_especialidad")
    )
    @JsonProperty("especialidad")
    private Set<Especialidad> especialidad = new HashSet<>();
}
