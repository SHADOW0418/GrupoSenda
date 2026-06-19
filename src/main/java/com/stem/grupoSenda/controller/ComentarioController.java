package com.stem.grupoSenda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.stem.grupoSenda.model.Comentario;
import com.stem.grupoSenda.service.ComentarioService;

/**
 * Controlador que maneja todas las operaciones de la API para los comentarios.
 * Se encarga de recibir las peticiones web y comunicarse con el servicio
 * para listar, ver, crear, cambiar o borrar comentarios.
 * * @author Alex Fernandez Orta
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/comentarios")
public class ComentarioController {
    @Autowired
    private ComentarioService comentarioService;

    /**
     * Obtiene una lista con todos los comentarios guardados.
     * * @return Una lista que contiene todos los comentarios.
     */
    @GetMapping
    public List<Comentario> obtenerTodos(){
        return comentarioService.listarComentarios();
    }

    /**
     * Busca y devuelve un comentario utilizando su número de id.
     * * @param id El número identificador del comentario que queremos buscar.
     * @return El comentario encontrado.
     */
    @GetMapping("/{id}")
    public Comentario obtenerPorId(@PathVariable Long id){
        return comentarioService.obtenerComentarioPorId(id).orElseThrow();
    }

    /**
     * Guarda un nuevo comentario en el sistema.
     * * @param comentarioCrear Los datos del comentario que se va a crear.
     * @return El comentario ya guardado con su id asignado.
     */
    @PostMapping
    public Comentario crearComentario(@RequestBody Comentario comentarioCrear) {
        return comentarioService.crearComentario(comentarioCrear);
    }

    /**
     * Modifica los datos de un comentario que ya existe.
     * * @param id El número de id del comentario que queremos cambiar.
     * @param Comentario Los nuevos datos que le vamos a poner al comentario.
     * @return El comentario ya actualizado, o null si no se pudo hacer.
     */
    @PutMapping("/{id}")
    public Comentario actualizarComentario(@PathVariable Long id, @RequestBody Comentario Comentario) {
        return comentarioService.actualizarComentario(id, Comentario).orElse(null);
    }

    /**
     * Elimina un comentario del sistema usando su número de id.
     * * @param id El número de id del comentario que se quiere borrar.
     */
    @DeleteMapping("/{id}")
    public void borrarComentario(@PathVariable Long id) {
        comentarioService.borrarComentario(id);
    }
}