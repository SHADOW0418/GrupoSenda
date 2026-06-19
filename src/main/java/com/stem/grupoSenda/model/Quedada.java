package com.stem.grupoSenda.model;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

/**
 * Entidad que representa una quedada o evento en el sistema.
 * Esta clase se conecta con la tabla "quedada" en la base de datos y guarda
 * información sobre el momento del encuentro, el lugar, el límite de personas y 
 * las listas de usuarios, comentarios y la ruta que tiene asignada.
 *
 * @author Alex Fernandez Orta
 * @version 1.0
 */
@Entity
@Table(name = "quedada")
public class Quedada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDateTime fechaEncuentro;
    
    @Column(nullable = false)
    private String puntoEncuentro;
    
    @Column(nullable = false)
    private Integer maximosAsistentes;

    //Relacion n:m con usuario
    @ManyToMany(mappedBy = "quedadas")
    private List<Usuario> usuarios;

    //Relacion directa con comentarios
    // @JsonIgnore
    @OneToMany(mappedBy = "quedada", cascade = CascadeType.ALL)
    private List<Comentario> comentarios;

    //Relacion directa con ruta
    @ManyToOne
    @JsonIgnoreProperties("quedadas")
    @JoinColumn(name = "ruta_id")
    private Ruta ruta;

    /**
     * Constructor vacío obligatorio para que el sistema de persistencia (JPA) 
     * pueda crear las instancias correctamente.
     */
    public Quedada() {
    }

    /**
     * Constructor con parámetros para crear una quedada con sus datos principales.
     * <p>Al crearla, las listas de usuarios apuntados y comentarios se inician vacías.</p>
     *
     * @param fechaEncuentro El día y la hora en la que se han citado los usuarios.
     * @param puntoEncuentro El sitio exacto donde se va a quedar.
     * @param maximosAsistentes El número máximo de personas que pueden apuntarse.
     * @param ruta La ruta de senderismo elegida para la quedada.
     */
    public Quedada(LocalDateTime fechaEncuentro, String puntoEncuentro, Integer maximosAsistentes, Ruta ruta) {
        this.fechaEncuentro = fechaEncuentro;
        this.puntoEncuentro = puntoEncuentro;
        this.maximosAsistentes = maximosAsistentes;
        this.usuarios =   new ArrayList<>();
        this.comentarios =  new ArrayList<>();
        this.ruta = ruta;
    }

    /**
     * Obtiene la lista de todos los usuarios que se han apuntado a esta quedada.
     *
     * @return Una lista con los usuarios participantes.
     */
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * Asigna o cambia el grupo de usuarios de la quedada.
     *
     * @param usuarios La nueva lista de usuarios participantes.
     */
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * Obtiene todos los comentarios escritos por los usuarios sobre esta quedada.
     *
     * @return Una lista con los comentarios de la quedada.
     */
    public List<Comentario> getComentarios() {
        return comentarios;
    }

    /**
     * Asigna la lista de comentarios de la quedada.
     *
     * @param comentarios Los nuevos comentarios a guardar.
     */
    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    /**
     * Obtiene la ruta de senderismo asignada a esta quedada.
     *
     * @return La ruta asociada.
     */
    public Ruta getRuta() {
        return ruta;
    }

    /**
     * Vincula una ruta de senderismo específica a esta quedada.
     *
     * @param ruta La ruta que se va a recorrer.
     */
    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    /**
     * Obtiene el número de id único de la quedada.
     *
     * @return El número identificador.
     */
    public Long getId() {
        return id;
    }

    /**
     * Asigna el número de id a la quedada.
     *
     * @param id El nuevo id de la quedada.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el momento de la cita (fecha y hora).
     *
     * @return La fecha y hora programadas.
     */
    public LocalDateTime getFechaEncuentro() {
        return fechaEncuentro;
    }

    /**
     * Cambia el día o la hora del encuentro.
     *
     * @param fechaEncuentro El nuevo momento de la cita.
     */
    public void setFechaEncuentro(LocalDateTime fechaEncuentro) {
        this.fechaEncuentro = fechaEncuentro;
    }

    /**
     * Obtiene el punto o lugar exacto de encuentro.
     *
     * @return El texto que describe el sitio donde se va a quedar.
     */
    public String getPuntoEncuentro() {
        return puntoEncuentro;
    }

    /**
     * Cambia el lugar donde se va a quedar.
     *
     * @param puntoEncuentro La dirección o descripción del nuevo punto de cita.
     */
    public void setPuntoEncuentro(String puntoEncuentro) {
        this.puntoEncuentro = puntoEncuentro;
    }

    /**
     * Obtiene la cantidad máxima de personas que pueden ir a la quedada.
     *
     * @return El límite de asistentes permitidos.
     */
    public Integer getMaximosAsistentes() {
        return maximosAsistentes;
    }

    /**
     * Modifica el límite de asistentes permitidos para la quedada.
     *
     * @param maximosAsistentes El nuevo número máximo de plazas.
     */
    public void setMaximosAsistentes(Integer maximosAsistentes) {
        this.maximosAsistentes = maximosAsistentes;
    }
}