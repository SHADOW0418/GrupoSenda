package com.stem.grupoSenda.service;

import java.util.List;
import java.util.Optional;

import com.stem.grupoSenda.model.Usuario;

public interface UsuarioService {
    List<Usuario>listarUsuarios();
    Optional<Usuario> obtenerUsuarioPorId(Long id);
    Usuario crearUsuario(Usuario usuario);
    Optional<Usuario> actualizarUsuario(Long id, Usuario usuario);
    void borrarUsuario(Long id);
}
