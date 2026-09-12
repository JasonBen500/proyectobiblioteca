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

import com.proyecto.biblioteca.dto.DetallePrestamoDTO;
import com.proyecto.biblioteca.service.DetallePrestamoService;

@RestController
@RequestMapping("/detalle-prestamo")
public class DetallePrestamoController {

    private final DetallePrestamoService detallePrestamoService;

    public DetallePrestamoController(DetallePrestamoService detallePrestamoService) {
        this.detallePrestamoService = detallePrestamoService;
    }

    @GetMapping
    public List<DetallePrestamoDTO> mostrar() {
        return detallePrestamoService.mostrar();
    }

    @PostMapping
    public DetallePrestamoDTO agregar(@RequestBody DetallePrestamoDTO dto) {
        return detallePrestamoService.agregar(dto);
    }

    @PutMapping("/{id}")
    public DetallePrestamoDTO modificar(@PathVariable Integer id, @RequestBody DetallePrestamoDTO dto) {
        return detallePrestamoService.modificar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        detallePrestamoService.eliminar(id);
    }

    @PatchMapping("/{id}/anular")
    public void anular(@PathVariable Integer id, @RequestBody DetallePrestamoDTO dto) {
        detallePrestamoService.anular(id, dto);
    }
}
