package com.signosp.signospbackend.Models.subtarea;

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
@Table(name="subtarea")
public class Subtarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_subtarea;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "estado")
    private Boolean estado;

}
