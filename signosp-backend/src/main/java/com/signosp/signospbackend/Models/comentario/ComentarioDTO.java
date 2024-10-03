package com.signosp.signospbackend.Models.comentario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioDTO {
    private Long id_comentario;
    private String contenido;
    private Long id_evento;
    private Long id_usuario;

}
