package com.signosp.signospbackend.Service;

import com.signosp.signospbackend.Models.cliente.Cliente;
import com.signosp.signospbackend.Models.cliente.ClienteDTO;
import com.signosp.signospbackend.Models.cliente.ClienteRepository;
import com.signosp.signospbackend.Models.evento.Evento;
import com.signosp.signospbackend.Models.evento.EventoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {
    public final ClienteRepository clienteRepository;
    public final EventoRepository eventoRepository;
    public void crearCliente(ClienteDTO clienteDTO) {
        Cliente nuevoCliente = Cliente.builder()
                .nomyape(clienteDTO.getNomyape())
                .correo(clienteDTO.getCorreo())
                .telefono(clienteDTO.getTelefono())
                .build();
        clienteRepository.save(nuevoCliente);
    }

    public ClienteDTO getClienteById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Error en getClienteById"));
        return convertirClienteDTO(cliente);
    }

    public ClienteDTO convertirClienteDTO(Cliente cliente) {
        return ClienteDTO.builder()
                .id_cliente(cliente.getId_cliente())
                .nomyape(cliente.getNomyape())
                .correo(cliente.getCorreo())
                .telefono(cliente.getTelefono())
                .build();
    }

    @Transactional
    public ResponseEntity<String> modificarCliente(ClienteDTO clienteDTO){
        Optional<Cliente> optionalCliente = clienteRepository.findById(clienteDTO.getId_cliente());
        if(!optionalCliente.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado");
        }
        Cliente cliente = optionalCliente.get();

        Cliente clienteModificado = Cliente.builder()
                .id_cliente(cliente.getId_cliente())
                .nomyape(clienteDTO.getNomyape() != null ? clienteDTO.getNomyape() : cliente.getNomyape())
                .correo(clienteDTO.getCorreo() != null ? clienteDTO.getCorreo() : cliente.getCorreo())
                .telefono(clienteDTO.getTelefono() != null ? clienteDTO.getTelefono() : cliente.getTelefono())
                .build();

        clienteRepository.save(clienteModificado);
        return ResponseEntity.ok("Cliente modificado");
    }

    // Para eliminar un cliente compruebo si el cliente tiene eventos pendientes,
    // retorna un boleando si tiene o no, asi se si mostrar el mensaje de que esta seguro de eliminarlo
    // con todos los eventos relacionados al ciente, y si no un mensaje chill de eliminacion
    public boolean comprobarEventosCliente(Long id_cliente){
        List<Evento> eventos = eventoRepository.findAll();
        List<Evento> eventosBusq = new ArrayList<>();
        for(Evento evento : eventos){
            if(evento.getCliente().getId_cliente() == id_cliente){
                eventosBusq.add(evento);
            }
        }
        if(!eventosBusq.isEmpty()){
            return true;
        } else {
            return false;
        }
    }
    public ResponseEntity<String> eliminarCliente(Long id_cliente){
        List<Evento> eventos = eventoRepository.findById_cliente(id_cliente);
        if(eventos.isEmpty()){
            clienteRepository.deleteById(id_cliente);
            return ResponseEntity.ok("Cliente eliminado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El cliente esta asociado a un evento,\npruebe eliminando el evento.");
        }
    }

    public ClienteDTO getClientePorNombre(String nombre) {
        Cliente c = clienteRepository.findByNomyape(nombre)
                .orElseThrow(()-> new EntityNotFoundException("error en getClientePorNombre"));
        ClienteDTO resultado = convertirClienteDTO(c);
        return resultado;
    }
    public List<ClienteDTO> findAll(){
        List<Cliente> a = clienteRepository.findAll();
        List<ClienteDTO> list = new ArrayList<>();
        for(Cliente c : a){
            list.add(convertirClienteDTO(c));
        }
        return list;
    }

    public ResponseEntity<?> existeCliente(ClienteDTO clienteDTO) {
        Cliente cliente = clienteRepository.existeCliente(clienteDTO.getNomyape(),clienteDTO.getTelefono(),clienteDTO.getCorreo());
        if(cliente!=null){
            return ResponseEntity.ok(convertirClienteDTO(cliente));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el cliente");
        }
    }
}
