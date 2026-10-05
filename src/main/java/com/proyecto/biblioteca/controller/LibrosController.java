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

import com.proyecto.biblioteca.dto.LibrosDTO;
import com.proyecto.biblioteca.dto.MessageResponse;
import com.proyecto.biblioteca.service.LibrosService;

@RestController
@RequestMapping("/libros")
@CrossOrigin (origins = "http://localhost:5173/")
public class LibrosController {

    private final LibrosService librosService;

    public LibrosController(LibrosService librosService) {
        this.librosService = librosService;
    }

    @GetMapping
    public List<LibrosDTO> mostrar() {
        return librosService.mostrar();
    }

    @GetMapping("/activos")
    public List<LibrosDTO> mostrarActivos() {
        return librosService.mostrarActivos();
    }

    @PostMapping
    public ResponseEntity<MessageResponse> agregar(@RequestBody LibrosDTO dto) {
        try {
            librosService.agregar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Libro creado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error al crear el libro: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> modificar(@PathVariable Integer id, @RequestBody LibrosDTO dto) {
        try {
            librosService.modificar(id, dto);
            return ResponseEntity.ok(new MessageResponse("Libro modificado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error al modificar el libro: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> eliminar(@PathVariable Integer id) {
        try {
            librosService.eliminar(id);
            return ResponseEntity.ok(new MessageResponse("Libro eliminado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error al eliminar el libro: " + e.getMessage()));
        }
    }

    @PatchMapping("/{id}/anular")
    public ResponseEntity<MessageResponse> anular(@PathVariable Integer id) {
        try {
            librosService.anular(id);
            return ResponseEntity.ok(new MessageResponse("Libro anulado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error al anular el libro: " + e.getMessage()));
        }
    }
}
