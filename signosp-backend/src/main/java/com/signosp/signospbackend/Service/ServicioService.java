package com.signosp.signospbackend.Service;

import com.signosp.signospbackend.Models.paquete.Paquete;
import com.signosp.signospbackend.Models.paquete.PaqueteRepository;
import com.signosp.signospbackend.Models.servicio.Servicio;
import com.signosp.signospbackend.Models.servicio.ServicioDTO;
import com.signosp.signospbackend.Models.servicio.ServicioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServicioService {
    public final ServicioRepository servicioRepository;
    public final PaqueteRepository paqueteRepository;

    public void crearServicio(ServicioDTO servicioDTO) {
        Servicio nuevoServicio = Servicio.builder()
            .nombre(servicioDTO.getNombre())
            .build();
        servicioRepository.save(nuevoServicio);
    }

    public ResponseEntity modificarServicio(ServicioDTO servicioDTO){
        Optional<Servicio> optionalServicio = servicioRepository.findById(servicioDTO.getId_servicio());
        if(!optionalServicio.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el servicio");
        }
        Servicio servicio = optionalServicio.get();
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



    public ResponseEntity<String> eliminarServicio(Long id_servicio){
        List<Object> paquetes = paqueteRepository.findByIdServicio(id_servicio);
        if(paquetes.isEmpty()){
            servicioRepository.deleteById(id_servicio);
            return ResponseEntity.ok("Servicio eliminado exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se elimino.\nServicio asociado a un paquete.");
        }
    }

    public List<ServicioDTO> findAll() {
            List<Servicio> a = servicioRepository.findAll();
            List<ServicioDTO> b = new ArrayList<>();
            for(Servicio c : a){
                b.add(convertirServicioDTO(c));
            }
            return b;
    }
    public ServicioDTO byId(Long id) {
            Servicio a = servicioRepository.findById(id)
                    .orElseThrow(()-> new EntityNotFoundException("Servicio"));
            return convertirServicioDTO(a);
        }

    public List<ServicioDTO> findServiciosXidPaquete(Long idPaquete) {
        List<Long> listIds = servicioRepository.findServiciosXidPaquete(idPaquete);
        List<ServicioDTO> listServicio = new ArrayList<>();
        for(Long id : listIds){
            Servicio s = servicioRepository.findById(id).orElse(null);
            if(s != null){
                listServicio.add(convertirServicioDTO(s));
            }
        }
        return listServicio;
    }

    public Servicio findById(Long idServicio) {
        return servicioRepository.findById(idServicio).orElseThrow(()-> new EntityNotFoundException("No se encontro el servicio"));
    }
}
