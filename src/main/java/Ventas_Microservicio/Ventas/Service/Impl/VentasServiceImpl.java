package Ventas_Microservicio.Ventas.Service.Impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import Ventas_Microservicio.Ventas.Model.Cliente;
import Ventas_Microservicio.Ventas.Model.Producto;
import Ventas_Microservicio.Ventas.Model.Ventas;
import Ventas_Microservicio.Ventas.Repository.VentasRepository;
import Ventas_Microservicio.Ventas.Service.VentasService;

@Service
public class VentasServiceImpl implements VentasService {

    @Autowired
    private VentasRepository ventasRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${cliente.service.url}")
    private String clienteServiceUrl;

    @Value("${producto.service.url}")
    private String productoServiceUrl;

    @Override
    public Ventas createVenta(Ventas venta) {
        // Obtener el cliente para obtener el nombre
        ResponseEntity<Cliente> clienteResponse = restTemplate.getForEntity(
            clienteServiceUrl + venta.getClienteId(),
            Cliente.class
        );

        if (!clienteResponse.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Cliente no encontrado");
        }

        Cliente cliente = clienteResponse.getBody();

        // Verificar si el cliente está bloqueado
        if (cliente.isBloqueado()) {
            throw new RuntimeException("Cliente bloqueado, no puede realizar ventas");
        }

        // Obtener el producto para validar stock y obtener precio
        ResponseEntity<Producto> productoResponse = restTemplate.getForEntity(
            productoServiceUrl + venta.getProductoId(),
            Producto.class
        );

        if (!productoResponse.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Producto no encontrado");
        }

        Producto producto = productoResponse.getBody();

        // Validar stock
        if (producto.getStock() == null || producto.getStock() < venta.getCantidad()) {
            throw new RuntimeException("Stock insuficiente para el producto");
        }

        // Asignar nombres automáticamente
        venta.setClienteNombre(cliente.getPrimerNombre() + " " + cliente.getUltimoNombre());
        venta.setProductoNombre(producto.getNombre());

        // Calcular total en pesos chilenos (1 USD ≈ 950 CLP)
        double totalEnUSD = producto.getPrecio() * venta.getCantidad();
        double totalEnCLP = totalEnUSD * 950;
        venta.setTotal(totalEnCLP);
        venta.setFecha(LocalDateTime.now());

        return ventasRepository.save(venta);
    }

    @Override
    public Optional<Ventas> getVentaById(Long id) {
        return ventasRepository.findById(id);
    }

    @Override
    public List<Ventas> getVentasByClienteId(Long clienteId) {
        return ventasRepository.findByClienteId(clienteId);
    }
}
