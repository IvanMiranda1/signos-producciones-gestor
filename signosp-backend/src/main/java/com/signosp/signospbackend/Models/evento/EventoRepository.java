package com.signosp.signospbackend.Models.evento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    /*@Query("SELECT * FROM evento e WHERE e.cliente.id_cliente = :id_cliente")
    List<Evento> findById_cliente(@Param("id_cliente")Long id_cliente);
     */
    /*@Query("SELECT e FROM evento e JOIN FETCH e.id_cliente c WHERE c.id_cliente = :id_cliente")
    List<Evento> findById_cliente(@Param("id_cliente") Long id_cliente);
    */
    @Query("SELECT e FROM Evento e JOIN FETCH e.cliente c WHERE c.id_cliente = :id_cliente")
    List<Evento> findById_cliente(@Param("id_cliente") Long id_cliente);

    @Query("SELECT e FROM Evento e JOIN FETCH e.categoria c WHERE c.nombre = :categoria")
    List<Evento> findPorCategoria(@Param("categoria") String categoria);

    @Query("SELECT e FROM Evento e JOIN FETCH e.paquete p WHERE p.id_paquete = :idPaquete AND e.estado = false")
    List<Evento> findPorPaquete(@Param("idPaquete") Long idPaquete);

    @Query("SELECT e FROM Evento e WHERE e.fecha BETWEEN :desde AND :hasta")
    List<Evento> findEventsXFechas(@Param("desde") Date desde, @Param("hasta") Date hasta);

    @Modifying
    @Query(value = "DELETE FROM evento_empleado WHERE id_evento = :idEvento",nativeQuery = true)
    void deleteRelacionEmpleado(@Param("idEvento") Long idEvento);

    @Modifying
    @Query(value = "DELETE FROM comentario WHERE id_evento = :idEvento",nativeQuery = true)
    void deleteRelacionComentarios(@Param("idEvento") Long idEvento);

    @Modifying
    @Query(value = "DELETE FROM pago WHERE id_evento = :idEvento",nativeQuery = true)
    void deleteRelacionPago(@Param("idEvento") Long idEvento);

    @Modifying
    @Query(value = "DELETE FROM cuota WHERE id_pago = (SELECT id_pago FROM pago WHERE id_evento = :idEvento)", nativeQuery = true)
    void deleteRelacionCuotas(@Param("idEvento") Long idEvento);

    @Modifying
    @Query(value = "DELETE FROM subtarea WHERE id_evento = :idEvento", nativeQuery = true)
    void deleteRelacionSubtareas(@Param("idEvento") Long idEvento);
}
