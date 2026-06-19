package com.stem.grupoSenda.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stem.grupoSenda.model.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    Optional<Comentario> findByUsuarioIdAndQuedadaId(Long usuarioId, Long quedadaId);
} 
