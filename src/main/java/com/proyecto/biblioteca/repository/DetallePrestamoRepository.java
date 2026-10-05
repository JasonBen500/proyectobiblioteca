package com.proyecto.biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.biblioteca.entity.DetallePrestamo;

@Repository
public interface DetallePrestamoRepository extends JpaRepository<DetallePrestamo, Integer> {
    List<DetallePrestamo> findByEstadoTrue();

    List<DetallePrestamo> findByIdPrestamo_IdPrestamo(Integer idPrestamo);
}
