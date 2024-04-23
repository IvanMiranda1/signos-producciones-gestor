package com.signosp.signospbackend.Models.cuota;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuotaDTO {
    private CuotaId id_cuota;
    private Long id_pago;
    private Integer nroCuota;
    private Float monto;
    private Date fecha_de_pago;

}
