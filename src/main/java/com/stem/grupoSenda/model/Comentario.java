package com.stem.grupoSenda.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

/**
 * Entidad que representa un comentario en el sistema.
 * Esta clase se mapea con la tabla "comentario" en la base de datos y guarda
 * el texto, la puntuación, la fecha y las relaciones con el usuario y la quedada.
 * * @author Alex Fernandez Orta
 * @version 1.0
 */
@Entity
@Table(name = "comentario")
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String texto;

    @Column(nullable = false)
    private Double puntuacion;

    @Column(nullable = false)
    private LocalDateTime fechaPublicacion;

    // Relacion directa con usuario
    @ManyToOne
    @JsonIgnoreProperties("comentarios")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Relacion directa con quedada
    @ManyToOne
    @JsonIgnoreProperties({"comentarios", "usuarios"})
    @JoinColumn(name = "quedada_id")
    private Quedada quedada;

    /**
     * Constructor vacío obligatorio para que JPA pueda crear las instancias.
     */
    public Comentario() {
    }

    /**
     * Constructor con parámetros para crear un comentario con todos sus datos rellenos.
     * * @param texto El contenido o mensaje del comentario.
     * @param puntuacion La nota o valoración numérica dada por el usuario.
     * @param fechaPublicacion El día y la hora exacta en la que se escribe el comentario.
     * @param usuario El usuario que escribe este comentario.
     * @param quedada La quedada sobre la que se está opinando.
     */
    public Comentario(String texto, Double puntuacion, LocalDateTime fechaPublicacion, Usuario usuario,
            Quedada quedada) {
        this.texto = texto;
        this.puntuacion = puntuacion;
        this.fechaPublicacion = fechaPublicacion;
        this.usuario = usuario;
        this.quedada = quedada;
    }

    /**
     * Obtiene el usuario que escribió el comentario.
     * * @return El objeto Usuario correspondiente.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Asigna el usuario que ha escrito este comentario.
     * * @param usuario El usuario que se va a asociar.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene la quedada a la que pertenece este comentario.
     * * @return La quedada asociada.
     */
    public Quedada getQuedada() {
        return quedada;
    }

    /**
     * Vincula este comentario a una quedada concreta.
     * * @param quedada La quedada en la que se quiere poner el comentario.
     */
    public void setQuedada(Quedada quedada) {
        this.quedada = quedada;
    }

    /**
     * Obtiene el número de id del comentario.
     * * @return El id autonumérico.
     */
    public Long getId() {
        return id;
    }

    /**
     * Asigna el número de id al comentario.
     * * @param id El nuevo número de id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el texto escrito en el comentario.
     * * @return El contenido del mensaje.
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Cambia el texto del comentario por uno nuevo.
     * * @param texto El nuevo mensaje que se quiere guardar.
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * Obtiene la puntuación asignada en el comentario.
     * * @return La nota numérica.
     */
    public Double getPuntuacion() {
        return puntuacion;
    }

    /**
     * Modifica la puntuación del comentario.
     * * @param puntuacion La nueva valoración numérica.
     */
    public void setPuntuacion(Double puntuacion) {
        this.puntuacion = puntuacion;
    }

    /**
     * Obtiene el momento exacto en el que se publicó el comentario.
     * * @return La fecha y hora de publicación.
     */
    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * Guarda el momento de publicación del comentario.
     * * @param fechaPublicacion La fecha y hora a registrar.
     */
    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
}