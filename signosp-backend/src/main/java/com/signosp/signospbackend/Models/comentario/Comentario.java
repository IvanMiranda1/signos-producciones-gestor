package com.signosp.signospbackend.Models.comentario;

import com.signosp.signospbackend.Models.empleado.Empleado;
import com.signosp.signospbackend.Models.evento.Evento;
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
@Table(name="comentario")
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_comentario;
    @Column(name = "contenido")
    private String contenido;
    @ManyToOne
    @JoinColumn(name="id_evento")
    private Evento evento;
    @ManyToOne
    @JoinColumn(name="id_empleado")
    private Empleado empleado;

}
