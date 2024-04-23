package com.signosp.signospbackend.Models.evento_empleado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Evento_empleadoRepository extends JpaRepository<Evento_empleado, Long> {
}
