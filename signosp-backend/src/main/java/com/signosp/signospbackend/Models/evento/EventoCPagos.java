package com.signosp.signospbackend.Models.evento;

import com.signosp.signospbackend.Models.pago.PagoConCuotasDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoCPagos {
    private EventoDTO evento;
    private PagoConCuotasDTO pagoConCuotas;
}
