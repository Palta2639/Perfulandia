package Ventas_Microservicio.Ventas.Service;

import java.util.List;
import java.util.Optional;

import Ventas_Microservicio.Ventas.Model.Ventas;

public interface VentasService {

    Ventas createVenta(Ventas venta);
    Optional<Ventas> getVentaById(Long id);
    List<Ventas> getVentasByClienteId(Long clienteId);
}
