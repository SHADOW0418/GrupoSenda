package com.stem.grupoSenda.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stem.grupoSenda.model.Comentario;
import com.stem.grupoSenda.model.Quedada;
import com.stem.grupoSenda.model.Ruta;
import com.stem.grupoSenda.model.Usuario;
import com.stem.grupoSenda.repository.ComentarioRepository;
import com.stem.grupoSenda.repository.QuedadaRepository;
import com.stem.grupoSenda.repository.UsuarioRepository;
import com.stem.grupoSenda.service.QuedadaService;
import com.stem.grupoSenda.service.RutaService;
import com.stem.grupoSenda.service.UsuarioService;

/**
 * Implementación del servicio de quedadas.
 * Aquí se maneja toda la lógica del negocio relacionada con las quedadas,
 * como crearlas vinculándolas a una ruta o modificar sus datos de forma
 * parcial.
 * * @author Alex Fernandez Orta
 * 
 * @version 1.0
 */
@Service
public class QuedadaServiceImpl implements QuedadaService {
    @Autowired
    private QuedadaRepository quedadaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RutaService rutaService;

    @Autowired
    private ComentarioRepository comentarioRepository;

    /**
     * Busca y devuelve una lista con todas las quedadas que hay guardadas.
     * * @return Una lista que contiene todas las quedadas encontradas.
     */
    @Override
    public List<Quedada> listarQuedadas() {
        // Aquí podrías aplicar lógica extra si lo necesitas
        return quedadaRepository.findAll();
    }

    /**
     * Busca una quedada concreta utilizando su número de id.
     * * @param id El número identificador de la quedada que queremos encontrar.
     * 
     * @return Un Optional que contiene la quedada si existe, o vacío si no se
     *         encuentra.
     */
    @Override
    public Optional<Quedada> obtenerQuedadaPorId(Long id) {
        return quedadaRepository.findById(id);
    }

    /**
     * Crea una nueva quedada en el sistema y la asigna a una ruta existente.
     * El método comprueba primero si la ruta existe usando su id. Si la encuentra,
     * conecta la quedada con esa ruta y la guarda en la base de datos.
     * * @param quedada Los datos de la quedada que se quiere dar de alta.
     * 
     * @param idRuta El número de id de la ruta que le queremos asignar.
     * @return La quedada guardada con la ruta vinculada, o null si la ruta no
     *         existía.
     */
    @Override
    public Quedada crearQuedada(Quedada quedada, Long idRuta) {
        Optional<Ruta> rutaExiste = rutaService.obtenerRutaPorId(idRuta);
        Quedada quedadaActualizada = null;
        if (rutaExiste.isPresent()) {
            Ruta ruta = rutaExiste.get();
            quedada.setRuta(ruta);
            quedadaActualizada = quedadaRepository.save(quedada);
        }
        return quedadaActualizada;
    }

    /**
     * Actualiza una quedada modificando solamente los campos que se hayan enviado
     * con datos.
     * Permite cambiar la fecha, el punto de encuentro o el límite de asistentes de
     * forma
     * independiente sin necesidad de volver a mandar toda la información de la
     * quedada.
     * * @param id El número identificador de la quedada que queremos modificar.
     * 
     * @param nuevaQuedada Objeto que trae los nuevos cambios que se van a aplicar.
     * @return La quedada ya actualizada y guardada con los nuevos cambios.
     */
    @Override
    public Quedada actualizarQuedada(Long id, Quedada nuevaQuedada) {
        Optional<Quedada> result = obtenerQuedadaPorId(id);
        Quedada quedadaActualizada = null;
        if (result.isPresent()) {
            Quedada quedadaExistente = result.get();
            nuevaQuedada.setId(id);

            if (nuevaQuedada.getFechaEncuentro() != null) {
                quedadaExistente.setFechaEncuentro(nuevaQuedada.getFechaEncuentro());
            }
            if (nuevaQuedada.getPuntoEncuentro() != null) {
                quedadaExistente.setPuntoEncuentro(nuevaQuedada.getPuntoEncuentro());
            }
            if (nuevaQuedada.getMaximosAsistentes() != null) {
                quedadaExistente.setMaximosAsistentes(nuevaQuedada.getMaximosAsistentes());
            }

            quedadaActualizada = quedadaRepository.save(quedadaExistente);
        }
        return quedadaActualizada;
    }

    /**
     * Metodo para borrar una quedada con un id en especifico.
     * 1.Compruebo que la quedada existe
     * 2.Obtengo los usuarios de esa quedada
     * 3.Itero sobre esos usuarios y saco sus quedadas
     * 4.Itero sobre las quedadas de ese usuario y si el id de esa quedada es igual
     * que es id de la quedada a borrar
     * le elimino esa quedada, guardo la informacion del usuario
     * 5.Cuando se hayan eliminado todos los usuarios elimino la quedada por su id
     */
    @Override
    public void borrarQuedada(Long id) {
        Optional<Quedada> quedada = obtenerQuedadaPorId(id);
        if (quedada.isPresent()) {
            Quedada quedadaObtenida = quedada.get();
            List<Usuario> usuariosEnQuedada = quedadaObtenida.getUsuarios();

            for (Usuario usuario : usuariosEnQuedada) {
                List<Quedada> quedadasUsuario = usuario.getQuedadas();

                for (int i = 0; i < quedadasUsuario.size(); i++) {
                    Quedada quedadaActual = quedadasUsuario.get(i);
                    if (quedadaActual.getId().equals(id)) {
                        quedadasUsuario.remove(i);
                        usuarioRepository.save(usuario);
                    }
                }
            }
            quedadaRepository.deleteById(id);
        }
    }

    /**
     * Metodo para agregar un usuario a una quedada
     * 1.Compruebo que la quedada y el usuario existen
     * 2.Compruebo que el numero maximo de asistentes sea mayor al numero total de
     * usuarios
     * 3.Compruebo que el usuario no este ya en la quedada comparandolo por su id
     * 4.Compruebo que el usuario no este ya incrito en una quedada ese mismo dia
     * 5.Si no existe el usuario lo añado a la quedada
     * 
     */
    @Override
    public Optional<Quedada> agregarUsuario(Long id, Long idUsuario) {
        Optional<Quedada> quedadaExistente = obtenerQuedadaPorId(id);
        Quedada quedadaActualizada = null;
        // Llamo al optional de usuario
        Optional<Usuario> usuarioExistente = usuarioService.obtenerUsuarioPorId(idUsuario);
        // Compruebo que existe el usuario
        if (usuarioExistente.isPresent() && quedadaExistente.isPresent()) {
            // Saco al usuario de la caja
            Usuario usuarioExiste = usuarioExistente.get();
            Quedada quedadaExiste = quedadaExistente.get();
            Boolean usuarioExisteEnQuedada = false;
            // Compruebo que este entre los parametros de maximos asistentes
            if (quedadaExiste.getMaximosAsistentes() > quedadaExiste.getUsuarios().size()) {
                // Comprobar que el usuario no existe en la quedada
                for (int i = 0; i < quedadaExiste.getUsuarios().size() && !usuarioExisteEnQuedada; i++) {
                    if (quedadaExiste.getUsuarios().get(i).getId().equals(idUsuario)) {
                        usuarioExisteEnQuedada = true;
                    }
                }
                // Comprobar que ese usuario no este ya incrito en otra quedada el mismo dia
                Boolean tieneQuedadaEseDia = false;
                LocalDateTime fechaCompleta = quedadaExiste.getFechaEncuentro();
                LocalDate soloElDiaActual = fechaCompleta.toLocalDate();
                for (int i = 0; i < usuarioExiste.getQuedadas().size() && !tieneQuedadaEseDia; i++) {
                    LocalDate diaQuedadaExistente = usuarioExiste.getQuedadas().get(i).getFechaEncuentro()
                            .toLocalDate();
                    if (diaQuedadaExistente.equals(soloElDiaActual)) {
                        tieneQuedadaEseDia = true;
                    }

                }

                if (!usuarioExisteEnQuedada && !tieneQuedadaEseDia) {
                    // Añado al array de usuarios que esta en quedada el usuario y vicebersa
                    quedadaExiste.getUsuarios().add(usuarioExiste);
                    usuarioExiste.getQuedadas().add(quedadaExiste);
                    usuarioRepository.save(usuarioExiste);
                    quedadaActualizada = quedadaRepository.save(quedadaExiste);
                }
            }
        }

        return Optional.of(quedadaActualizada);
    }

    /**
     * Asignar una ruta a una quedada
     * Compruebo que ambos campos existen
     * Si no hay una ruta ya en el campo de quedada se la añado
     */
    @Override
    public Optional<Quedada> asignarRuta(Long idQuedada, Long idRuta) {
        Optional<Quedada> quedada = obtenerQuedadaPorId(idQuedada);
        Optional<Ruta> ruta = rutaService.obtenerRutaPorId(idRuta);
        Quedada quedadaActualizada = null;
        if (quedada.isPresent() && ruta.isPresent()) {
            Quedada quedadaExiste = quedada.get();
            if (quedadaExiste.getRuta() == null) {

                Ruta rutaExiste = ruta.get();
                quedadaExiste.setRuta(rutaExiste);
                quedadaActualizada = quedadaRepository.save(quedadaExiste);
            }
        }
        return Optional.of(quedadaActualizada);
    }

    /**
     * Elimina un usuario de una quedada especifica y gestiona la desvinculacion, ademas de borrar
     * los comentarios asociados a esa relacion
     * 
     * @param idQuedada el id unico de la quedada de la que se va a eliminar el usuario
     * @param udUsuario El id unico deo usuario que sera eliminado de la quedada
     */
    @Override
    public void eliminarUsuarioDeQuedada(Long idQuedada, Long idUsuario) {
        Optional<Quedada> quedadaBuscar = obtenerQuedadaPorId(idQuedada);

        if (quedadaBuscar.isPresent()) {
            Quedada quedadaExiste = quedadaBuscar.get();

            Usuario usuarioAEliminar = null;
            boolean usuarioEncontrado = false;

            // busco el usuario exacto en la memoria
            for (int i = 0; i < quedadaExiste.getUsuarios().size() && !usuarioEncontrado; i++) {
                Usuario usuarioQuedada = quedadaExiste.getUsuarios().get(i);
                if (usuarioQuedada.getId().equals(idUsuario)) {
                    usuarioAEliminar = usuarioQuedada;
                    usuarioEncontrado = true;
                }
            }

            // Si esta buscamos la quedada exacta dentro de ese usuario
            if (usuarioEncontrado) {

                Quedada quedadaAEliminar = null;
                boolean quedadaEncontrada = false;

                // Buscamos la quedada exacta en la lista del usuario
                for (int j = 0; j < usuarioAEliminar.getQuedadas().size() && !quedadaEncontrada; j++) {
                    Quedada quedada = usuarioAEliminar.getQuedadas().get(j);
                    if (quedada.getId().equals(idQuedada)) {
                        quedadaAEliminar = quedada;
                        quedadaEncontrada = true;
                    }
                }

                // borramos en ambos lados
                if (quedadaEncontrada) {
                    usuarioAEliminar.getQuedadas().remove(quedadaAEliminar);
                }
                quedadaExiste.getUsuarios().remove(usuarioAEliminar);

                Optional<Comentario> comentario = comentarioRepository.findByUsuarioIdAndQuedadaId(idUsuario,
                        idQuedada);
                if (comentario.isPresent()) {
                    comentarioRepository.delete(comentario.get());
                }
                // guardamos en cada uno
                usuarioRepository.save(usuarioAEliminar);
                quedadaRepository.save(quedadaExiste);
            }
        }
    }
}
