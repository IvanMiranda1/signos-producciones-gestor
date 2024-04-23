package com.signosp.signospbackend.Models.evento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

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
}
