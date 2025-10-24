package Ventas_Microservicio.Ventas.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Ventas_Microservicio.Ventas.Model.Ventas;

@Repository
public interface VentasRepository extends JpaRepository<Ventas, Long> {

    List<Ventas> findByClienteId(Long clienteId);
}
