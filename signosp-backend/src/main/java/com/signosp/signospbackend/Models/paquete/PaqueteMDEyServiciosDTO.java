package com.signosp.signospbackend.Models.paquete;

import com.signosp.signospbackend.Models.material_de_entrega.Material_de_entregaDTO;
import com.signosp.signospbackend.Models.paquete_material_de_entrega.Paquete_material_de_entregaDTO2;
import com.signosp.signospbackend.Models.servicio.ServicioDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaqueteMDEyServiciosDTO {
    private PaqueteDTO paqueteDTO;
    private List<Paquete_material_de_entregaDTO2> paqueteMaterialDeEntregaDTOS;
    private List<ServicioDTO> servicioDTOS;
}
