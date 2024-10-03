package com.signosp.signospbackend.Service;

import com.signosp.signospbackend.Models.especialidad.Especialidad;
import com.signosp.signospbackend.Models.especialidad.EspecialidadRepository;
import com.signosp.signospbackend.Models.evento.Evento;
import com.signosp.signospbackend.Models.evento.EventoRepository;
import com.signosp.signospbackend.Models.material_de_entrega.Material_de_entrega;
import com.signosp.signospbackend.Models.material_de_entrega.Material_de_entregaDTO;
import com.signosp.signospbackend.Models.material_de_entrega.Material_de_entregaRepository;
import com.signosp.signospbackend.Models.paquete.*;
import com.signosp.signospbackend.Models.paquete_material_de_entrega.Paquete_material_de_entrega;
import com.signosp.signospbackend.Models.paquete_material_de_entrega.Paquete_material_de_entregaDTO;
import com.signosp.signospbackend.Models.paquete_material_de_entrega.Paquete_material_de_entregaDTO2;
import com.signosp.signospbackend.Models.paquete_material_de_entrega.Paquete_material_de_entregaRepository;
import com.signosp.signospbackend.Models.servicio.Servicio;
import com.signosp.signospbackend.Models.servicio.ServicioDTO;
import com.signosp.signospbackend.Models.servicio.ServicioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
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
    public final Material_de_entregaRepository material_de_entregaRepository;
    public final EventoRepository eventoRepository;
    public final Paquete_material_de_entregaRepository  paqueteMaterialDeEntregaRepository;

    public void crearPaquete(PaqueteDTO paqueteDTO) {
        Paquete nuevoPaquete = Paquete.builder()
                .nombre(paqueteDTO.getNombre())
                .precio(paqueteDTO.getPrecio())
                .detalles(paqueteDTO.getDetalles())
                .build();
        Set<Servicio> servicios = new HashSet<>();
        for(Long id_servicio : paqueteDTO.getServicios()){
            Servicio serv = servicioRepository.findById(id_servicio).orElse(null);
            if(serv != null){
                servicios.add(serv);
            }
        }
        nuevoPaquete.setServicios(servicios);
        Paquete newpaquete = paqueteRepository.save(nuevoPaquete);

        for (Paquete_material_de_entregaDTO mats : paqueteDTO.getMateriales()){
            Material_de_entrega regMats = material_de_entregaRepository.findById(mats.getId_material_de_entrega()).orElse(null);
            if(regMats != null) {
                Paquete_material_de_entregaDTO newRelac = Paquete_material_de_entregaDTO.builder()
                        .id_paquete(newpaquete.getId_paquete())
                        .id_material_de_entrega(mats.getId_material_de_entrega())
                        .cantidad(mats.getCantidad())
                        .build();
                paqueteMaterialDeEntregaService.crearPaqueteMaterial(newRelac);
            }
        }
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
    public void modificarPaquete(PaqueteMDEyServiciosDTO paqueteDTO) {
        Paquete paqueteExistente = paqueteRepository.findById(paqueteDTO.getPaqueteDTO().getId_paquete())
                .orElseThrow(() -> new EntityNotFoundException("Paquete no encontrado"));

        // Actualizar los campos del paquete
        paqueteExistente.setNombre(paqueteDTO.getPaqueteDTO().getNombre());
        paqueteExistente.setPrecio(paqueteDTO.getPaqueteDTO().getPrecio());
        paqueteExistente.setDetalles(paqueteDTO.getPaqueteDTO().getDetalles());


        // Actualizar los servicios
        Set<Servicio> servicios = new HashSet<>();
        for (ServicioDTO servicioDTO : paqueteDTO.getServicioDTOS()) {
            Servicio servicio = servicioRepository.findById(servicioDTO.getId_servicio())
                    .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado"));
            servicios.add(servicio);
        }
        paqueteExistente.setServicios(servicios);

        // Guardar el paquete actualizado
        paqueteRepository.save(paqueteExistente);
        paqueteMaterialDeEntregaRepository.deleteByIdPaquete(paqueteExistente.getId_paquete());

        //////
        for (Paquete_material_de_entregaDTO2 mats : paqueteDTO.getPaqueteMaterialDeEntregaDTOS()){
            Material_de_entrega regMats = material_de_entregaRepository.findById(mats.getMaterial_de_entrega().getId_material_de_entrega()).orElse(null);
            if(regMats != null) {
                Paquete_material_de_entregaDTO newRelac = Paquete_material_de_entregaDTO.builder()
                        .id_paquete(paqueteExistente.getId_paquete())
                        .id_material_de_entrega(mats.getMaterial_de_entrega().getId_material_de_entrega())
                        .cantidad(mats.getCantidad())
                        .build();
                paqueteMaterialDeEntregaService.crearPaqueteMaterial(newRelac);
            }
        }

    }

    @Transactional
    public ResponseEntity<String> eliminarPaquete(Long id_paquete) {
        //verificar que no este en ningun evento que aun no este marcado terminado
        //al eliminar tambien debo borrar las relaciones con los servicios y mde
        Paquete paquete = paqueteRepository.findById(id_paquete).orElse(null);
        if(paquete != null){
            List<Evento> eventos = eventoRepository.findPorPaquete(paquete.getId_paquete());
            if(eventos.isEmpty()){
                paqueteRepository.deleteRecServByIdPaquete(id_paquete);
                paqueteMaterialDeEntregaRepository.deleteByIdPaquete(paquete.getId_paquete());
                paqueteRepository.deleteById(id_paquete);
                return ResponseEntity.ok("Paquete eliminado exitosamente.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("No se puede eliminar el paquete porque está asociado a uno o más eventos sin finalizar.");
            }
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El paquete con el ID especificado no existe.");
        }
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
