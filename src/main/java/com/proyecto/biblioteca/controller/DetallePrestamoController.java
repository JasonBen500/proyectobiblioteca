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

import com.proyecto.biblioteca.dto.DetallePrestamoDTO;
import com.proyecto.biblioteca.dto.MessageResponse;
import com.proyecto.biblioteca.service.DetallePrestamoService;

@RestController
@RequestMapping("/detalle-prestamo")
@CrossOrigin (origins = "http://localhost:5173/")
public class DetallePrestamoController {

    private final DetallePrestamoService detallePrestamoService;

    public DetallePrestamoController(DetallePrestamoService detallePrestamoService) {
        this.detallePrestamoService = detallePrestamoService;
    }

    @GetMapping
    public List<DetallePrestamoDTO> mostrar() {
        return detallePrestamoService.mostrar();
    }

    @GetMapping("/prestamo/{idPrestamo}")
    public List<DetallePrestamoDTO> mostrarPorPrestamo(@PathVariable Integer idPrestamo) {
        return detallePrestamoService.mostrarPorPrestamo(idPrestamo);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> agregar(@RequestBody DetallePrestamoDTO dto) {
        try {
            detallePrestamoService.agregar(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new MessageResponse("Detalle de préstamo creado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error al crear el detalle de préstamo: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> modificar(@PathVariable Integer id, @RequestBody DetallePrestamoDTO dto) {
        try {
            detallePrestamoService.modificar(id, dto);
            return ResponseEntity.ok(new MessageResponse("Detalle de préstamo modificado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error al modificar el detalle de préstamo: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> eliminar(@PathVariable Integer id) {
        try {
            detallePrestamoService.eliminar(id);
            return ResponseEntity.ok(new MessageResponse("Detalle de préstamo eliminado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error al eliminar el detalle de préstamo: " + e.getMessage()));
        }
    }

    @PatchMapping("/{id}/anular")
    public ResponseEntity<MessageResponse> anular(@PathVariable Integer id, @RequestBody DetallePrestamoDTO dto) {
        try {
            detallePrestamoService.anular(id, dto);
            return ResponseEntity.ok(new MessageResponse("Detalle de préstamo anulado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error al anular el detalle de préstamo: " + e.getMessage()));
        }
    }
}
