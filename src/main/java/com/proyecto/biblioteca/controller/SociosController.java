package com.proyecto.biblioteca.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.biblioteca.dto.MessageResponse;
import com.proyecto.biblioteca.dto.SociosDTO;
import com.proyecto.biblioteca.service.SociosService;

@RestController
@RequestMapping("/socios")
@CrossOrigin (origins = "http://localhost:5173/")
public class SociosController {

    private final SociosService sociosService;

    public SociosController(SociosService sociosService) {
        this.sociosService = sociosService;
    }

    @GetMapping
    public List<SociosDTO> mostrar() {
        return sociosService.mostrar();
    }

    @GetMapping("/activos")
    public List<SociosDTO> mostrarActivos() {
        return sociosService.mostrarActivos();
    }

    @GetMapping("/buscar")
    public List<SociosDTO> buscar(@RequestParam String nombre) {
        return sociosService.buscar(nombre);
    }

    @PostMapping
    public ResponseEntity<?> agregar(@RequestBody SociosDTO dto) {
        try {
            SociosDTO creado = sociosService.agregar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Error al crear el socio: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> modificar(@PathVariable Integer id, @RequestBody SociosDTO dto) {
        try {
            sociosService.modificar(id, dto);
            return ResponseEntity.ok(new MessageResponse("Socio modificado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Error al modificar el socio: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> eliminar(@PathVariable Integer id) {
        try {
            sociosService.eliminar(id);
            return ResponseEntity.ok(new MessageResponse("Socio eliminado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Error al eliminar el socio: " + e.getMessage()));
        }
    }

    @PatchMapping("/{id}/anular")
    public ResponseEntity<MessageResponse> anular(@PathVariable Integer id) {
        try {
            sociosService.anular(id);
            return ResponseEntity.ok(new MessageResponse("Socio anulado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Error al anular el socio: " + e.getMessage()));
        }
    }
}

