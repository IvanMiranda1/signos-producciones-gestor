package com.signosp.signospbackend.Models.cuota;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuotaRepository extends JpaRepository<Cuota, Long> {
    @Query(value = "SELECT c FROM Cuota c JOIN FETCH c.pago p WHERE p.id_pago = :id_pago")
    List<Cuota> listCuotasByIdPago(@Param("id_pago")Long id_pago);

    @Query(value = "DELETE FROM Cuota c WHERE c.pago.id_pago = :id_pago")
    void deleteByIdPago(@Param("id_pago") Long id_pago);

    @Query("SELECT COUNT(c) FROM Cuota c WHERE c.pago.id_pago = :id_pago")
    Long coutByPagoId(@Param("id_pago") Long idPago);

    @Query("SELECT c FROM Cuota c WHERE c.pago.id_pago = :idPago AND c.nro_cuota = :nroCuota")
    Optional<Cuota> findByIdxNroCuota(@Param("idPago") Long idPago, @Param("nroCuota") Integer nroCuota);
}