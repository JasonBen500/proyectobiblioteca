package com.proyecto.biblioteca.dto;

import lombok.Data;

@Data 
public class LoginResponseDTO {
    private Integer idUsuario;
    private String nombre;
    private String usuario;
    private Integer idPerfil;
    private String nombrePerfil;
}
