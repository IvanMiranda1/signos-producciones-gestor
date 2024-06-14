package com.signosp.signospbackend.Models.cuota;

import com.signosp.signospbackend.Models.pago.Pago;
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
@Table(name ="cuota")
public class Cuota {
    @EmbeddedId
    private CuotaId id_cuota;
    @ManyToOne
    @MapsId("pago") // lo mapea en cuotaid
    @JoinColumn(name = "id_pago", insertable = false, updatable = false)
    private Pago pago;
    @Column(name = "nro_cuota", insertable = false, updatable = false)
    private Integer nro_cuota;
    @Column(name="monto")
    private Float monto;
    @Column(name="fecha_de_pago")
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date fecha_de_pago;

}
