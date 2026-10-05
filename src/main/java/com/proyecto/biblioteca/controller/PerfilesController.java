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
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.biblioteca.dto.MessageResponse;
import com.proyecto.biblioteca.dto.PerfilesDTO;
import com.proyecto.biblioteca.service.PerfilesService;

@RestController
@RequestMapping("/perfiles")
@CrossOrigin (origins = "http://localhost:5173/")
public class PerfilesController {

    private final PerfilesService perfilesService;

    public PerfilesController(PerfilesService perfilesService) {
        this.perfilesService = perfilesService;
    }

    @GetMapping
    public List<PerfilesDTO> mostrar() {
        return perfilesService.mostrar();
    }

    @GetMapping("/activos")
    public List<PerfilesDTO> mostrarActivos() {
        return perfilesService.mostrarActivos();
    }

    @PostMapping
    public ResponseEntity<MessageResponse> agregar(@RequestBody PerfilesDTO dto) {
        try {
            perfilesService.agregar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Perfil creado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error al crear el perfil: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> modificar(@PathVariable Integer id, @RequestBody PerfilesDTO dto) {
        try {
            perfilesService.modificar(id, dto);
            return ResponseEntity.ok(new MessageResponse("Perfil modificado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error al modificar el perfil: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> eliminar(@PathVariable Integer id) {
        try {
            perfilesService.eliminar(id);
            return ResponseEntity.ok(new MessageResponse("Perfil eliminado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error al eliminar el perfil: " + e.getMessage()));
        }
    }

    @PatchMapping("/{id}/anular")
    public ResponseEntity<MessageResponse> anular(@PathVariable Integer id) {
        try {
            perfilesService.anular(id);
            return ResponseEntity.ok(new MessageResponse("Perfil anulado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error al anular el perfil: " + e.getMessage()));
        }
    }
}