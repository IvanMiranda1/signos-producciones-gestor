package com.signosp.signospbackend.Models.servicio;

import com.signosp.signospbackend.Models.paquete.Paquete;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="servicio")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_servicio;
    @Column(name = "nombre")
    private String nombre;
    @ManyToMany(mappedBy = "servicios")
    private Set<Paquete> paquetes = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Servicio servicio = (Servicio) o;
        return Objects.equals(id_servicio, servicio.id_servicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_servicio);
    }

    public void addPaquete(Paquete paquete){
        this.paquetes.add(paquete);
        paquete.getServicios().add(this);
    }

    public void removePaquete(Paquete paquete){
        this.paquetes.remove(paquete);
        paquete.getServicios().remove(this);
    }
}