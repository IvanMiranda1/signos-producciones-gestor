package com.signosp.signospbackend.Models.paquete;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.signosp.signospbackend.Models.servicio.Servicio;
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
@Table(name="paquete")
public class Paquete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_paquete;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "precio")
    private Integer precio;
    @Column(name = "detalles")
    private String detalles;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "paquete_servicio",
            joinColumns = @JoinColumn(name = "id_paquete"),
            inverseJoinColumns = @JoinColumn(name = "id_servicio")
    )
    private Set<Servicio> servicios = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paquete paquete = (Paquete) o;
        return Objects.equals(id_paquete, paquete.id_paquete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_paquete);
    }

    public void addServicio(Servicio servicio){
        this.servicios.add(servicio);
        servicio.getPaquetes().add(this);
    }

    public void removeServicio(Servicio servicio){
        this.servicios.remove(servicio);
        servicio.getPaquetes().remove(this);
    }
}
