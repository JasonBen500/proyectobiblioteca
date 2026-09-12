package com.proyecto.biblioteca.dto;

import java.util.Date;

import lombok.Data;

@Data 
public class DetallePrestamoDTO {
    private Integer idDetallePrestamo;
    private Integer idPrestamo;
    private Integer idLibro;
    private Date fechaDevolucionReal;
}
