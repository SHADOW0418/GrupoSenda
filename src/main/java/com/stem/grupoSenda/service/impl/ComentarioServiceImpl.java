package com.stem.grupoSenda.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import com.stem.grupoSenda.model.Comentario;
import com.stem.grupoSenda.model.Quedada;
import com.stem.grupoSenda.model.Usuario;
import com.stem.grupoSenda.repository.ComentarioRepository;
import com.stem.grupoSenda.service.ComentarioService;
import com.stem.grupoSenda.service.QuedadaService;
import com.stem.grupoSenda.service.UsuarioService;

/**
 * Implementación del servicio de comentarios.
 * Aquí se gestiona la lógica de negocio para los comentarios, como las 
 * comprobaciones de seguridad para evitar que un usuario comente dos veces.
 * * @author Alex Fernandez Orta
 * @version 1.0
 */
@Service
public class ComentarioServiceImpl implements ComentarioService {
    @Autowired
    private ComentarioRepository comentarioRepository;
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private QuedadaService quedadaService;

    /**
     * Devuelve una lista con todos los comentarios que existen en la base de datos.
     * * @return Una lista con todos los comentarios encontrados.
     */
    @Override
    public List<Comentario> listarComentarios() {
        // Aquí podrías aplicar lógica extra si lo necesitas
        return comentarioRepository.findAll();
    }

    /**
     * Busca un comentario específico utilizando su número de id.
     * * @param id El número identificador del comentario que queremos buscar.
     * @return Un Optional que contiene el comentario si existe, o vacío si no se encuentra.
     */
    @Override
    public Optional<Comentario> obtenerComentarioPorId(Long id) {
        return comentarioRepository.findById(id);
    }

    /**
     * Crea un nuevo comentario en una quedada si el usuario existe y no ha comentado antes.
     * El método busca al usuario y la quedada; si los encuentra, revisa la lista de 
     * comentarios para asegurarse de que ese usuario no tenga ya un comentario allí.
     * * @param comentario Los datos del comentario que se quiere añadir.
     * @return El comentario guardado con sus relaciones listas, o null si el usuario ya había comentado o no existía.
     */
    @Override
    public Comentario crearComentario(Comentario comentario){
        Optional<Usuario> usuario = usuarioService.obtenerUsuarioPorId(comentario.getUsuario().getId());
        Optional<Quedada> quedada = quedadaService.obtenerQuedadaPorId(comentario.getQuedada().getId());
        Comentario comentarioActualizado = null;
        if (usuario.isPresent() && quedada.isPresent()) {
            
            Usuario usuarioExiste = usuario.get();
            Quedada quedadaExiste = quedada.get();
            Boolean existeUsuario = false;
            for (Comentario comentarioQuedada : quedadaExiste.getComentarios()) {
                if (comentarioQuedada.getUsuario().getId().equals(usuarioExiste.getId())) {
                    existeUsuario = true;
                }
                
            }
            if (!existeUsuario) {
                comentario.setUsuario(usuarioExiste);
                comentario.setQuedada(quedadaExiste);
                comentarioActualizado = comentarioRepository.save(comentario);
                
            }
        }
        return comentarioActualizado;
    }

    /**
     * Actualiza un comentario existente modificando solo los campos que no vengan vacíos.
     * Permite cambiar el texto, la puntuación o la fecha sin necesidad de tener que 
     * volver a enviar todos los datos obligatoriamente.
     * * @param id El número identificador del comentario que queremos cambiar.
     * @param nuevoComentario Objeto que contiene los nuevos datos a aplicar.
     * @return Un Optional que contiene el comentario ya actualizado.
     */
    @Override
    public Optional<Comentario> actualizarComentario(Long id, Comentario nuevoComentario) {
        Optional<Comentario> result = obtenerComentarioPorId(id);
        Comentario comentarioActualizado = null;

        if (result.isPresent()) {
            Comentario comentarioExistente = result.get();
            nuevoComentario.setId(id);

            if (nuevoComentario.getTexto() != null) {
                comentarioExistente.setTexto(nuevoComentario.getTexto());
            }
            if (nuevoComentario.getPuntuacion() != null) {
                comentarioExistente.setPuntuacion(nuevoComentario.getPuntuacion());
            }
            if (nuevoComentario.getFechaPublicacion() != null) {
                comentarioExistente.setFechaPublicacion(nuevoComentario.getFechaPublicacion());
            }

            comentarioActualizado = comentarioRepository.save(comentarioExistente);
        }
        return Optional.of(comentarioActualizado);
        
    }

    /**
     * Elimina un comentario de la base de datos usando su número de id.
     * * @param id El número de id del comentario que se quiere borrar.
     */
    @Override
    public void borrarComentario(Long id) {
        comentarioRepository.deleteById(id);
    }
}