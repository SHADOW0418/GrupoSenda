package com.stem.grupoSenda.service;

import java.util.List;
import java.util.Optional;

import com.stem.grupoSenda.model.Quedada;

public interface QuedadaService {
    List<Quedada>listarQuedadas();
    Optional<Quedada> obtenerQuedadaPorId(Long id);
    Quedada crearQuedada(Quedada quedada, Long idRuta);
    Quedada actualizarQuedada(Long id, Quedada cuedada);
    void borrarQuedada(Long id);
    Optional<Quedada> agregarUsuario(Long id, Long idUsuario);
    Optional<Quedada> asignarRuta(Long idQuedada, Long idRuta);
    void eliminarUsuarioDeQuedada(Long idQuedada, Long idUsuario);
}
