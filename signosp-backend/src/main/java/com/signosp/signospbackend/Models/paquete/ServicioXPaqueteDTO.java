package com.signosp.signospbackend.Models.paquete;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServicioXPaqueteDTO {
    private Long id_paquete;
    private Set<Long> id_servicios;
}
