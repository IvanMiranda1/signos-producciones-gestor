package com.signosp.signospbackend.Models.paquete;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaqueteRepository extends JpaRepository<Paquete, Long> {
    @Query("SELECT p FROM Paquete p WHERE p.nombre = :nombre")
    Optional<Paquete> findByNombre(@Param("nombre") String nombre);

    @Modifying
    @Query(value = "DELETE FROM paquete_servicio ps WHERE ps.id_paquete = :idPaquete", nativeQuery = true)
    void deleteRecServByIdPaquete(@Param("idPaquete") Long idPaquete);

    @Query(value = "SELECT ps FROM paquete_servicio ps WHERE ps.id_servicio = :idServicio", nativeQuery = true)
    List<Object> findByIdServicio(@Param("idServicio") Long idServicio);

    @Query(value = "SELECT mde FROM paquete_material_de_entrega mde WHERE mde.id_material_de_entrega = :idMaterial",nativeQuery = true)
    List<Object> findByIdMaterial(@Param("idMaterial") Long idMaterial);

}
