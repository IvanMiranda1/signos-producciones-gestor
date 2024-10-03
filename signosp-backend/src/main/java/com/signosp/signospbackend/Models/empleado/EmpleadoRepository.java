package com.signosp.signospbackend.Models.empleado;

import com.signosp.signospbackend.Models.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado,Long> {
    @Query(value = "SELECT e.id_empleado FROM empleado_especialidad e WHERE e.id_especialidad = :idEspecialidad", nativeQuery = true)
    List<Long> findByIdEspecialidad(@Param("idEspecialidad") Long idEspecialidad);
}
