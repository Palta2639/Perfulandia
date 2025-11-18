package Ventas_Microservicio.Ventas.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Ventas_Microservicio.Ventas.Model.Ventas;
import Ventas_Microservicio.Ventas.Service.Impl.VentasServiceImpl;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentasController {

    @Autowired
    private VentasServiceImpl ventasService;

    @Value("${cliente.service.url}")
    private String clienteServiceUrl;

    @Value("${producto.service.url}")
    private String productoServiceUrl;

    @PostMapping
    public ResponseEntity<Ventas> createVenta(@RequestBody Ventas venta) {
        try {
            // El servicio maneja todas las validaciones y asigna nombres automáticamente
            Ventas nuevaVenta = ventasService.createVenta(venta);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVenta);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Cliente no encontrado")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            } else if (e.getMessage().contains("Producto no encontrado")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            } else if (e.getMessage().contains("Stock insuficiente")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

      @GetMapping
    public ResponseEntity<List<Ventas>> getAllVentas() {
        List<Ventas> ventas = ventasService.getAllVentas();
        return ResponseEntity.ok(ventas);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Ventas> getVentaById(@PathVariable Long id) {
        return ventasService.getVentaById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Ventas>> getVentasByCliente(@PathVariable Long clienteId) {
        List<Ventas> ventas = ventasService.getVentasByClienteId(clienteId);
        return ResponseEntity.ok(ventas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ventas> updateVenta(@PathVariable Long id, @RequestBody Ventas venta) {
        try {
            venta.setId(id); // Asegurar que el ID sea el del path
            Ventas updatedVenta = ventasService.updateVenta(venta);
            return ResponseEntity.ok(updatedVenta);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Venta no encontrada")) {
                return ResponseEntity.notFound().build();
            } else if (e.getMessage().contains("Cliente no encontrado")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            } else if (e.getMessage().contains("Producto no encontrado")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            } else if (e.getMessage().contains("Stock insuficiente")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVenta(@PathVariable Long id) {
        try {
            ventasService.deleteVenta(id);
            return ResponseEntity.ok("Venta eliminada exitosamente");
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Venta no encontrada")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la venta");
        }
    }
}
