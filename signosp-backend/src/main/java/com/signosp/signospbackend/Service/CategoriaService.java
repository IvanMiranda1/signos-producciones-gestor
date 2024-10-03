package com.signosp.signospbackend.Service;

import com.signosp.signospbackend.Models.categoria.Categoria;
import com.signosp.signospbackend.Models.categoria.CategoriaDTO;
import com.signosp.signospbackend.Models.categoria.CategoriaRepository;
import com.signosp.signospbackend.Models.evento.Evento;
import com.signosp.signospbackend.Models.evento.EventoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaService {
    public final CategoriaRepository categoriaRepository;
    public final EventoRepository eventoRepository;

    public void crearCategoria(CategoriaDTO categoriaDTO){
        Categoria nuevaCategoria = Categoria.builder()
                .nombre(categoriaDTO.getNombre())
                .build();
        categoriaRepository.save(nuevaCategoria);
    }

    public CategoriaDTO getCategoriaById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Error en busqueda por id categoria"));
        return convertirCategoriaDTO(categoria);
    }

    private CategoriaDTO convertirCategoriaDTO(Categoria categoria) {
        return CategoriaDTO.builder()
                .id_categoria(categoria.getId_categoria())
                .nombre(categoria.getNombre())
                .build();
    }

    public ResponseEntity<String> modificarCategoria(CategoriaDTO categoriaDTO) {
        Optional<Categoria> optionalCategoria = categoriaRepository.findById(categoriaDTO.getId_categoria());
        if(!optionalCategoria.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro Categoria");
        }
        Categoria categoria = optionalCategoria.get();

        Categoria categoriaModificada = Categoria.builder()
                .id_categoria(categoriaDTO.getId_categoria())
                .nombre(categoriaDTO.getNombre() != null? categoriaDTO.getNombre() : categoria.getNombre())
                .build();
        categoriaRepository.save(categoriaModificada);
        return ResponseEntity.ok("Categoria modificada");
    }

    public ResponseEntity<String> eliminarCategoria(Long id_categoria) {
        Categoria categoria = categoriaRepository.findById(id_categoria).orElse(null);
        if (categoria != null) {
            List<Evento> eventos = eventoRepository.findPorCategoria(categoria.getNombre());
            if (eventos.isEmpty()) {
                categoriaRepository.deleteById(id_categoria);
                return ResponseEntity.ok("Categoría eliminada exitosamente.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("No se puede eliminar la categoría porque está asociada a uno o más eventos.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("La categoría con el ID especificado no existe.");
        }
    }

    public List<CategoriaDTO> findAll(){
        List<Categoria> listado = categoriaRepository.findAll();
        List<CategoriaDTO> listadoDTO = new ArrayList<>();
        for (Categoria cat: listado){
            listadoDTO.add(convertirCategoriaDTO(cat));
        }
        return listadoDTO;
    }
}
