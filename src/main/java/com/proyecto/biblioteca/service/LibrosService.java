package com.proyecto.biblioteca.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.proyecto.biblioteca.dto.LibrosDTO;
import com.proyecto.biblioteca.entity.Libros;
import com.proyecto.biblioteca.repository.LibrosRepository;

@Service
public class LibrosService {

    private final LibrosRepository librosRepository;

    public LibrosService(LibrosRepository librosRepository) {
        this.librosRepository = librosRepository;
    }

    public List<LibrosDTO> mostrar() {
        return librosRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<LibrosDTO> mostrarActivos() {
    return librosRepository.findByEstadoTrue().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
}

    public LibrosDTO agregar(LibrosDTO dto) {
        Libros libro = convertToEntity(dto);
        return convertToDTO(librosRepository.save(libro));
    }

    public LibrosDTO modificar(Integer id, LibrosDTO dto) {
        Libros libro = librosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        libro.setTitulo(dto.getTitulo());
        libro.setAutor(dto.getAutor());
        libro.setIsbn(dto.getIsbn());
        libro.setEjemplaresDisponibles(dto.getEjemplaresDisponibles());
        libro.setEstado(dto.getEstado());
        return convertToDTO(librosRepository.save(libro));
    }

    public void eliminar(Integer id) {
        librosRepository.deleteById(id);
    }

    public void anular(Integer id) {
        Libros libro = librosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        libro.setEstado(false);
        librosRepository.save(libro);
    }

    private LibrosDTO convertToDTO(Libros libro) {
        LibrosDTO dto = new LibrosDTO();
        dto.setIdLibro(libro.getIdLibro());
        dto.setTitulo(libro.getTitulo());
        dto.setAutor(libro.getAutor());
        dto.setIsbn(libro.getIsbn());
        dto.setEjemplaresDisponibles(libro.getEjemplaresDisponibles());
        dto.setEstado(libro.getEstado());
        return dto;
    }

    private Libros convertToEntity(LibrosDTO dto) {
        Libros libro = new Libros();
        libro.setIdLibro(dto.getIdLibro());
        libro.setTitulo(dto.getTitulo());
        libro.setAutor(dto.getAutor());
        libro.setIsbn(dto.getIsbn());
        libro.setEjemplaresDisponibles(dto.getEjemplaresDisponibles());
        libro.setEstado(dto.getEstado());
        return libro;
    }
}
