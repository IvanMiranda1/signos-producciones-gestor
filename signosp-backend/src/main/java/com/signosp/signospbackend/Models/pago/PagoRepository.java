package com.signosp.signospbackend.Models.pago;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<Pago,Long> {
    @Query(value = "SELECT p FROM Pago p JOIN FETCH p.evento e WHERE e.id_evento = :idEvento")
    Pago findByIdEvento(@Param("idEvento") Long idEvento);
}
