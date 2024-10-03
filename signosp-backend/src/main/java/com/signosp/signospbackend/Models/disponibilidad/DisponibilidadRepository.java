package com.signosp.signospbackend.Models.disponibilidad;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisponibilidadRepository extends JpaRepository<Disponibilidad, Long> {

    @Query("SELECT d FROM Disponibilidad d JOIN FETCH d.empleado e WHERE e.id_empleado = :id")
    List<Disponibilidad> findByIdEmpleado(@Param("id")Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Disponibilidad d WHERE d.empleado.id_empleado = :idEmpleado")
    void deleteByIdEmpleado(@Param("idEmpleado") Long idEmpleado);

}
