package com.stem.grupoSenda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.stem.grupoSenda.model.Comentario;
import com.stem.grupoSenda.model.Quedada;
import com.stem.grupoSenda.model.Usuario;
import com.stem.grupoSenda.service.QuedadaService;

/**
 * Controlador para gestionar todas las operaciones de las quedadas en la API.
 * Permite listar, crear, modificar y borrar quedadas, además de controlar 
 * los usuarios, rutas y comentarios asociados a cada una.
 * * @author Alex Fernandez Orta
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/quedadas")
public class QuedadaController {
    @Autowired
    private QuedadaService quedadaService;

    /**
     * Obtiene una lista con todas las quedadas registradas.
     * * @return Una lista que contiene todas las quedadas.
     */
    @GetMapping
    public List<Quedada> obtenerTodos(){
        return quedadaService.listarQuedadas();
    }

    /**
     * Busca y devuelve una quedada según su número de id.
     * * @param id El número identificador de la quedada que queremos buscar.
     * @return La quedada encontrada.
     */
    @GetMapping("/{id}")
    public Quedada obtenerPorId(@PathVariable Long id){
        return quedadaService.obtenerQuedadaPorId(id).orElseThrow();
    }

    /**
     * Crea una nueva quedada y la asocia a una ruta concreta.
     * * @param quedada Los datos de la quedada que se va a registrar.
     * @param idRuta El id de la ruta que se va a asignar a esta quedada.
     * @return La quedada creada con su id asignado.
     */
    @PostMapping("/ruta/{idRuta}")
    public Quedada crearQuedada(@RequestBody Quedada quedada, @PathVariable Long idRuta) {
        return quedadaService.crearQuedada(quedada, idRuta);
    }

    /**
     * Añade un usuario a una quedada para que participe en ella.
     * * @param idQuedada El número de id de la quedada.
     * @param idUsuario El número de id del usuario que se va a apuntar.
     * @return La quedada actualizada con el usuario ya incluido.
     */
    @PostMapping("/{idQuedada}/usuarios/{idUsuario}")
    public Quedada aniadirUsuario(@PathVariable Long idQuedada, @PathVariable Long idUsuario) {
        return quedadaService.agregarUsuario(idQuedada, idUsuario).orElse(null);
    }

    /**
     * Asigna o cambia la ruta vinculada a una quedada.
     * * @param idQuedada El número de id de la quedada.
     * @param idRuta El número de id de la nueva ruta que se quiere poner.
     * @return La quedada actualizada con la nueva ruta.
     */
    @PostMapping("/{idQuedada}/ruta/{idRuta}")
    public Quedada asignarRutaQuedada(@PathVariable Long idQuedada, @PathVariable Long idRuta){
        return quedadaService.asignarRuta(idQuedada, idRuta).orElse(null);
    }

    /**
     * Actualiza los datos de una quedada que ya existe.
     * * @param id El número de id de la quedada que se va a modificar.
     * @param Quedada Los nuevos datos que queremos guardar en la quedada.
     * @return La quedada ya actualizada con los cambios.
     */
    @PutMapping("/{id}")
    public Quedada actualizarQuedada(@PathVariable Long id, @RequestBody Quedada Quedada) {
        return quedadaService.actualizarQuedada(id, Quedada);
    }

    /**
     * Elimina una quedada del sistema usando su número de id.
     * * @param id El número de id de la quedada que se va a borrar.
     */
    @DeleteMapping("/{id}")
    public void borrarQuedada(@PathVariable Long id) {
        quedadaService.borrarQuedada(id);
    }

    /**
     * Obtiene la lista de todos los usuarios apuntados a una quedada.
     * * @param id El número de id de la quedada.
     * @return Una lista con los usuarios que participan en esa quedada.
     */
    @GetMapping("/{id}/usuarios")
    public List<Usuario> getUsuariosByQuedadaId(@PathVariable Long id) {
        return quedadaService.obtenerQuedadaPorId(id).get().getUsuarios();
    }

    /**
     * Obtiene todos los comentarios que han escrito los usuarios en una quedada.
     * * @param id El número de id de la quedada.
     * @return Una lista con los comentarios de esa quedada.
     */
    @GetMapping("/{id}/comentarios")
    public List<Comentario> verComentariosUnaQuedada(@PathVariable Long id) {
        return quedadaService.obtenerQuedadaPorId(id).get().getComentarios();
    }

    /**
     * Quita o desapunta a un usuario de una quedada en específico.
     * * @param idQuedada El número de id de la quedada.
     * @param idUsuario El número de id del usuario que se quiere dar de baja.
     */
    @DeleteMapping("{idQuedada}/usuarios/{idUsuario}")
    public void eliminarUsuarioDeQuedada(@PathVariable Long idQuedada, @PathVariable Long idUsuario) {
        quedadaService.eliminarUsuarioDeQuedada(idQuedada, idUsuario);
    }
}