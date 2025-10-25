package Clientes_Microservicio.Service;

import java.util.List;
import Clientes_Microservicio.Model.Cliente;

public interface ClienteService {

    Cliente createCliente(Cliente cliente);

    Cliente getClienteById(Long id);

    Cliente updateCliente(Long id, Cliente cliente);

    void blockCliente(Long id);

    void deleteCliente(Long id);

    List<Cliente> getAllClientes();
}
