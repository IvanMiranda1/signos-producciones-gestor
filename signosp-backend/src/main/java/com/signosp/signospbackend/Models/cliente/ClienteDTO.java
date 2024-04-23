package com.signosp.signospbackend.Models.cliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Long id_cliente;
    private String nomyape;
    private String correo;
    private String telefono;

}