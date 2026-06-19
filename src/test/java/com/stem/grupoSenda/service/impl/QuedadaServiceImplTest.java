package com.stem.grupoSenda.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.stem.grupoSenda.model.Quedada;
import com.stem.grupoSenda.model.Ruta;
import com.stem.grupoSenda.model.Usuario;
import com.stem.grupoSenda.repository.QuedadaRepository;
import com.stem.grupoSenda.repository.RutaRepository;
import com.stem.grupoSenda.repository.UsuarioRepository;
import com.stem.grupoSenda.service.QuedadaService;

@SpringBootTest
@Transactional
public class QuedadaServiceImplTest {

    @Autowired
    private QuedadaService quedadaService;

    @Autowired
    private QuedadaRepository quedadaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RutaRepository rutaRepository;

    private Usuario usuarioTest;
    private Ruta rutaTest;

    @BeforeEach
    void setUp() {
        // Preparo un usuario y una ruta base para usar en los tests
        usuarioTest = new Usuario("Ana", "García", 4.0, "ana@test.com");
        usuarioTest = usuarioRepository.save(usuarioTest);

        rutaTest = new Ruta("Ruta Sur", "Ruta de nivel bajo", 100, "Fácil", 500, 5.0);
        rutaTest = rutaRepository.save(rutaTest);
    }

    @Test
    void testCrearQuedada() {
        // Preparo una nueva quedada
        Quedada nuevaQuedada = new Quedada(LocalDateTime.now().plusDays(2), "Parque Central", 5, null);

        // Llamo al servicio para crearla asociándola a la ruta creada
        Quedada resultado = quedadaService.crearQuedada(nuevaQuedada, rutaTest.getId());

        // Compruebo que se ha creado correctamente y tiene la ruta asignada
        assertNotNull(resultado);
        assertEquals(rutaTest.getId(), resultado.getRuta().getId());
    }

    @Test
    void testAgregarUsuarioExito() {
        // Preparo la quedada
        Quedada quedada = new Quedada(LocalDateTime.now().plusDays(2), "Plaza", 10, rutaTest);
        quedada = quedadaRepository.save(quedada);

        // Llamo al servicio para agregar al usuario
        Optional<Quedada> resultado = quedadaService.agregarUsuario(quedada.getId(), usuarioTest.getId());

        // Compruebo que el resultado está presente y el usuario se añadió
        assertTrue(resultado.isPresent());
        assertTrue(resultado.get().getUsuarios().contains(usuarioTest));
    }

    @Test
    void testEliminarUsuarioDeQuedada() {
        // Preparo quedada y añado usuario
        Quedada quedada = new Quedada(LocalDateTime.now().plusDays(2), "Plaza", 10, rutaTest);
        quedada = quedadaRepository.save(quedada);
        quedadaService.agregarUsuario(quedada.getId(), usuarioTest.getId());

        // Llamo al servicio para eliminarlo
        quedadaService.eliminarUsuarioDeQuedada(quedada.getId(), usuarioTest.getId());

        // Compruebo que el usuario ya no está en la lista de la quedada
        Quedada actualizada = quedadaRepository.findById(quedada.getId()).get();
        assertFalse(actualizada.getUsuarios().contains(usuarioTest));
    }

    @Test
    void testBorrarQuedada() {
        // Preparo una quedada
        Quedada quedada = new Quedada(LocalDateTime.now().plusDays(2), "Plaza", 10, rutaTest);
        quedada = quedadaRepository.save(quedada);
        Long idQuedada = quedada.getId();

        // Llamo al servicio para borrarla
        quedadaService.borrarQuedada(idQuedada);

        // Compruebo que ya no existe en la base de datos
        Optional<Quedada> borrada = quedadaRepository.findById(idQuedada);
        assertFalse(borrada.isPresent());
    }
    
}