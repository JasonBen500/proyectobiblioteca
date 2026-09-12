package com.proyecto.biblioteca.dto;

import lombok.Data;

@Data 
    public class UsuariosDTO {
    private Integer idUsuario;
    private Integer idPerfil;
    private String nombre;
    private String usuario;
    private String contrasena;
    private String correo;
    private Boolean estado;
}
