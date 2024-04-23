package com.signosp.signospbackend.Service;

import com.signosp.signospbackend.Models.paquete.Paquete;
import com.signosp.signospbackend.Models.paquete.PaqueteDTO;
import com.signosp.signospbackend.Models.paquete.PaqueteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaqueteService {
    public final PaqueteRepository paqueteRepository;
    public void crearPaquete(PaqueteDTO paqueteDTO) {
        Paquete nuevoPaquete = Paquete.builder()
                .nombre(paqueteDTO.getNombre())
                .precio(paqueteDTO.getPrecio())
                .detalles(paqueteDTO.getDetalles())
                .build();
        paqueteRepository.save(nuevoPaquete);
    }

    public PaqueteDTO getPaquetePorNombre(String nombre) {
        Paquete p = paqueteRepository.findByNombre(nombre).orElse(null);
        PaqueteDTO resultado = convertirPaqueteDTO(p);
        return resultado;
    }

    private PaqueteDTO convertirPaqueteDTO(Paquete paquete) {
        return PaqueteDTO.builder()
                .id_paquete(paquete.getId_paquete())
                .nombre(paquete.getNombre())
                .precio(paquete.getPrecio())
                .detalles(paquete.getDetalles())
                .build();
    }

    public PaqueteDTO getPaqueteById(Long id) {
        Paquete paquete = paqueteRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Error en getPaqueteByID (service)"));
        return convertirPaqueteDTO(paquete);
    }

    @Transactional
    public void modificarPaquete(PaqueteDTO paqueteDTO) {
        Paquete paquete = paqueteRepository.findById(paqueteDTO.getId_paquete()).orElse(null);
        if(paquete == null){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paquete no encontrado (modificar paqete)");
        } else {
            Paquete paqueteModificado = Paquete.builder()
                    .id_paquete(paquete.getId_paquete())
                    .nombre(paqueteDTO.getNombre())
                    .precio(paqueteDTO.getPrecio())
                    .detalles(paqueteDTO.getDetalles())
                    .build();
            paqueteRepository.save(paqueteModificado);
        }
    }


    public void eliminarPaquete(Long id_paquete) {
        paqueteRepository.deleteById(id_paquete);
    }

    public List<PaqueteDTO> findAll() {
            List<Paquete> a = paqueteRepository.findAll();
            List<PaqueteDTO> b = new ArrayList<>();
            for(Paquete c : a){
                b.add(convertirPaqueteDTO(c));
            }
            return b;
        }
}
