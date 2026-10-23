package com.proyecto.biblioteca.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.biblioteca.dto.PrestamoRegistroDTO;
import com.proyecto.biblioteca.dto.PrestamosDTO;
import com.proyecto.biblioteca.entity.DetallePrestamo;
import com.proyecto.biblioteca.entity.Libros;
import com.proyecto.biblioteca.entity.Prestamos;
import com.proyecto.biblioteca.entity.Socios;
import com.proyecto.biblioteca.entity.Usuarios;
import com.proyecto.biblioteca.repository.DetallePrestamoRepository;
import com.proyecto.biblioteca.repository.LibrosRepository;
import com.proyecto.biblioteca.repository.PrestamosRepository;
import com.proyecto.biblioteca.repository.SociosRepository;

@Service
public class PrestamosService {

    private final PrestamosRepository prestamosRepository;
    private final SociosRepository sociosRepository;
    private final LibrosRepository librosRepository;
    private final DetallePrestamoRepository detallePrestamoRepository;

    public PrestamosService(PrestamosRepository prestamosRepository,
            SociosRepository sociosRepository,
            LibrosRepository librosRepository,
            DetallePrestamoRepository detallePrestamoRepository) {
        this.prestamosRepository = prestamosRepository;
        this.sociosRepository = sociosRepository;
        this.librosRepository = librosRepository;
        this.detallePrestamoRepository = detallePrestamoRepository;
    }

    public List<PrestamosDTO> mostrar() {
        return prestamosRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PrestamosDTO> mostrarActivos() {
        return prestamosRepository.findByEstadoTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PrestamosDTO agregar(PrestamosDTO dto) {
        Prestamos prestamo = convertToEntity(dto);
        return convertToDTO(prestamosRepository.save(prestamo));
    }

    // Registra el préstamo completo en una sola transacción:
    // si algo falla (sin stock, libro inactivo, socio inválido), no se guarda nada.
    @Transactional
    public PrestamosDTO registrar(PrestamoRegistroDTO dto) {
        if (dto.getIdLibros() == null || dto.getIdLibros().isEmpty()) {
            throw new RuntimeException("Debe seleccionar al menos un libro");
        }
        if (dto.getIdUsuario() == null) {
            throw new RuntimeException("No se pudo identificar al usuario que registra el préstamo");
        }
        if (dto.getFechaDevolucionEsperada() == null) {
            throw new RuntimeException("La fecha de devolución esperada es obligatoria");
        }

        Socios socio = obtenerOCrearSocio(dto);

        Prestamos prestamo = new Prestamos();
        prestamo.setFechaPrestamo(dto.getFechaPrestamo() != null ? dto.getFechaPrestamo() : new Date());
        prestamo.setFechaDevolucionEsperada(dto.getFechaDevolucionEsperada());
        prestamo.setEstado(true);
        prestamo.setIdSocio(socio);
        prestamo.setIdUsuario(new Usuarios(dto.getIdUsuario()));
        prestamo = prestamosRepository.save(prestamo);

        List<Integer> idsLibros = dto.getIdLibros().stream().distinct().collect(Collectors.toList());

        for (Integer idLibro : idsLibros) {
            Libros libro = librosRepository.findById(idLibro)
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
            detalle.setIdPrestamo(prestamo);
            detalle.setIdLibro(libro);
            detalle.setFechaDevolucionReal(null);
            detalle.setEstado(true);
            detallePrestamoRepository.save(detalle);
        }

        return convertToDTO(prestamo);
    }

    public PrestamosDTO modificar(Integer id, PrestamosDTO dto) {
        Prestamos prestamo = prestamosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestamo no encontrado"));
        prestamo.setFechaPrestamo(dto.getFechaPrestamo());
        prestamo.setFechaDevolucionEsperada(dto.getFechaDevolucionEsperada());
        prestamo.setEstado(dto.getEstado());
        prestamo.setIdSocio(new Socios(dto.getIdSocio()));
        prestamo.setIdUsuario(new Usuarios(dto.getIdUsuario()));
        return convertToDTO(prestamosRepository.save(prestamo));
    }

    public void eliminar(Integer id) {
        prestamosRepository.deleteById(id);
    }

    public void anular(Integer id) {
        Prestamos prestamo = prestamosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestamo no encontrado"));
        prestamo.setEstado(false);
        prestamosRepository.save(prestamo);
    }

    private Socios obtenerOCrearSocio(PrestamoRegistroDTO dto) {
        if (dto.getIdSocio() != null) {
            Socios socio = sociosRepository.findById(dto.getIdSocio())
                    .orElseThrow(() -> new RuntimeException("Socio no encontrado"));
            if (!socio.getEstado()) {
                throw new RuntimeException("El socio \"" + socio.getNombre() + "\" está inactivo");
            }
            return socio;
        }

        if (dto.getNombreSocio() == null || dto.getNombreSocio().isBlank()) {
            throw new RuntimeException("Debe seleccionar un socio o ingresar los datos de uno nuevo");
        }

        Socios nuevo = new Socios();
        nuevo.setNombre(dto.getNombreSocio().trim());
        nuevo.setCarnet(dto.getCarnetSocio());
        nuevo.setTelefono(dto.getTelefonoSocio());
        nuevo.setEstado(true);
        return sociosRepository.save(nuevo);
    }

    private PrestamosDTO convertToDTO(Prestamos prestamo) {
        PrestamosDTO dto = new PrestamosDTO();
        dto.setIdPrestamo(prestamo.getIdPrestamo());
        dto.setIdSocio(prestamo.getIdSocio().getIdSocio());
        dto.setIdUsuario(prestamo.getIdUsuario().getIdUsuario());
        dto.setFechaPrestamo(prestamo.getFechaPrestamo());
        dto.setFechaDevolucionEsperada(prestamo.getFechaDevolucionEsperada());
        dto.setEstado(prestamo.getEstado());
        dto.setNombreSocio(prestamo.getIdSocio().getNombre());
        dto.setNombreUsuario(prestamo.getIdUsuario().getNombre());
        return dto;
    }

    private Prestamos convertToEntity(PrestamosDTO dto) {
        Prestamos prestamo = new Prestamos();
        prestamo.setIdPrestamo(dto.getIdPrestamo());
        prestamo.setFechaPrestamo(dto.getFechaPrestamo());
        prestamo.setFechaDevolucionEsperada(dto.getFechaDevolucionEsperada());
        prestamo.setEstado(dto.getEstado());
        prestamo.setIdSocio(new Socios(dto.getIdSocio()));
        prestamo.setIdUsuario(new Usuarios(dto.getIdUsuario()));
        return prestamo;
    }
}
