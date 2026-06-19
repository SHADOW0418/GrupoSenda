package com.stem.grupoSenda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.stem.grupoSenda.model.Quedada;
import com.stem.grupoSenda.model.Ruta;
import com.stem.grupoSenda.service.RutaService;

/**
 * Controlador para gestionar todas las operaciones de las rutas en la API.
 * Se encarga de recibir las peticiones web para listar, ver, crear, 
 * actualizar y borrar rutas, así como consultar las quedadas asociadas.
 * * @author Alex Fernandez Orta
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/rutas")
public class RutaController {
    @Autowired
    private RutaService rutaService;

    /**
     * Obtiene una lista con todas las rutas disponibles.
     * * @return Una lista que contiene todas las rutas.
     */
    @GetMapping
    public List<Ruta> obtenerTodos(){
        return rutaService.listarRuta();
    }

    /**
     * Busca y devuelve una ruta específica utilizando su número de id.
     * * @param id El número identificador de la ruta que queremos buscar.
     * @return La ruta encontrada.
     */
    @GetMapping("/{id}")
    public Ruta obtenerPorId(@PathVariable Long id){
        return rutaService.obtenerRutaPorId(id).orElseThrow();
    }

    /**
     * Guarda una nueva ruta en el sistema.
     * * @param ruta Los datos de la ruta que se va a crear.
     * @return La ruta ya guardada con su id asignado.
     */
    @PostMapping
    public Ruta crearRuta(@RequestBody Ruta ruta) {
        return rutaService.crearRuta(ruta);
    }

    /**
     * Modifica los datos de una ruta que ya existe.
     * * @param id El número de id de la ruta que queremos cambiar.
     * @param Ruta Los nuevos datos que le vamos a poner a la ruta.
     * @return La ruta ya actualizada con los cambios, o null si no se encontró.
     */
    @PutMapping("/{id}")
    public Ruta actualizarRuta(@PathVariable Long id, @RequestBody Ruta Ruta) {
        return rutaService.actualizarRuta(id, Ruta).orElse(null);
    }

    /**
     * Elimina una ruta del sistema usando su número de id.
     * * @param id El número de id de la ruta que se quiere borrar.
     */
    @DeleteMapping("/{id}")
    public void borrarRuta(@PathVariable Long id) {
        rutaService.borrarRuta(id);
    }

    /**
     * Obtiene la lista de todas las quedadas que se van a hacer en una ruta concreta.
     * * @param id El número de id de la ruta.
     * @return Una lista con las quedadas organizadas para esa ruta.
     */
    @GetMapping("/{id}/quedadas")
    public List<Quedada> getQuedadasByRutaId(@PathVariable Long id) {
        return rutaService.obtenerRutaPorId(id).get().getQuedadas();
    }
}