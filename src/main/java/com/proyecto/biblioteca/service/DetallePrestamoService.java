package com.proyecto.biblioteca.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.biblioteca.dto.DetallePrestamoDTO;
import com.proyecto.biblioteca.entity.DetallePrestamo;
import com.proyecto.biblioteca.entity.Libros;
import com.proyecto.biblioteca.entity.Prestamos;
import com.proyecto.biblioteca.repository.DetallePrestamoRepository;
import com.proyecto.biblioteca.repository.LibrosRepository;
import com.proyecto.biblioteca.repository.PrestamosRepository;

@Service
public class DetallePrestamoService {

    private final DetallePrestamoRepository detallePrestamoRepository;
    private final LibrosRepository librosRepository;
    private final PrestamosRepository prestamosRepository;

    public DetallePrestamoService(DetallePrestamoRepository detallePrestamoRepository,
            LibrosRepository librosRepository,
            PrestamosRepository prestamosRepository) {
        this.detallePrestamoRepository = detallePrestamoRepository;
        this.librosRepository = librosRepository;
        this.prestamosRepository = prestamosRepository;
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

    @Transactional
    public DetallePrestamoDTO agregar(DetallePrestamoDTO dto) {
        Prestamos prestamo = prestamosRepository.findById(dto.getIdPrestamo())
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

        if (!prestamo.getEstado()) {
            throw new RuntimeException("El préstamo está anulado");
        }

        Libros libro = librosRepository.findById(dto.getIdLibro())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        if (!libro.getEstado()) {
            throw new RuntimeException("El libro \"" + libro.getTitulo() + "\" no está activo");
        }
        if (libro.getEjemplaresDisponibles() <= 0) {
            throw new RuntimeException("No hay ejemplares disponibles de \"" + libro.getTitulo() + "\"");
        }

        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() - 1);
        librosRepository.save(libro);

        DetallePrestamo detalle = new DetallePrestamo();
       detalle.setFechaDevolucionReal(null);
        detalle.setEstado(true);
        detalle.setIdPrestamo(prestamo);
        detalle.setIdLibro(libro);
        return convertToDTO(detallePrestamoRepository.save(detalle));
    }

    // Ajusta el stock según el cambio: un detalle "pendiente" (sin fecha de
    // devolución)
    // tiene un ejemplar fuera de la biblioteca.
    @Transactional
    public DetallePrestamoDTO modificar(Integer id, DetallePrestamoDTO dto) {
        DetallePrestamo detalle = detallePrestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de prestamo no encontrado"));

        Prestamos prestamo = prestamosRepository.findById(dto.getIdPrestamo())
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

        Libros libroAnterior = detalle.getIdLibro();
        boolean pendienteAntes = detalle.getFechaDevolucionReal() == null;
        boolean pendienteDespues = dto.getFechaDevolucionReal() == null;

        // El libro anterior recupera su ejemplar si estaba pendiente
        if (pendienteAntes) {
            libroAnterior.setEjemplaresDisponibles(libroAnterior.getEjemplaresDisponibles() + 1);
            librosRepository.save(libroAnterior);
        }

        Libros libroNuevo = librosRepository.findById(dto.getIdLibro())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        // El libro nuevo entrega un ejemplar si queda pendiente
        if (pendienteDespues) {
            if (!libroNuevo.getEstado()) {
                throw new RuntimeException("El libro \"" + libroNuevo.getTitulo() + "\" no está activo");
            }
            if (libroNuevo.getEjemplaresDisponibles() <= 0) {
                throw new RuntimeException("No hay ejemplares disponibles de \"" + libroNuevo.getTitulo() + "\"");
            }
            libroNuevo.setEjemplaresDisponibles(libroNuevo.getEjemplaresDisponibles() - 1);
            librosRepository.save(libroNuevo);
        }

        detalle.setFechaDevolucionReal(dto.getFechaDevolucionReal());
        detalle.setEstado(pendienteDespues);
        detalle.setIdPrestamo(prestamo);
        detalle.setIdLibro(libroNuevo);
        return convertToDTO(detallePrestamoRepository.save(detalle));
    }

    @Transactional
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

    // "Anular" un detalle = registrar la devolución del libro.
    // Solo se puede hacer una vez, así el stock no se infla.
    @Transactional
    public void anular(Integer id, DetallePrestamoDTO dto) {
        DetallePrestamo detalle = detallePrestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de prestamo no encontrado"));

        if (detalle.getFechaDevolucionReal() != null) {
            throw new RuntimeException("Este libro ya fue devuelto");
        }

        Libros libro = detalle.getIdLibro();
        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() + 1);
        librosRepository.save(libro);

        Date fecha = dto.getFechaDevolucionReal() != null ? dto.getFechaDevolucionReal() : new Date();
        detalle.setFechaDevolucionReal(fecha);
        detalle.setEstado(false);
        detallePrestamoRepository.save(detalle);
    }

    private DetallePrestamoDTO convertToDTO(DetallePrestamo detalle) {
        DetallePrestamoDTO dto = new DetallePrestamoDTO();
        dto.setIdDetallePrestamo(detalle.getIdDetallePrestamo());
        dto.setIdPrestamo(detalle.getIdPrestamo().getIdPrestamo());
        dto.setIdLibro(detalle.getIdLibro().getIdLibro());
        dto.setFechaDevolucionReal(detalle.getFechaDevolucionReal());
        dto.setEstado(detalle.getEstado());
        dto.setDescripcionPrestamo("Préstamo #" + detalle.getIdPrestamo().getIdPrestamo());
        dto.setNombreLibro(detalle.getIdLibro().getTitulo());
        if (detalle.getIdPrestamo().getIdSocio() != null) {
            dto.setNombreSocio(detalle.getIdPrestamo().getIdSocio().getNombre());
        }
        return dto;
    }
}