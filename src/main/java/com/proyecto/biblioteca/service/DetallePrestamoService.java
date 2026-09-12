package com.proyecto.biblioteca.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.proyecto.biblioteca.dto.DetallePrestamoDTO;
import com.proyecto.biblioteca.entity.DetallePrestamo;
import com.proyecto.biblioteca.entity.Libros;
import com.proyecto.biblioteca.entity.Prestamos;
import com.proyecto.biblioteca.repository.DetallePrestamoRepository;

@Service
public class DetallePrestamoService {

    private final DetallePrestamoRepository detallePrestamoRepository;

    public DetallePrestamoService(DetallePrestamoRepository detallePrestamoRepository) {
        this.detallePrestamoRepository = detallePrestamoRepository;
    }

    public List<DetallePrestamoDTO> mostrar() {
        return detallePrestamoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public DetallePrestamoDTO agregar(DetallePrestamoDTO dto) {
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
        detallePrestamoRepository.deleteById(id);
    }

    public void anular(Integer id, DetallePrestamoDTO dto) {
        DetallePrestamo detalle = detallePrestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de prestamo no encontrado"));
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