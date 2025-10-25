package Clientes_Microservicio.Controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import Clientes_Microservicio.Model.Cliente;
import Clientes_Microservicio.Service.ClienteService;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
        try {
            Cliente clienteCreado = clienteService.createCliente(cliente);
            URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(clienteCreado.getId())
                    .toUri();
            return ResponseEntity.created(ubicacion).body(clienteCreado);
        } catch (Exception e) {
            if (e.getMessage().contains("El correo electrónico ya existe")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getCliente(@PathVariable Long id) {
        try {
            Cliente cliente = clienteService.getClienteById(id);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        try {
            Cliente clienteActualizado = clienteService.updateCliente(id, cliente);
            return ResponseEntity.ok(clienteActualizado);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Cliente no encontrado")) {
                return ResponseEntity.notFound().build();
            } else if (e.getMessage().contains("El correo electrónico ya existe")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/{id}/block")
    public ResponseEntity<Void> blockCliente(@PathVariable Long id) {
        try {
            clienteService.blockCliente(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        try {
            clienteService.deleteCliente(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        try {
            List<Cliente> clientes = clienteService.getAllClientes();
            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
