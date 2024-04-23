package com.signosp.signospbackend.Models.pago;

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
@Table(name = "pago")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pago;
    @ManyToOne
    @JoinColumn(name="id_evento")
    private Evento evento;
    @Column(name = "forma_de_pago")
    private String forma_de_pago;
    @Column(name = "cant_cuotas")
    private Integer cant_cuotas;
}
