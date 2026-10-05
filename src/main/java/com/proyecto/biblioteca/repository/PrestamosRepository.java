package com.proyecto.biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.biblioteca.entity.Prestamos;

@Repository
public interface PrestamosRepository extends JpaRepository<Prestamos, Integer> {
    List<Prestamos> findByEstadoTrue();
}
