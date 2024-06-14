package com.signosp.signospbackend.Models.cuota;


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
public class CuotaDTO {
    private CuotaId id_cuota;
    private Long id_pago;
    private Integer nro_cuota;
    private Float monto;
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date fecha_de_pago;

}