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

import com.proyecto.biblioteca.dto.UsuariosDTO;
import com.proyecto.biblioteca.service.UsuariosService;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    private final UsuariosService usuariosService;

    public UsuariosController(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    @GetMapping
    public List<UsuariosDTO> mostrar() {
        return usuariosService.mostrar();
    }

    @PostMapping
    public UsuariosDTO agregar(@RequestBody UsuariosDTO dto) {
        return usuariosService.agregar(dto);
    }

    @PutMapping("/{id}")
    public UsuariosDTO modificar(@PathVariable Integer id, @RequestBody UsuariosDTO dto) {
        return usuariosService.modificar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        usuariosService.eliminar(id);
    }

    @PatchMapping("/{id}/anular")
    public void anular(@PathVariable Integer id) {
        usuariosService.anular(id);
    }
}
