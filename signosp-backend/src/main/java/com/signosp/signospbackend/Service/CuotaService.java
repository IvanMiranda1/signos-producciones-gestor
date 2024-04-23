package com.signosp.signospbackend.Service;

import com.signosp.signospbackend.Models.cuota.Cuota;
import com.signosp.signospbackend.Models.cuota.CuotaDTO;
import com.signosp.signospbackend.Models.cuota.CuotaId;
import com.signosp.signospbackend.Models.cuota.CuotaRepository;
import com.signosp.signospbackend.Models.pago.Pago;
import com.signosp.signospbackend.Models.pago.PagoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CuotaService {
    public final CuotaRepository cuotaRepository;
    public final PagoRepository pagoRepository;
    public void crearCuota(CuotaDTO cuotaDTO) {
        Pago pago = pagoRepository.findById(cuotaDTO.getId_pago())
                .orElseThrow(()-> new EntityNotFoundException("No se encontro el pago con id"+ cuotaDTO.getId_cuota()));
        CuotaId cuotaId = new CuotaId(pago, cuotaDTO.getNroCuota());
        Cuota nuevaCuota = Cuota.builder()
                .nro_cuota(cuotaDTO.getNroCuota())
                .monto(cuotaDTO.getMonto())
                .fecha_de_pago(cuotaDTO.getFecha_de_pago())
                .pago(pago)
                .id_cuota(cuotaId)
                .build();
        cuotaRepository.save(nuevaCuota);
    }
    public ResponseEntity<String> modificarMontoCuota(CuotaDTO cuotaDTO){
        Cuota cuota = cuotaRepository.findById(cuotaDTO.getId_pago()).orElse(null);
        if(cuota == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cuota no encontrada");
        }
        Cuota cuotaModificada = Cuota.builder()
                .id_cuota(cuota.getId_cuota())
                .monto(cuota.getMonto())
                .build();
        cuotaRepository.save(cuotaModificada);
        return ResponseEntity.ok("Cuota modificada");
    }

    public CuotaDTO convertirCuotaDTO(Cuota cuota){
        return CuotaDTO.builder()
                .id_cuota(cuota.getId_cuota())
                .id_pago(cuota.getPago().getId_pago())
                .monto(cuota.getMonto())
                .fecha_de_pago(cuota.getFecha_de_pago())
                .build();
    }

    public ResponseEntity<String> eliminarCuota(Long id_cuota) {
        cuotaRepository.deleteById(id_cuota);
        return ResponseEntity.ok("Cuota eliminada");
    }

}
