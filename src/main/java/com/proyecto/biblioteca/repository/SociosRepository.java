package com.proyecto.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.biblioteca.entity.Socios;


@Repository 
public interface SociosRepository extends JpaRepository <Socios, Integer> {

}
