package com.proyecto.biblioteca.dto;

import lombok.Data;

@Data 
public class LibrosDTO {
    private Integer idLibro;
    private String titulo;
    private String autor;
    private String isbn;
    private Integer ejemplaresDisponibles;
    private Boolean estado;
}
