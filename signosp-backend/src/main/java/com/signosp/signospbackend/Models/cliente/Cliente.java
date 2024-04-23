package com.signosp.signospbackend.Models.cliente;

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
@Table(name="cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cliente;
    @Column(name = "nomyape")
    private String nomyape;
    @Column(name = "correo")
    private String correo;
    @Column(name = "telefono")
    private String telefono;

}
