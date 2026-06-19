package com.stem.grupoSenda.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.stem.grupoSenda.model.Usuario;
import com.stem.grupoSenda.repository.UsuarioRepository;
import com.stem.grupoSenda.service.UsuarioService;

@SpringBootTest
@Transactional
public class UsuarioServiceImplTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void testCrearUsuarioExito() {
        // Preparo un usuario nuevo
        Usuario nuevoUsuario = new Usuario("Alex", "Gomez", 3.0, "alex@test.com");

        // Llamo al servicio
        Usuario resultado = usuarioService.crearUsuario(nuevoUsuario);

        // Compruebo
        assertNotNull(resultado);
        assertEquals("alex@test.com", resultado.getEmail());
    }

    @Test
    void testCrearUsuarioFallaEmailDuplicado() {
        // Preparo un usuario inicial y lo guardo
        usuarioRepository.save(new Usuario("Existente", "User", 1.0, "dup@test.com"));

        // Intento crear otro usuario con el mismo email
        Usuario usuarioDuplicado = new Usuario("Nuevo", "Usuario", 2.0, "dup@test.com");

        // En tu implementación actual, esto debería lanzar excepción o fallar al guardar null.
        // Si el usuario no se crea, el resultado será nulo.
        assertThrows(Exception.class, () -> {
            usuarioService.crearUsuario(usuarioDuplicado);
        });
    }

@Test
    void testListarUsuarios() {
        // Preparo datos
        usuarioRepository.save(new Usuario("U1", "A1", 1.0, "u1@test.com"));
        usuarioRepository.save(new Usuario("U2", "A2", 2.0, "u2@test.com"));

        // Act
        List<Usuario> usuarios = usuarioService.listarUsuarios();

        // Assert
        assertTrue(usuarios.size() >= 2);
    }

    @Test
    void testActualizarUsuario() {
        // Preparo datos
        Usuario original = usuarioRepository.save(new Usuario("Antiguo", "Apellido", 1.0, "a@test.com"));
        
        Usuario cambios = new Usuario();
        cambios.setNombre("NuevoNombre");

        // Act
        Optional<Usuario> resultado = usuarioService.actualizarUsuario(original.getId(), cambios);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("NuevoNombre", resultado.get().getNombre());
    }

    @Test
    void testBorrarUsuario() {
        // Preparo datos
        Usuario usuario = usuarioRepository.save(new Usuario("Borrar", "Me", 1.0, "borrar@test.com"));
        Long id = usuario.getId();

        // Act
        usuarioService.borrarUsuario(id);

        // Assert
        assertFalse(usuarioRepository.findById(id).isPresent());
    }
}