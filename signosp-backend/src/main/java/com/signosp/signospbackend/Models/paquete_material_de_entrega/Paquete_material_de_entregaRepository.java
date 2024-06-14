package com.signosp.signospbackend.Models.paquete_material_de_entrega;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Paquete_material_de_entregaRepository extends JpaRepository<Paquete_material_de_entrega, Long> {
    @Query(value = "SELECT pmde FROM Paquete_material_de_entrega pmde JOIN FETCH pmde.paquete p WHERE p.id_paquete = :idPaquete")
    List<Paquete_material_de_entrega> findRelaXidPaquete(@Param("idPaquete") Long idPaquete);

    @Query(value = "SELECT pmde FROM Paquete_material_de_entrega pmde JOIN FETCH pmde.paquete p JOIN FETCH pmde.material_de_entrega mde WHERE p.id_paquete = :idPaquete AND mde.id_material_de_entrega = :idMaterialDeEntrega")
    Paquete_material_de_entrega existeRelacion(@Param("idPaquete") Long idPaquete, @Param("idMaterialDeEntrega") Long idMaterialDeEntrega);
}
