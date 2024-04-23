package com.signosp.signospbackend.Models.evento;

import com.signosp.signospbackend.Models.categoria.Categoria;
import com.signosp.signospbackend.Models.cliente.Cliente;
import com.signosp.signospbackend.Models.paquete.Paquete;
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
@Table(name = "evento")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_evento;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha")
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date fecha;
    @ManyToOne
    @JoinColumn(name="id_cliente", nullable = true)
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name="id_paquete")
    private Paquete paquete;
    @ManyToOne
    @JoinColumn(name="id_categoria")
    private Categoria categoria;
}
