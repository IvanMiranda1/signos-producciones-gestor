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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CuotaService {
    public final CuotaRepository cuotaRepository;
    public final PagoRepository pagoRepository;

    public ResponseEntity<String> crearCuota(CuotaDTO cuotaDTO) {
        Pago pago = pagoRepository.findById(cuotaDTO.getId_pago())
                .orElseThrow(()-> new EntityNotFoundException("No se encontro el pago con id"+ cuotaDTO.getId_cuota()));
        Long numCuotasRegistradas = cuotaRepository.coutByPagoId(cuotaDTO.getId_pago());
        if(numCuotasRegistradas > pago.getCant_cuotas()){
            throw new IllegalStateException("No se puede registrar mas cuotas para este pago porque ya se ha alcanzado el maximo");
        }

        // Buscar si la cuota ya existe en la base de datos
        Optional<Cuota> cuotaExistente = cuotaRepository.findByIdxNroCuota(pago.getId_pago(),cuotaDTO.getNro_cuota());
        if (cuotaExistente.isPresent()) {
            Cuota cuota = cuotaExistente.get();
            float monto = cuota.getMonto();
            float montoDTO = cuotaDTO.getMonto();
            float montoRestante = monto - montoDTO;
            if (montoRestante <= 0) {
                cuota.setMonto(0f);  // 0f es para float
            } else {
                cuota.setMonto(montoRestante);
            }
            cuota.setFecha_de_pago(cuotaDTO.getFecha_de_pago());
            cuotaRepository.save(cuota);
            return ResponseEntity.ok("Cuota "+cuota.getNro_cuota()+"° Modificada.");
        } else {
            CuotaId cuotaId = CuotaId.builder()
                    .pago(pago)
                    .nroCuota(cuotaDTO.getNro_cuota())
                    .build();

            Cuota nuevaCuota = Cuota.builder()
                    .nro_cuota(cuotaDTO.getNro_cuota())
                    .monto(cuotaDTO.getMonto())
                    .fecha_de_pago(cuotaDTO.getFecha_de_pago())
                    .pago(pago)
                    .id_cuota(cuotaId)
                    .build();
            cuotaRepository.save(nuevaCuota);
            return ResponseEntity.ok("Cuota creada");
        }
    }

    public ResponseEntity<String> modificarMontoCuota(CuotaDTO cuotaDTO){
        Optional<Cuota> optionalCuota = cuotaRepository.findById(cuotaDTO.getId_pago());
        if(!optionalCuota.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cuota no encontrada");
        }
        Cuota cuota = optionalCuota.get();

        Cuota cuotaModificada = Cuota.builder()
                .id_cuota(cuota.getId_cuota())
                .monto(cuotaDTO.getMonto() != null? cuotaDTO.getMonto() : cuota.getMonto())
                .build();
        cuotaRepository.save(cuotaModificada);
        return ResponseEntity.ok("Cuota modificada");
    }

    public CuotaDTO convertirCuotaDTO(Cuota cuota){
        return CuotaDTO.builder()
                .id_cuota(cuota.getId_cuota())
                .id_pago(cuota.getPago().getId_pago())
                .monto(cuota.getMonto())
                .nro_cuota(cuota.getNro_cuota())
                .fecha_de_pago(cuota.getFecha_de_pago())
                .build();
    }

    public ResponseEntity<String> eliminarCuota(Long id_cuota) {
        cuotaRepository.deleteById(id_cuota);
        return ResponseEntity.ok("Cuota eliminada");
    }

    public CuotaDTO byId(Long id) {
        Cuota a = cuotaRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("No se encontro"));
        return convertirCuotaDTO(a);
    }

    public List<CuotaDTO> findAll() {
        List<Cuota> a = cuotaRepository.findAll();
        List<CuotaDTO> b = new ArrayList<>();
        for(Cuota c : a){
            b.add(convertirCuotaDTO(c));
        }
        return b;
    }

    public List<CuotaDTO> cuotasDelPago(Long idPago) {
        List<Cuota> list = cuotaRepository.listCuotasByIdPago(idPago);
        List<CuotaDTO> listDTO = new ArrayList<>();
        for(Cuota c : list){
            listDTO.add(convertirCuotaDTO(c));
        }
        return listDTO;
    }
}
