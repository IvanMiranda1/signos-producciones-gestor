package com.signosp.signospbackend.Models.subtarea;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SubtareaRepository extends JpaRepository<Subtarea, Long> {
    @Query(value = "SELECT s FROM Subtarea s WHERE s.id_evento = :idEvento")
    List<Subtarea> findByIdEvento(@Param("idEvento") Long idEvento);
}
