package com.signosp.signospbackend.Models.paquete;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaqueteRepository extends JpaRepository<Paquete, Long> {
    @Query("SELECT p FROM Paquete p WHERE p.nombre = :nombre")
    Optional<Paquete> findByNombre(@Param("nombre") String nombre);
}
