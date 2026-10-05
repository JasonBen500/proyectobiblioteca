package com.proyecto.biblioteca.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.proyecto.biblioteca.dto.PerfilesDTO;
import com.proyecto.biblioteca.entity.Perfiles;
import com.proyecto.biblioteca.repository.PerfilesRepository;

@Service
public class PerfilesService {

    private final PerfilesRepository perfilesRepository;

    public PerfilesService(PerfilesRepository perfilesRepository) {
        this.perfilesRepository = perfilesRepository;
    }

    public List<PerfilesDTO> mostrar() {
        return perfilesRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PerfilesDTO> mostrarActivos() {
        return perfilesRepository.findByEstadoTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PerfilesDTO agregar(PerfilesDTO dto) {
        Perfiles perfil = convertToEntity(dto);
        return convertToDTO(perfilesRepository.save(perfil));
    }

    public PerfilesDTO modificar(Integer id, PerfilesDTO dto) {
        Perfiles perfil = perfilesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
        perfil.setNombre(dto.getNombre());
        perfil.setEstado(dto.getEstado());
        return convertToDTO(perfilesRepository.save(perfil));
    }

    public void eliminar(Integer id) {
        perfilesRepository.deleteById(id);
    }

    public void anular(Integer id) {
        Perfiles perfil = perfilesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
        perfil.setEstado(false);
        perfilesRepository.save(perfil);
    }

    private PerfilesDTO convertToDTO(Perfiles perfil) {
        PerfilesDTO dto = new PerfilesDTO();
        dto.setIdPerfil(perfil.getIdPerfil());
        dto.setNombre(perfil.getNombre());
        dto.setEstado(perfil.getEstado());
        return dto;
    }

    private Perfiles convertToEntity(PerfilesDTO dto) {
        Perfiles perfil = new Perfiles();
        perfil.setIdPerfil(dto.getIdPerfil());
        perfil.setNombre(dto.getNombre());
        perfil.setEstado(dto.getEstado());
        return perfil;
    }
}
