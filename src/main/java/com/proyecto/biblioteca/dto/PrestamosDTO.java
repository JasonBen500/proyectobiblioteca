package com.proyecto.biblioteca.dto;

import java.util.Date;

import lombok.Data;

@Data
public class PrestamosDTO {
    private Integer idPrestamo;
    private Integer idSocio;
    private Integer idUsuario;
    private Date fechaPrestamo;
    private Date fechaDevolucionEsperada;
    private Boolean estado;
}
