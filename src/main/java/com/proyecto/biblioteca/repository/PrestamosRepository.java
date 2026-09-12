package com.proyecto.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.biblioteca.entity.Prestamos;

@Repository 
public interface PrestamosRepository extends JpaRepository <Prestamos, Integer> {

}
