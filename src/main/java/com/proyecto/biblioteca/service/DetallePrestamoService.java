package com.proyecto.biblioteca.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.proyecto.biblioteca.dto.DetallePrestamoDTO;
import com.proyecto.biblioteca.entity.DetallePrestamo;
import com.proyecto.biblioteca.entity.Libros;
import com.proyecto.biblioteca.entity.Prestamos;
import com.proyecto.biblioteca.repository.DetallePrestamoRepository;
import com.proyecto.biblioteca.repository.LibrosRepository;

@Service
public class DetallePrestamoService {

    private final DetallePrestamoRepository detallePrestamoRepository;
    private final LibrosRepository librosRepository;

    public DetallePrestamoService(DetallePrestamoRepository detallePrestamoRepository,
            LibrosRepository librosRepository) {
        this.detallePrestamoRepository = detallePrestamoRepository;
        this.librosRepository = librosRepository;
    }

    public List<DetallePrestamoDTO> mostrar() {
        return detallePrestamoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<DetallePrestamoDTO> mostrarPorPrestamo(Integer idPrestamo) {
        return detallePrestamoRepository.findByIdPrestamo_IdPrestamo(idPrestamo).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public DetallePrestamoDTO agregar(DetallePrestamoDTO dto) {
        Libros libro = librosRepository.findById(dto.getIdLibro())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        if (libro.getEjemplaresDisponibles() <= 0) {
            throw new RuntimeException("No hay ejemplares disponibles de este libro");
        }

        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() - 1);
        librosRepository.save(libro);

        DetallePrestamo detalle = convertToEntity(dto);
        return convertToDTO(detallePrestamoRepository.save(detalle));
    }

    public DetallePrestamoDTO modificar(Integer id, DetallePrestamoDTO dto) {
        DetallePrestamo detalle = detallePrestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de prestamo no encontrado"));
        detalle.setFechaDevolucionReal(dto.getFechaDevolucionReal());
        detalle.setIdPrestamo(new Prestamos(dto.getIdPrestamo()));
        detalle.setIdLibro(new Libros(dto.getIdLibro()));
        return convertToDTO(detallePrestamoRepository.save(detalle));
    }

    public void eliminar(Integer id) {
        DetallePrestamo detalle = detallePrestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de prestamo no encontrado"));

        if (detalle.getFechaDevolucionReal() == null) {
            Libros libro = detalle.getIdLibro();
            libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() + 1);
            librosRepository.save(libro);
        }

        detallePrestamoRepository.deleteById(id);
    }

    public void anular(Integer id, DetallePrestamoDTO dto) {
        DetallePrestamo detalle = detallePrestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de prestamo no encontrado"));

        if (detalle.getFechaDevolucionReal() == null) {
            Libros libro = detalle.getIdLibro();
            libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() + 1);
            librosRepository.save(libro);
        }

        detalle.setFechaDevolucionReal(dto.getFechaDevolucionReal());
        detallePrestamoRepository.save(detalle);
    }

    private DetallePrestamoDTO convertToDTO(DetallePrestamo detalle) {
        DetallePrestamoDTO dto = new DetallePrestamoDTO();
        dto.setIdDetallePrestamo(detalle.getIdDetallePrestamo());
        dto.setIdPrestamo(detalle.getIdPrestamo().getIdPrestamo());
        dto.setIdLibro(detalle.getIdLibro().getIdLibro());
        dto.setFechaDevolucionReal(detalle.getFechaDevolucionReal());
        return dto;
    }

    private DetallePrestamo convertToEntity(DetallePrestamoDTO dto) {
        DetallePrestamo detalle = new DetallePrestamo();
        detalle.setIdDetallePrestamo(dto.getIdDetallePrestamo());
        detalle.setFechaDevolucionReal(dto.getFechaDevolucionReal());
        detalle.setIdPrestamo(new Prestamos(dto.getIdPrestamo()));
        detalle.setIdLibro(new Libros(dto.getIdLibro()));
        return detalle;
    }
}