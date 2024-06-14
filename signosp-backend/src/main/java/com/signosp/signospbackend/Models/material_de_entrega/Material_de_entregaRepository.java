package com.signosp.signospbackend.Models.material_de_entrega;

import com.signosp.signospbackend.Models.paquete_material_de_entrega.Paquete_material_de_entrega;
import com.signosp.signospbackend.Models.paquete_material_de_entrega.Paquete_material_de_entregaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Material_de_entregaRepository extends JpaRepository<Material_de_entrega, Long> {
    @Query(value = "SELECT pmde.*\n" +
            "FROM paquete_material_de_entrega pmde\n" +
            "JOIN paquete p ON p.id_paquete = pmde.id_paquete\n" +
            "WHERE p.id_paquete = :idPaquete",nativeQuery = true)
    List<Object> findRelaXidPaquete(@Param("idPaquete") Long idPaquete);
}
