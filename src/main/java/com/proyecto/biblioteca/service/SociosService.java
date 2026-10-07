package com.proyecto.biblioteca.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.proyecto.biblioteca.dto.SociosDTO;
import com.proyecto.biblioteca.entity.Socios;
import com.proyecto.biblioteca.repository.SociosRepository;

@Service
public class SociosService {

    private final SociosRepository sociosRepository;

    public SociosService(SociosRepository sociosRepository) {
        this.sociosRepository = sociosRepository;
    }

    public List<SociosDTO> mostrar() {
        return sociosRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SociosDTO> mostrarActivos() {
        return sociosRepository.findByEstadoTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SociosDTO> buscar(String nombre) {
        return sociosRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SociosDTO agregar(SociosDTO dto) {
        Socios socio = convertToEntity(dto);
        return convertToDTO(sociosRepository.save(socio));
    }

    public SociosDTO modificar(Integer id, SociosDTO dto) {
        Socios socio = sociosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Socio no encontrado"));
        socio.setNombre(dto.getNombre());
        socio.setCarnet(dto.getCarnet());
        socio.setTelefono(dto.getTelefono());
        socio.setEstado(dto.getEstado());
        return convertToDTO(sociosRepository.save(socio));
    }

    public void eliminar(Integer id) {
        sociosRepository.deleteById(id);
    }

    public void anular(Integer id) {
        Socios socio = sociosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Socio no encontrado"));
        socio.setEstado(false);
        sociosRepository.save(socio);
    }

    private SociosDTO convertToDTO(Socios socio) {
        SociosDTO dto = new SociosDTO();
        dto.setIdSocio(socio.getIdSocio());
        dto.setNombre(socio.getNombre());
        dto.setCarnet(socio.getCarnet());
        dto.setTelefono(socio.getTelefono());
        dto.setEstado(socio.getEstado());
        return dto;
    }

    private Socios convertToEntity(SociosDTO dto) {
        Socios socio = new Socios();
        socio.setIdSocio(dto.getIdSocio());
        socio.setNombre(dto.getNombre());
        socio.setCarnet(dto.getCarnet());
        socio.setTelefono(dto.getTelefono());
        socio.setEstado(dto.getEstado());
        return socio;
    }
}