package com.proyecto.biblioteca.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class PrestamoRegistroDTO {
    private Integer idSocio;
    private String nombreSocio;
    private String carnetSocio;
    private String telefonoSocio;
    private Integer idUsuario;
    private Date fechaPrestamo;
    private Date fechaDevolucionEsperada;
    private List<Integer> idLibros;
}
