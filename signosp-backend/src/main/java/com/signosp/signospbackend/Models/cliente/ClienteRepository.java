    package com.signosp.signospbackend.Models.cliente;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;
    import org.springframework.stereotype.Repository;

    import java.util.Optional;

    @Repository
    public interface ClienteRepository extends JpaRepository<Cliente,Long> {
        @Query(value="SELECT c FROM cliente c WHERE c.nomyape LIKE :%nombre%", nativeQuery = true)
        Optional<Cliente> findByNomyape(@Param("nombre")String nombre);

        @Query("SELECT c FROM Cliente c WHERE c.nomyape = :nomyape AND c.telefono = :telefono AND c.correo = :correo")
        Cliente existeCliente(@Param("nomyape") String nomyape,@Param("telefono") String telefono,@Param("correo") String correo);
    }
