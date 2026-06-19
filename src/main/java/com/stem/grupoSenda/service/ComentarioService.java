package com.stem.grupoSenda.service;

import java.util.List;
import java.util.Optional;

import com.stem.grupoSenda.model.Comentario;

public interface ComentarioService {
    List<Comentario>listarComentarios();
    Optional<Comentario> obtenerComentarioPorId(Long id);
    Comentario crearComentario(Comentario comentario);
    Optional<Comentario> actualizarComentario(Long id, Comentario comentario);
    void borrarComentario(Long id);
}
