package com.signosp.signospbackend.Service;

import com.signosp.signospbackend.Models.categoria.Categoria;
import com.signosp.signospbackend.Models.categoria.CategoriaDTO;
import com.signosp.signospbackend.Models.categoria.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoriaService {
    public final CategoriaRepository categoriaRepository;
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

    public void modificarCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaRepository.findById(categoriaDTO.getId_categoria()).orElse(null);
        if(categoria == null){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria no encontrada (modificar");
        }
        Categoria categoriaModificada = Categoria.builder()
                .id_categoria(categoriaDTO.getId_categoria())
                .nombre(categoriaDTO.getNombre() != null? categoriaDTO.getNombre() : categoria.getNombre())
                .build();
        categoriaRepository.save(categoriaModificada);
    }
    public void eliminarCategoria(Long id_categoria){
        categoriaRepository.deleteById(id_categoria);
    }
}
