package com.signosp.signospbackend.Models.evento;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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
