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

import com.proyecto.biblioteca.dto.LibrosDTO;
import com.proyecto.biblioteca.service.LibrosService;

@RestController
@RequestMapping("/libros")
public class LibrosController {

    private final LibrosService librosService;

    public LibrosController(LibrosService librosService) {
        this.librosService = librosService;
    }

    @GetMapping
    public List<LibrosDTO> mostrar() {
        return librosService.mostrar();
    }

    @PostMapping
    public LibrosDTO agregar(@RequestBody LibrosDTO dto) {
        return librosService.agregar(dto);
    }

    @PutMapping("/{id}")
    public LibrosDTO modificar(@PathVariable Integer id, @RequestBody LibrosDTO dto) {
        return librosService.modificar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        librosService.eliminar(id);
    }

    @PatchMapping("/{id}/anular")
    public void anular(@PathVariable Integer id) {
        librosService.anular(id);
    }
}
