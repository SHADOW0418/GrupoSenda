package com.stem.grupoSenda.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stem.grupoSenda.model.Ruta;
import com.stem.grupoSenda.repository.RutaRepository;
import com.stem.grupoSenda.service.RutaService;

/**
 * Implementación del servicio de rutas.
 * Esta clase contiene la lógica de negocio para gestionar las rutas de senderismo,
 * encargándose de listarlas, buscarlas, crearlas, borrarlas o editarlas de forma parcial.
 * * @author Alex Fernandez Orta
 * @version 1.0
 */
@Service
public class RutaServiceImpl implements RutaService {
    @Autowired
    private RutaRepository rutaRepository;

    /**
     * Busca y devuelve una lista con todas las rutas que hay guardadas en el sistema.
     * * @return Una lista que contiene todas las rutas encontradas.
     */
    @Override
    public List<Ruta> listarRuta() {
        // Aquí podrías aplicar lógica extra si lo necesitas
        return rutaRepository.findAll();
    }

    /**
     * Busca una ruta específica utilizando su número de id.
     * * @param id El número identificador de la ruta que queremos encontrar.
     * @return Un Optional que contiene la ruta si existe, o vacío si no se encuentra.
     */
    @Override
    public Optional<Ruta> obtenerRutaPorId(Long id) {
        return rutaRepository.findById(id);
    }

    /**
     * Guarda una nueva ruta en la base de datos.
     * * @param ruta Los datos de la ruta que se va a crear.
     * @return La ruta ya guardada con su id generado.
     */
    @Override
    public Ruta crearRuta(Ruta ruta){
        return rutaRepository.save(ruta);
    }

    /**
     * Actualiza una ruta existente modificando solo los detalles que se hayan enviado con cambios.
     * Permite editar campos como el nombre, la descripción, la dificultad o la distancia
     * de manera independiente, sin obligar a reenviar toda la información de la ruta.
     * * @param id El número identificador de la ruta que queremos modificar.
     * @param nuevaRuta Objeto que contiene las novedades que vamos a aplicar en la ruta.
     * @return Un Optional que contiene la ruta ya actualizada con los nuevos datos.
     */
    @Override
    public Optional<Ruta> actualizarRuta(Long id, Ruta nuevaRuta) {
        Optional<Ruta> result = obtenerRutaPorId(id);
        Ruta rutaActualizada = null;

        if (result.isPresent()) {
            Ruta rutaExistente = result.get();
            nuevaRuta.setId(id);

            if (nuevaRuta.getNombre() != null) {
                rutaExistente.setNombre(nuevaRuta.getNombre());
            }
            if (nuevaRuta.getDescripcion() != null) {
                rutaExistente.setDescripcion(nuevaRuta.getDescripcion());
            }
            if (nuevaRuta.getDesnivel() != null) {
                rutaExistente.setDesnivel(nuevaRuta.getDesnivel());
            }
            if (nuevaRuta.getDificultad() != null) {
                rutaExistente.setDificultad(nuevaRuta.getDificultad());
            }
            if (nuevaRuta.getAltitud() != null) {
                rutaExistente.setAltitud(nuevaRuta.getAltitud());
            }
            if (nuevaRuta.getDistanciaKm() != null) {
                rutaExistente.setDistanciaKm(nuevaRuta.getDistanciaKm());
            }

            rutaActualizada = rutaRepository.save(rutaExistente);
        }
        return Optional.of(rutaActualizada);
        
    }

    /**
     * Elimina una ruta de la base de datos utilizando su número de id.
     * * @param id El número de id de la ruta que se quiere borrar.
     */
    @Override
    public void borrarRuta(Long id) {
        rutaRepository.deleteById(id);
    }
}