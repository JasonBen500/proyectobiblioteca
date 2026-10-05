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

import com.proyecto.biblioteca.dto.LoginRequestDTO;
import com.proyecto.biblioteca.dto.LoginResponseDTO;
import com.proyecto.biblioteca.dto.MessageResponse;
import com.proyecto.biblioteca.dto.UsuariosDTO;
import com.proyecto.biblioteca.service.UsuariosService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:5173/")
public class UsuariosController {

    private final UsuariosService usuariosService;

    public UsuariosController(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    @GetMapping
    public List<UsuariosDTO> mostrar() {
        return usuariosService.mostrar();
    }

    @GetMapping("/activos")
    public List<UsuariosDTO> mostrarActivos() {
        return usuariosService.mostrarActivos();
    }

    @PostMapping
    public ResponseEntity<MessageResponse> agregar(@RequestBody UsuariosDTO dto) {
        try {
            usuariosService.agregar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Usuario creado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error al crear el usuario: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> modificar(@PathVariable Integer id, @RequestBody UsuariosDTO dto) {
        try {
            usuariosService.modificar(id, dto);
            return ResponseEntity.ok(new MessageResponse("Usuario modificado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error al modificar el usuario: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> eliminar(@PathVariable Integer id) {
        try {
            usuariosService.eliminar(id);
            return ResponseEntity.ok(new MessageResponse("Usuario eliminado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error al eliminar el usuario: " + e.getMessage()));
        }
    }

    @PatchMapping("/{id}/anular")
    public ResponseEntity<MessageResponse> anular(@PathVariable Integer id) {
        try {
            usuariosService.anular(id);
            return ResponseEntity.ok(new MessageResponse("Usuario anulado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error al anular el usuario: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto) {
        try {
            LoginResponseDTO response = usuariosService.login(dto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Error al iniciar sesión: " + e.getMessage()));
        }
    }
}
