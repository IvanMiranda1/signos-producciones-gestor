package com.signosp.signospbackend.Models.paquete_material_de_entrega;

import com.signosp.signospbackend.Models.empleado.Empleado;
import com.signosp.signospbackend.Models.evento.Evento;
import com.signosp.signospbackend.Models.material_de_entrega.Material_de_entrega;
import com.signosp.signospbackend.Models.paquete.Paquete;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "paquete_material_de_entrega")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Paquete_material_de_entrega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_paquete_material_de_entrega;
    @ManyToOne
    @JoinColumn(name="id_paquete")
    private Paquete paquete;
    @ManyToOne
    @JoinColumn(name="id_material_de_entrega")
    private Material_de_entrega material_de_entrega;
    @Column(name="cantidad")
    private Integer cantidad;
}
