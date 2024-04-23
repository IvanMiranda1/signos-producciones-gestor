package com.signosp.signospbackend.Models.material_de_entrega;

import com.signosp.signospbackend.Models.paquete.Paquete;
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
@Table(name="material_de_entrega")
public class Material_de_entrega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_material_de_entrega;
    @ManyToOne
    @JoinColumn(name="id_paquete")
    private Paquete paquete;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "cantidad")
    private String cantidad;

}
