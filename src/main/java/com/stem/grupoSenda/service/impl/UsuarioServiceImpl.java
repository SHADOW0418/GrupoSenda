package com.stem.grupoSenda.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stem.grupoSenda.model.Usuario;
import com.stem.grupoSenda.repository.UsuarioRepository;
import com.stem.grupoSenda.service.UsuarioService;

/**
 * Implementación del servicio de usuarios.
 * Esta clase se encarga de gestionar toda la lógica de negocio de los usuarios,
 * como listar a los excursionistas, buscarlos, borrarlos, editarlos parcialmente 
 * o crearlos asegurando que no se repitan los correos electrónicos.
 * * @author Alex Fernandez Orta
 * @version 1.0
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Busca y devuelve una lista con todos los usuarios registrados en el sistema.
     * * @return Una lista que contiene a todos los usuarios.
     */
    @Override
    public List<Usuario> listarUsuarios() {
        // Aquí podrías aplicar lógica extra si lo necesitas
        return usuarioRepository.findAll();
    }

    /**
     * Busca un usuario específico en el sistema utilizando su número de id.
     * * @param id El número identificador del usuario que queremos encontrar.
     * @return Un Optional que contiene al usuario si se encuentra, o vacío si no existe.
     */
    @Override
    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        // Maneja el caso si no encuentra al usuario
        return usuarioRepository.findById(id);
    }

    /**
     * Registra un nuevo usuario en el sistema comprobando que su email sea único.
     * El método recorre la lista de usuarios existentes y compara los correos. 
     * Si el email ya está registrado, no creará el usuario para evitar duplicados.
     * * @param usuario Los datos del nuevo usuario que se quiere dar de alta.
     * @return El usuario que se ha guardado en la base de datos, o null si el email ya existía.
     */
    @Override
    public Usuario crearUsuario(Usuario usuario){
        Usuario usuarioCreado = null;
        boolean emailExistente = false;
        for (int i = 0; i < listarUsuarios().size() && emailExistente == false; i++) {
            if (listarUsuarios().get(i).getEmail().equalsIgnoreCase(usuario.getEmail())) {
                emailExistente = true;
            }
        }
        if (!emailExistente) {
            usuarioCreado = usuario;
        }
        return usuarioRepository.save(usuarioCreado);
    }

    /**
     * Actualiza los datos de un usuario modificando solo los campos que traigan información.
     * Permite modificar de forma independiente el nombre, los apellidos, el nivel de 
     * experiencia o el email, sin obligar a cambiar todo el perfil entero.
     * * @param id El número identificador del usuario que se va a actualizar.
     * @param nuevoUsuario Objeto que contiene las modificaciones que se van a aplicar.
     * @return Un Optional que contiene al usuario ya actualizado con los nuevos cambios.
     */
    @Override
    public Optional<Usuario> actualizarUsuario(Long id, Usuario nuevoUsuario) {
        Optional<Usuario> result = obtenerUsuarioPorId(id);
        Usuario usuarioActualizado = null;
        if (result.isPresent()) {
            //Saco el usuario de la caja
            Usuario usuarioExistente = result.get();

            if (nuevoUsuario.getNombre() != null) {
                usuarioExistente.setNombre(nuevoUsuario.getNombre());
            }
            if (nuevoUsuario.getApellido() != null) {
                usuarioExistente.setApellido(nuevoUsuario.getApellido());
            }
            if (nuevoUsuario.getNivelExperiencia() != null) {
                usuarioExistente.setNivelExperiencia(nuevoUsuario.getNivelExperiencia());
            }
            if (nuevoUsuario.getEmail() != null) {
                usuarioExistente.setEmail(nuevoUsuario.getEmail());
            }
            usuarioActualizado = usuarioRepository.save(usuarioExistente);
            
        }
        return Optional.of(usuarioActualizado);
        
    }

    /**
     * Elimina a un usuario del sistema utilizando su número de id.
     * * @param id El número de id del usuario que se quiere dar de baja.
     */
    @Override
    public void borrarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}