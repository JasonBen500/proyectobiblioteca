package com.proyecto.biblioteca.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.proyecto.biblioteca.dto.UsuariosDTO;
import com.proyecto.biblioteca.entity.Perfiles;
import com.proyecto.biblioteca.entity.Usuarios;
import com.proyecto.biblioteca.repository.UsuariosRepository;

@Service
public class UsuariosService {

    private final UsuariosRepository usuariosRepository;

    public UsuariosService(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    public List<UsuariosDTO> mostrar() {
        return usuariosRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UsuariosDTO agregar(UsuariosDTO dto) {
        Usuarios usuario = convertToEntity(dto);
        return convertToDTO(usuariosRepository.save(usuario));
    }

    public UsuariosDTO modificar(Integer id, UsuariosDTO dto) {
        Usuarios usuario = usuariosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setNombre(dto.getNombre());
        usuario.setUsuario(dto.getUsuario());
        usuario.setContrasena(dto.getContrasena());
        usuario.setCorreo(dto.getCorreo());
        usuario.setEstado(dto.getEstado());
        usuario.setPerfiles(new Perfiles(dto.getIdPerfil()));
        return convertToDTO(usuariosRepository.save(usuario));
    }

    public void eliminar(Integer id) {
        usuariosRepository.deleteById(id);
    }

    public void anular(Integer id) {
        Usuarios usuario = usuariosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setEstado(false);
        usuariosRepository.save(usuario);
    }

    private UsuariosDTO convertToDTO(Usuarios usuario) {
        UsuariosDTO dto = new UsuariosDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setIdPerfil(usuario.getIdUsuario());
        dto.setNombre(usuario.getNombre());
        dto.setUsuario(usuario.getUsuario());
        dto.setContrasena(usuario.getContrasena());
        dto.setCorreo(usuario.getCorreo());
        dto.setEstado(usuario.getEstado());
        return dto;
    }

    private Usuarios convertToEntity(UsuariosDTO dto) {
        Usuarios usuario = new Usuarios();
        usuario.setIdUsuario(dto.getIdUsuario());
        usuario.setNombre(dto.getNombre());
        usuario.setUsuario(dto.getUsuario());
        usuario.setContrasena(dto.getContrasena());
        usuario.setCorreo(dto.getCorreo());
        usuario.setEstado(dto.getEstado());
        usuario.setPerfiles(new Perfiles(dto.getIdPerfil()));
        return usuario;
    }
}