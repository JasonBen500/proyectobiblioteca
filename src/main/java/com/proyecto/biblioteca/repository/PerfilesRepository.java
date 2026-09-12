package com.proyecto.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.biblioteca.entity.Perfiles;


@Repository 
public interface PerfilesRepository extends JpaRepository<Perfiles, Integer> {

}
