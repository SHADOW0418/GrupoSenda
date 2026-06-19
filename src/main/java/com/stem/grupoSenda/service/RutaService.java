package com.stem.grupoSenda.service;

import java.util.List;
import java.util.Optional;

import com.stem.grupoSenda.model.Ruta;

public interface RutaService {
    List<Ruta>listarRuta();
    Optional<Ruta> obtenerRutaPorId(Long id);
    Ruta crearRuta(Ruta tarea);
    Optional<Ruta> actualizarRuta(Long id, Ruta Ruta);
    void borrarRuta(Long id);
}
