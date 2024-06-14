package com.signosp.signospbackend.Models.pago;

import com.signosp.signospbackend.Models.cuota.CuotaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagoConCuotasDTO {
    private PagoDTO pagoDTO;
    private List<CuotaDTO> cuotas;
}
