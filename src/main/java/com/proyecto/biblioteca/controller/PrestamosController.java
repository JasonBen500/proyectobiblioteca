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

import com.proyecto.biblioteca.dto.PrestamosDTO;
import com.proyecto.biblioteca.service.PrestamosService;

@RestController
@RequestMapping("/prestamos")
public class PrestamosController {

    private final PrestamosService prestamosService;

    public PrestamosController(PrestamosService prestamosService) {
        this.prestamosService = prestamosService;
    }

    @GetMapping
    public List<PrestamosDTO> mostrar() {
        return prestamosService.mostrar();
    }

    @PostMapping
    public PrestamosDTO agregar(@RequestBody PrestamosDTO dto) {
        return prestamosService.agregar(dto);
    }

    @PutMapping("/{id}")
    public PrestamosDTO modificar(@PathVariable Integer id, @RequestBody PrestamosDTO dto) {
        return prestamosService.modificar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        prestamosService.eliminar(id);
    }

    @PatchMapping("/{id}/anular")
    public void anular(@PathVariable Integer id) {
        prestamosService.anular(id);
    }
}
