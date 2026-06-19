package com.stem.grupoSenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stem.grupoSenda.model.Ruta;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, Long> {
    
} 
