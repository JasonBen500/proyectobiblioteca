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

import com.proyecto.biblioteca.dto.SociosDTO;
import com.proyecto.biblioteca.service.SociosService;

@RestController
@RequestMapping("/socios")
public class SociosController {

    private final SociosService sociosService;

    public SociosController(SociosService sociosService) {
        this.sociosService = sociosService;
    }

    @GetMapping
    public List<SociosDTO> mostrar() {
        return sociosService.mostrar();
    }

    @PostMapping
    public SociosDTO agregar(@RequestBody SociosDTO dto) {
        return sociosService.agregar(dto);
    }

    @PutMapping("/{id}")
    public SociosDTO modificar(@PathVariable Integer id, @RequestBody SociosDTO dto) {
        return sociosService.modificar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        sociosService.eliminar(id);
    }

    @PatchMapping("/{id}/anular")
    public void anular(@PathVariable Integer id) {
        sociosService.anular(id);
    }
}
