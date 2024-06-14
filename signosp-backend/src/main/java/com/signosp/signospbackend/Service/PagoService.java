package com.signosp.signospbackend.Service;

import com.signosp.signospbackend.Models.cuota.Cuota;
import com.signosp.signospbackend.Models.cuota.CuotaDTO;
import com.signosp.signospbackend.Models.cuota.CuotaRepository;
import com.signosp.signospbackend.Models.evento.Evento;
import com.signosp.signospbackend.Models.evento.EventoRepository;
import com.signosp.signospbackend.Models.pago.Pago;
import com.signosp.signospbackend.Models.pago.PagoConCuotasDTO;
import com.signosp.signospbackend.Models.pago.PagoDTO;
import com.signosp.signospbackend.Models.pago.PagoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagoService {
    public final PagoRepository pagoRepository;
    public final EventoRepository eventoRepository;
    public final CuotaRepository cuotaRepository;
    public final CuotaService cuotaService;

    public void crearPago(PagoDTO pagoDTO) {
        Evento evento = eventoRepository.findById(pagoDTO.getId_evento())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el evento con ID: " + pagoDTO.getId_evento()));
        Pago nuevoPago = Pago.builder()
                .evento(evento)
                .forma_de_pago(pagoDTO.getForma_de_pago())
                .cant_cuotas(pagoDTO.getCant_cuotas())
                .build();
        pagoRepository.save(nuevoPago);
    }

    public ResponseEntity<String> modificarPago(PagoDTO pagoDTO){
        Pago pago = pagoRepository.findById(pagoDTO.getId_pago()).orElse(null);
        if(pago == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra el pago");
        }
        Pago pagoModificado = Pago.builder()
                .id_pago(pago.getId_pago())
                .forma_de_pago(pagoDTO.getForma_de_pago() != null? pagoDTO.getForma_de_pago() : pago.getForma_de_pago())
                .cant_cuotas(pagoDTO.getCant_cuotas() != null? pagoDTO.getCant_cuotas(): pago.getCant_cuotas())
                .build();
        pagoRepository.save(pagoModificado);
        return ResponseEntity.ok("Pago modificado");
    }

    public PagoDTO convertirPagoDTO(Pago pago){
        return PagoDTO.builder()
                .id_pago(pago.getId_pago())
                .id_evento(pago.getEvento().getId_evento())
                .forma_de_pago(pago.getForma_de_pago())
                .cant_cuotas(pago.getCant_cuotas())
                .build();
    }

    public List<CuotaDTO> listarCuotas(Long id_pago){
        List<CuotaDTO> listadoDTO = new ArrayList<>();
        List<Cuota> listado = cuotaRepository.listCuotasByIdPago(id_pago);
        for (Cuota cuota : listado){
            listadoDTO.add(cuotaService.convertirCuotaDTO(cuota));
        }
        return listadoDTO;
    }

    public void eliminarPago(Long id_pago){
        pagoRepository.deleteById(id_pago);
        cuotaRepository.deleteByIdPago(id_pago);
    }

    public List<PagoDTO> findAll() {
            List<Pago> a = pagoRepository.findAll();
            List<PagoDTO> b = new ArrayList<>();
            for(Pago c : a){
                b.add(convertirPagoDTO(c));
            }
            return b;
        }
    public PagoDTO byId(Long id) {
            Pago a = pagoRepository.findById(id)
                    .orElseThrow(()-> new EntityNotFoundException("PagoDTO"));
            return convertirPagoDTO(a);
        }

    public PagoConCuotasDTO obtenerPagoDeEvento(Long idEvento) {
        Pago pago = pagoRepository.findByIdEvento(idEvento);
        PagoDTO pagoDTO = convertirPagoDTO(pago);
        List<CuotaDTO> listCuotas = cuotaService.cuotasDelPago(pago.getId_pago());
        return PagoConCuotasDTO.builder()
                .pagoDTO(pagoDTO)
                .cuotas(listCuotas)
                .build();
    }

}
