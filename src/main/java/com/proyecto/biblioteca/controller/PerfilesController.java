package com.proyecto.biblioteca.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.biblioteca.dto.PerfilesDTO;
import com.proyecto.biblioteca.service.PerfilesService;

@RestController
@RequestMapping("/perfiles")
public class PerfilesController {

    private final PerfilesService perfilesService;

    public PerfilesController(PerfilesService perfilesService) {
        this.perfilesService = perfilesService;
    }

    @GetMapping
    public List<PerfilesDTO> mostrar() {
        return perfilesService.mostrar();
    }

    @PostMapping
    public PerfilesDTO agregar(@RequestBody PerfilesDTO dto) {
        return perfilesService.agregar(dto);
    }

    @PutMapping("/{id}")
    public PerfilesDTO modificar(@PathVariable Integer id, @RequestBody PerfilesDTO dto) {
        return perfilesService.modificar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        perfilesService.eliminar(id);
    }

    @PatchMapping("/{id}/anular")
    public void anular(@PathVariable Integer id) {
        perfilesService.anular(id);
    }
}
