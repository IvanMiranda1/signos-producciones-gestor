package com.signosp.signospbackend.Models.comentario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario,Long> {
    @Query("SELECT c FROM Comentario c JOIN FETCH c.evento e WHERE e.id_evento = :idEvento")
    List<Comentario> findByIdEvento(Long idEvento);
}
