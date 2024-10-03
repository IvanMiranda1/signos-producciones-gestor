package com.signosp.signospbackend.Controller;

import com.signosp.signospbackend.Models.cliente.Cliente;
import com.signosp.signospbackend.Models.cliente.ClienteDTO;
import com.signosp.signospbackend.Models.cliente.ClienteRepository;
import com.signosp.signospbackend.Service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor // Se utiliza para que lombok cree automaticamente el constructor de las clases con final. por ejemplo acceder a los meteodos de cliente repository
public class ClienteController {
    public final ClienteRepository clienteRepository;
    public final ClienteService clienteService;

    @GetMapping
    public List<ClienteDTO> getAllClientes(){
        return clienteService.findAll();
    }
    //busqueda con nombre quizas
    @GetMapping("/bxn/{nombre}")
    public ClienteDTO getClienteNombre(@PathVariable String nombre){
        return clienteService.getClientePorNombre(nombre);
    }
    @GetMapping("/{id}")
    public ClienteDTO getClienteById(@PathVariable Long id){
        return clienteService.getClienteById(id);
    }

    @PostMapping("/existe")
    public ResponseEntity<?> existeCliente(@RequestBody ClienteDTO clienteDTO){
        return clienteService.existeCliente(clienteDTO);
    }
    @PostMapping
    public void crearCliente(@RequestBody ClienteDTO nuevoCliente){
        clienteService.crearCliente(nuevoCliente);
    }
    @PutMapping
    public void modificarCliente(@RequestBody ClienteDTO cliente){
        clienteService.modificarCliente(cliente);
    }
    @DeleteMapping("/{id_cliente}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Long id_cliente){
        return clienteService.eliminarCliente(id_cliente);
    }

    @GetMapping("/compClient")
    public boolean comprobarEventoCLiente(@PathVariable Long id_cliente){
        return clienteService.comprobarEventosCliente(id_cliente);
    }

}
