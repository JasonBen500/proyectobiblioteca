package com.proyecto.biblioteca.dto;

import lombok.Data;

@Data 
public class SociosDTO {
    private Integer idSocio;
    private String nombre;
    private String carnet;
    private String telefono;
    private Boolean estado;
}
