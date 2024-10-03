package com.signosp.signospbackend.Models.evento;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.signosp.signospbackend.Models.cliente.ClienteDTO;
import com.signosp.signospbackend.Models.pago.PagoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoFull {
    private Long id_evento;
    private String titulo;
    private Boolean estado;
    private ClienteDTO cliente;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fecha;
    private Long id_paquete;
    private Long id_categoria;
    private PagoDTO pago;
    private List<Long> empleados;
}
