package com.proyecto.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.biblioteca.entity.Usuarios;

@Repository 
public interface UsuariosRepository extends JpaRepository <Usuarios, Integer>{

}
