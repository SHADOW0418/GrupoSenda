package com.stem.grupoSenda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.stem.grupoSenda.model.Comentario;
import com.stem.grupoSenda.model.Quedada;
import com.stem.grupoSenda.model.Usuario;
import com.stem.grupoSenda.service.UsuarioService;

/**
 * Controlador para gestionar todas las operaciones de los usuarios en la API.
 * Se encarga de recibir las peticiones web para listar, ver, crear, 
 * actualizar y borrar usuarios, además de consultar sus comentarios y quedadas.
 * * @author Alex Fernandez Orta
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Obtiene una lista con todos los usuarios registrados.
     * * @return Una lista que contiene a todos los usuarios.
     */
    @GetMapping
    public List<Usuario> obtenerTodos(){
        return usuarioService.listarUsuarios();
    }

    /**
     * Busca y devuelve un usuario específico utilizando su número de id.
     * * @param id El número identificador del usuario que queremos buscar.
     * @return El usuario encontrado, o null si no existe.
     */
    @GetMapping("/{id}")
    public Usuario obtenerPorId(@PathVariable Long id){
        return usuarioService.obtenerUsuarioPorId(id).orElse(null);
    }

    /**
     * Guarda un nuevo usuario en el sistema.
     * * @param usuario Los datos del usuario que se va a crear.
     * @return El usuario ya guardado con su id asignado.
     */
    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioService.crearUsuario(usuario);
    }

    /**
     * Modifica los datos de un usuario que ya existe.
     * * @param id El número de id del usuario que queremos cambiar.
     * @param usuario Los nuevos datos que le vamos a poner al usuario.
     * @return El usuario ya actualizado con los cambios, o null si no se encontró.
     */
    @PutMapping("/{id}")
    public Usuario actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.actualizarUsuario(id, usuario).orElse(null);
    }

    /**
     * Elimina un usuario del sistema usando su número de id.
     * * @param id El número de id del usuario que se quiere borrar.
     */
    @DeleteMapping("/{id}")
    public void borrarUsuario(@PathVariable Long id) {
        usuarioService.borrarUsuario(id);
    }

    /**
     * Obtiene la lista de todos los comentarios que ha escrito un usuario concreto.
     * * @param id El número de id del usuario.
     * @return Una lista con los comentarios escritos por este usuario.
     */
    @GetMapping("/{id}/comentarios")
    public List<Comentario> verComentariosUsuario(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id).get().getComentarios();
    }

    /**
     * Obtiene la lista de todas las quedadas a las que está apuntado un usuario.
     * * @param id El número de id del usuario.
     * @return Una lista con las quedadas en las que participa este usuario.
     */
    @GetMapping("/{id}/quedadas")
    public List<Quedada> verQuedadasUsuario(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id).get().getQuedadas();
    }
}