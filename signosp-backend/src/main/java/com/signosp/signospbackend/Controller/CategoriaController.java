package com.signosp.signospbackend.Controller;

import com.signosp.signospbackend.Models.categoria.Categoria;
import com.signosp.signospbackend.Models.categoria.CategoriaDTO;
import com.signosp.signospbackend.Models.categoria.CategoriaRepository;
import com.signosp.signospbackend.Service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categoria")
@RequiredArgsConstructor
public class CategoriaController {
    public final CategoriaRepository categoriaRepository;
    public final CategoriaService categoriaService;

    @GetMapping
    public List<Categoria> getAllCategorias(){
        return categoriaRepository.findAll();
    }
    @GetMapping({"/{id}"})
    public CategoriaDTO categoriaById(@PathVariable Long id){
        return categoriaService.getCategoriaById(id);
    }
    @PostMapping
    public void createCategoria(@RequestBody CategoriaDTO categoriaDTO){
        categoriaService.crearCategoria(categoriaDTO);
    }
    @PutMapping
    public void modificarCliente(@RequestBody CategoriaDTO categoriaDTO){
        categoriaService.modificarCategoria(categoriaDTO);
    }




}