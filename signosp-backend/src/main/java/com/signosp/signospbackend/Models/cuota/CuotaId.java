package com.signosp.signospbackend.Models.cuota;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.signosp.signospbackend.Models.pago.Pago;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CuotaId implements Serializable {
    @ManyToOne
    @JoinColumn(name="id_pago")
    @JsonBackReference
    private Pago pago;
    @Column(name = "nro_cuota")
    private Integer nroCuota;
}
