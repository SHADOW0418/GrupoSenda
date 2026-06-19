package com.stem.grupoSenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stem.grupoSenda.model.Quedada;

@Repository
public interface QuedadaRepository extends JpaRepository<Quedada, Long> {
    
} 
