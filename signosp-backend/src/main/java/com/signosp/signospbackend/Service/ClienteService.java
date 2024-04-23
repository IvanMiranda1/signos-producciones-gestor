package com.signosp.signospbackend.Service;

import com.signosp.signospbackend.Models.cliente.Cliente;
import com.signosp.signospbackend.Models.cliente.ClienteDTO;
import com.signosp.signospbackend.Models.cliente.ClienteRepository;
import com.signosp.signospbackend.Models.evento.Evento;
import com.signosp.signospbackend.Models.evento.EventoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        return convertirClinteDTO(cliente);
    }

    private ClienteDTO convertirClinteDTO(Cliente cliente) {
        return ClienteDTO.builder()
                .id_cliente(cliente.getId_cliente())
                .nomyape(cliente.getNomyape())
                .correo(cliente.getCorreo())
                .telefono(cliente.getTelefono())
                .build();
    }

    @Transactional
    public ResponseEntity<String> modificarCliente(ClienteDTO clienteDTO){
        Cliente cliente = clienteRepository.findById(clienteDTO.getId_cliente()).orElse(null);
        if(cliente == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado (modificar cliente)");
        }
        //deberia validar si cliente esta en eventos?
        // si no, mostrar una alerta y confirmar la eliminacion de eventos con el cliente solicitado
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
        clienteRepository.deleteById(id_cliente);
        return ResponseEntity.ok("Cliente eliminado junto a los eventos");
    }

    public ClienteDTO getClientePorNombre(String nombre) {
        Cliente c = clienteRepository.findByNomyape(nombre)
                .orElseThrow(()-> new EntityNotFoundException("error en getClientePorNombre"));
        ClienteDTO resultado = convertirClinteDTO(c);
        return resultado;
    }
    public List<ClienteDTO> findAll(){
        List<Cliente> a = clienteRepository.findAll();
        List<ClienteDTO> list = new ArrayList<>();
        for(Cliente c : a){
            list.add(convertirClinteDTO(c));
        }
        return list;
    }

}
