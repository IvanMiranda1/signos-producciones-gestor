package com.signosp.signospbackend.Service;

import com.signosp.signospbackend.Models.servicio.Servicio;
import com.signosp.signospbackend.Models.servicio.ServicioDTO;
import com.signosp.signospbackend.Models.servicio.ServicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicioService {
    public final ServicioRepository servicioRepository;
    public void crearServicio(ServicioDTO servicioDTO) {
        Servicio nuevoServicio = Servicio.builder()
            .nombre(servicioDTO.getNombre())
            .build();
        servicioRepository.save(nuevoServicio);
    }
    public ResponseEntity modificarServicio(ServicioDTO servicioDTO){
        Servicio servicio = servicioRepository.findById(servicioDTO.getId_servicio()).orElse(null);
        if(servicio == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el servicio");
        }
        Servicio servicioMod = Servicio.builder()
                .id_servicio(servicio.getId_servicio())
                .nombre(servicioDTO.getNombre()!=null?servicioDTO.getNombre():servicio.getNombre())
                .build();
        servicioRepository.save(servicioMod);
        return ResponseEntity.ok("Servicio modificado");
    }
    public ServicioDTO convertirServicioDTO(Servicio servicio){
        return ServicioDTO.builder()
                .id_servicio(servicio.getId_servicio())
                .nombre(servicio.getNombre())
                .build();
    }
    public void eliminarServicio(Long id_servicio){
        servicioRepository.deleteById(id_servicio);
    }

}
