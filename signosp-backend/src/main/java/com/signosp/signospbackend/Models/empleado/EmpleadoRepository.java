package com.signosp.signospbackend.Models.empleado;

import com.signosp.signospbackend.Models.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado,Long> {
}
