package com.stem.grupoSenda.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.stem.grupoSenda.model.Comentario;
import com.stem.grupoSenda.model.Quedada;
import com.stem.grupoSenda.model.Ruta;
import com.stem.grupoSenda.model.Usuario;
import com.stem.grupoSenda.repository.ComentarioRepository;
import com.stem.grupoSenda.repository.QuedadaRepository;
import com.stem.grupoSenda.repository.RutaRepository;
import com.stem.grupoSenda.repository.UsuarioRepository;
import com.stem.grupoSenda.service.ComentarioService;

@SpringBootTest
@Transactional // Revierte los cambios en la BD después de cada test para que sean independientes
public class ComentarioServiceImplTest {

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private QuedadaRepository quedadaRepository;

    @Autowired
    private RutaRepository rutaRepository;

    private Usuario usuarioTest;
    private Quedada quedadaTest;
    private Ruta rutaTest;

    @BeforeEach
    void setUp() {
        // Creo un usuario
        usuarioTest = new Usuario("Juan", "Perez", 3.5, "juan@test.com");
        usuarioTest = usuarioRepository.save(usuarioTest);

        // 2Creo una ruta
        rutaTest = new Ruta("Ruta Montaña", "Bonita ruta", 500, "Media", 1200, 15.0);
        rutaTest = rutaRepository.save(rutaTest);

        // Creo una quedada
        quedadaTest = new Quedada(LocalDateTime.now().plusDays(5), "Plaza Mayor", 10, rutaTest);
        quedadaTest = quedadaRepository.save(quedadaTest);
    }

    @Test
    void testCrearComentarioDevuelveTrueFunciona() {
        //Creo un comentario
        Comentario nuevoComentario = new Comentario();
        nuevoComentario.setTexto("bdbtfgbf");
        nuevoComentario.setPuntuacion(5.0);
        nuevoComentario.setFechaPublicacion(LocalDateTime.now());
        nuevoComentario.setUsuario(usuarioTest);
        nuevoComentario.setQuedada(quedadaTest);

        // Llamo a crear comentario
        Comentario resultado = comentarioService.crearComentario(nuevoComentario);

        // Compruebo que el texto sea el mismo que envie
        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertEquals("bdbtfgbf", resultado.getTexto());
        
        // Verificar que el comentario se ha guardado en la base de datos
        Optional<Comentario> guardado = comentarioRepository.findById(resultado.getId());
        assertTrue(guardado.isPresent());
    }

    @Test
void testCrearComentarioDevuelveNullNoFunciona() {
   // Preparo el primer comentario y lo guardo
    Comentario comentario1 = new Comentario("Primer comentario", 4.0, LocalDateTime.now(), usuarioTest, quedadaTest);
    comentarioService.crearComentario(comentario1);
    
    // Manualmente añado el comentario a la lista de la quedada de prueba
    quedadaTest.getComentarios().add(comentario1);

    // Intento crear un segundo comentario con el mismo usuario en la misma quedada
    Comentario comentario2 = new Comentario("Segundo comentario", 3.0, LocalDateTime.now(), usuarioTest, quedadaTest);

    // Llamo al servicio para crear el segundo comentario
    Comentario resultado = comentarioService.crearComentario(comentario2);

    // Compruebo que el resultado es nulo
    assertNull(resultado);
}

@Test
    void testListarComentarios() {
        // Preparo dos comentarios y los guardo en la base de datos
        Comentario comentario1 = new Comentario("Coment 1", 4.0, LocalDateTime.now(), usuarioTest, quedadaTest);
        Comentario comentario2 = new Comentario("Coment 2", 5.0, LocalDateTime.now(), usuarioTest, quedadaTest);
        comentarioRepository.save(comentario1);
        comentarioRepository.save(comentario2);

        // Llamo al servicio para obtener la lista de comentarios
        List<Comentario> comentarios = comentarioService.listarComentarios();

        // Compruebo que la lista contenga al menos los dos comentarios guardados
        assertTrue(comentarios.size() >= 2);
    }

    @Test
    void testObtenerComentarioPorId() {
        // Preparo un comentario y lo guardo para obtener su id
        Comentario comentario = new Comentario("Test ID", 4.0, LocalDateTime.now(), usuarioTest, quedadaTest);
        Comentario guardado = comentarioRepository.save(comentario);

        // Llamo al servicio para recuperar el comentario por su id
        Optional<Comentario> resultado = comentarioService.obtenerComentarioPorId(guardado.getId());

        // Compruebo que el comentario existe y que el id coincide
        assertTrue(resultado.isPresent());
        assertEquals(guardado.getId(), resultado.get().getId());
    }

    @Test
    void testActualizarComentario() {
        // Preparo un comentario inicial y lo guardo
        Comentario comentarioOriginal = new Comentario("Malo", 1.0, LocalDateTime.now(), usuarioTest, quedadaTest);
        comentarioOriginal = comentarioRepository.save(comentarioOriginal);

        // Creo un objeto con los nuevos datos que quiero actualizar
        Comentario datosNuevos = new Comentario();
        datosNuevos.setTexto("Bueno");
        datosNuevos.setPuntuacion(5.0);

        // Llamo al servicio para realizar la actualización
        Optional<Comentario> resultado = comentarioService.actualizarComentario(comentarioOriginal.getId(), datosNuevos);

        // Compruebo que la actualización se realizó correctamente
        assertTrue(resultado.isPresent());
        assertEquals("Bueno", resultado.get().getTexto());
        assertEquals(5.0, resultado.get().getPuntuacion());
        
        // Verifico que las relaciones de usuario y quedada se mantienen intactas
        assertNotNull(resultado.get().getUsuario());
    }

    @Test
    void testBorrarComentario() {
        // Preparo un comentario y lo guardo para obtener su ID
        Comentario comentario = new Comentario("Para borrar", 4.0, LocalDateTime.now(), usuarioTest, quedadaTest);
        Comentario guardado = comentarioRepository.save(comentario);
        Long idBorrar = guardado.getId();

        // Llamo al servicio para borrar el comentario
        comentarioService.borrarComentario(idBorrar);

        // Compruebo que el comentario ya no existe en la base de datos
        Optional<Comentario> busqueda = comentarioRepository.findById(idBorrar);
        assertFalse(busqueda.isPresent());
    }
}