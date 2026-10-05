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
import com.proyecto.biblioteca.dto.PrestamosDTO;
import com.proyecto.biblioteca.service.PrestamosService;

@RestController
@RequestMapping("/prestamos")
@CrossOrigin (origins = "http://localhost:5173/")
public class PrestamosController {

    private final PrestamosService prestamosService;

    public PrestamosController(PrestamosService prestamosService) {
        this.prestamosService = prestamosService;
    }

    @GetMapping
    public List<PrestamosDTO> mostrar() {
        return prestamosService.mostrar();
    }

    @GetMapping("/activos")
    public List<PrestamosDTO> mostrarActivos() {
        return prestamosService.mostrarActivos();
    }

    @PostMapping
    public ResponseEntity<MessageResponse> agregar(@RequestBody PrestamosDTO dto) {
        try {
            prestamosService.agregar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Préstamo creado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error al crear el préstamo: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> modificar(@PathVariable Integer id, @RequestBody PrestamosDTO dto) {
        try {
            prestamosService.modificar(id, dto);
            return ResponseEntity.ok(new MessageResponse("Préstamo modificado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error al modificar el préstamo: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> eliminar(@PathVariable Integer id) {
        try {
            prestamosService.eliminar(id);
            return ResponseEntity.ok(new MessageResponse("Préstamo eliminado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error al eliminar el préstamo: " + e.getMessage()));
        }
    }

    @PatchMapping("/{id}/anular")
    public ResponseEntity<MessageResponse> anular(@PathVariable Integer id) {
        try {
            prestamosService.anular(id);
            return ResponseEntity.ok(new MessageResponse("Préstamo anulado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error al anular el préstamo: " + e.getMessage()));
        }
    }
}
