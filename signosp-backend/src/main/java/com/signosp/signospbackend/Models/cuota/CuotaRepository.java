package com.signosp.signospbackend.Models.cuota;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuotaRepository extends JpaRepository<Cuota, Long> {
    @Query(value = "SELECT c FROM Cuota c JOIN FETCH c.pago p WHERE p.id_pago = :id_pago")
    List<Cuota> listCuotasByIdPago(@Param("id_pago")Long id_pago);

    @Query(value = "DELETE FROM Cuota c WHERE c.pago.id_pago = :id_pago")
    void deleteByIdPago(@Param("id_pago") Long id_pago);
}
