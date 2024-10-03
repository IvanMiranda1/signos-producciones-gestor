package com.signosp.signospbackend.Models.fecha_trabajada;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface Fecha_trabajadaRepository extends JpaRepository<Fecha_trabajada, Long> {
    //@Query(value = "SELECT f FROM Fecha_trabajada WHERE f.fecha_trabajada = :fecha AND f.id_evento_empleado = :idEvento_empleado")
    //Fecha_trabajada findFecha(@Param("fecha")Date fecha,@Param("idEvento_empleado")Long idEvento_empleado);

    @Query(value = "SELECT f FROM Fecha_trabajada f WHERE f.id_evento_empleado = :idEventoEmpleado")
    List<Fecha_trabajada> findxIdRelacion(@Param("idEventoEmpleado") Long idEventoEmpleado);
}
