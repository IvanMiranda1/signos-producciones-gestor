package com.signosp.signospbackend.Models.pago;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {
    private Long id_pago;
    private Long id_evento;
    private String forma_de_pago;
    private Integer cant_cuotas;

}
