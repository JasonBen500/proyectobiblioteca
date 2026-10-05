package com.proyecto.biblioteca.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.proyecto.biblioteca.dto.PrestamosDTO;
import com.proyecto.biblioteca.entity.Prestamos;
import com.proyecto.biblioteca.entity.Socios;
import com.proyecto.biblioteca.entity.Usuarios;
import com.proyecto.biblioteca.repository.PrestamosRepository;

@Service
public class PrestamosService {

    private final PrestamosRepository prestamosRepository;

    public PrestamosService(PrestamosRepository prestamosRepository) {
        this.prestamosRepository = prestamosRepository;
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

    private PrestamosDTO convertToDTO(Prestamos prestamo) {
        PrestamosDTO dto = new PrestamosDTO();
        dto.setIdPrestamo(prestamo.getIdPrestamo());
        dto.setIdSocio(prestamo.getIdSocio().getIdSocio());
        dto.setIdUsuario(prestamo.getIdUsuario().getIdUsuario());
        dto.setFechaPrestamo(prestamo.getFechaPrestamo());
        dto.setFechaDevolucionEsperada(prestamo.getFechaDevolucionEsperada());
        dto.setEstado(prestamo.getEstado());
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
