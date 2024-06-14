package com.signosp.signospbackend.Controller;

import com.signosp.signospbackend.Models.subtarea.SubtareaDTO;
import com.signosp.signospbackend.Service.SubtareaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subtarea")
@RequiredArgsConstructor
public class SubtareaController {
    public final SubtareaService subtareaService;

    @GetMapping
    public List<SubtareaDTO> getAllSubtareas(){
        return subtareaService.findAll();
    }

    @GetMapping("/{id}")
    public SubtareaDTO subtareaById(@PathVariable Long id){
        return subtareaService.byId(id);
    }

    @PostMapping
    public void crearSubtarea(@RequestBody SubtareaDTO subtareaDTO){
        subtareaService.crearSubtarea(subtareaDTO);
    }

    @PutMapping
    public void modificarSubtarea(@RequestBody SubtareaDTO subtareaDTO){
        subtareaService.modificarSubtarea(subtareaDTO);
    }

    @DeleteMapping
    public void eliminarSubtarea(@PathVariable Long id){
        subtareaService.eliminarSubtarea(id);
        }
}
