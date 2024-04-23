package com.signosp.signospbackend.Models.evento;


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
public class EventoDTO {
    private Long id_evento;
    private Boolean estado;
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date fecha;
    private Long id_cliente;
    private Long id_paquete;
    private Long id_categoria;
}
