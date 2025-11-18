package Clientes_Microservicio.Service.Impl;

import Clientes_Microservicio.Model.Cliente;
import Clientes_Microservicio.Repository.ClienteRepository;
import Clientes_Microservicio.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Cliente createCliente(Cliente cliente) {
        // Verificar si el correo electrónico ya existe
        if (clienteRepository.findByCorreoElectronico(cliente.getCorreoElectronico()).isPresent()) {
            throw new DataIntegrityViolationException("El correo electrónico ya existe");
        }
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente getClienteById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    @Override
    public Cliente updateCliente(Long id, Cliente cliente) {
        Cliente clienteExistente = getClienteById(id);
        clienteExistente.setPrimerNombre(cliente.getPrimerNombre());
        clienteExistente.setUltimoNombre(cliente.getUltimoNombre());
        clienteExistente.setCorreoElectronico(cliente.getCorreoElectronico());
        clienteExistente.setCalle(cliente.getCalle());
        clienteExistente.setCiudad(cliente.getCiudad());
        clienteExistente.setPais(cliente.getPais());
        clienteExistente.setCodigoPostal(cliente.getCodigoPostal());
        try {
            return clienteRepository.save(clienteExistente);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("El correo electrónico ya existe");
        }
    }


    @Override
    public void deleteCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public void blockCliente(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'blockCliente'");
    }

}
