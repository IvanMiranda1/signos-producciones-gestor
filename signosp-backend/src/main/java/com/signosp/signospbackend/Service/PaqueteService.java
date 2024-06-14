package com.signosp.signospbackend.Service;

import com.signosp.signospbackend.Models.especialidad.Especialidad;
import com.signosp.signospbackend.Models.especialidad.EspecialidadRepository;
import com.signosp.signospbackend.Models.material_de_entrega.Material_de_entrega;
import com.signosp.signospbackend.Models.material_de_entrega.Material_de_entregaDTO;
import com.signosp.signospbackend.Models.paquete.*;
import com.signosp.signospbackend.Models.paquete_material_de_entrega.Paquete_material_de_entrega;
import com.signosp.signospbackend.Models.paquete_material_de_entrega.Paquete_material_de_entregaDTO2;
import com.signosp.signospbackend.Models.servicio.Servicio;
import com.signosp.signospbackend.Models.servicio.ServicioDTO;
import com.signosp.signospbackend.Models.servicio.ServicioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PaqueteService {
    public final PaqueteRepository paqueteRepository;
    public final Paquete_material_de_entregaService paqueteMaterialDeEntregaService;
    public final ServicioService servicioService;
    public final ServicioRepository servicioRepository;

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

    public PaqueteMDEyServiciosDTO paqueteCompleto(Long id_paquete){
        Paquete paquete = paqueteRepository.findById(id_paquete).orElseThrow(
                ()-> new EntityNotFoundException("No se encontro el paquete")
        );
        PaqueteDTO paqueteDTO = convertirPaqueteDTO(paquete);

        List<Paquete_material_de_entregaDTO2> listRelacDTO = paqueteMaterialDeEntregaService.findRelacXIdPaquete(id_paquete);
        List<ServicioDTO> servicios = servicioService.findServiciosXidPaquete(id_paquete);
        return PaqueteMDEyServiciosDTO.builder()
                .paqueteDTO(paqueteDTO)
                .paqueteMaterialDeEntregaDTOS(listRelacDTO)
                .servicioDTOS(servicios)
                .build();
    }

    @Transactional
    public void asignarServicio(ServicioXPaqueteDTO servicioXPaqueteDTO) {
        Paquete paquete = paqueteRepository.findById(servicioXPaqueteDTO.getId_paquete()).orElseThrow(
                ()-> new EntityNotFoundException("Paquete no encontrado")
        );

        for(Long id_servicio : servicioXPaqueteDTO.getId_servicios()){
            Servicio servicio = servicioService.findById(id_servicio);
            if(!paquete.getServicios().contains(servicio)) {
                paquete.addServicio(servicio);
            } else {
                System.out.println("El servicio con el id "+servicio.getId_servicio()+" ya esta asociado");
            }

        }
        paqueteRepository.save(paquete);
    }

    @Transactional
    public void eliminarRelacion(Long id_paquete, Long id_servicio) {
        Paquete paquete = paqueteRepository.findById(id_paquete)
                .orElseThrow(()-> new EntityNotFoundException("No se encontro el paquete"));

        Servicio servicio = servicioRepository.findById(id_servicio)
                        .orElseThrow(()-> new EntityNotFoundException("No se encontro el servicio"));

        paquete.removeServicio(servicio);
        servicio.removePaquete(paquete);
        paqueteRepository.save(paquete);
        servicioRepository.save(servicio);

    }
}
