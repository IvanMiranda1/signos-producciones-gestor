package com.signosp.signospbackend.Models.evento_empleado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Evento_empleadoRepository extends JpaRepository<Evento_empleado, Long> {
    @Query(value = "SELECT rec FROM Evento_empleado rec JOIN FETCH rec.evento e WHERE e.id_evento = :idEvento")
    List<Evento_empleado> findEmpleadosxEvento(@Param("idEvento")Long idEvento);

    @Query(value = "SELECT rec FROM Evento_empleado rec JOIN FETCH rec.evento ev JOIN FETCH rec.empleado em WHERE ev.id_evento = :idEvento AND em.id_empleado = :idEmpleado")
    Evento_empleado findbyIds(@Param("idEvento") Long idEvento,@Param("idEmpleado") Long idEmpleado);
}
