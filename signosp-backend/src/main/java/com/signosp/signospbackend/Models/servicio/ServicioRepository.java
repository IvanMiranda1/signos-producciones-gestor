package com.signosp.signospbackend.Models.servicio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    @Query(value = "SELECT s.id_servicio FROM paquete_servicio s WHERE s.id_paquete = :idPaquete", nativeQuery = true)
    List<Long> findServiciosXidPaquete(@Param("idPaquete") Long idPaquete);
}
