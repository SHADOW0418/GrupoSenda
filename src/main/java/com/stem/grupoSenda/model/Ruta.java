package com.stem.grupoSenda.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

/**
 * Entidad que representa una ruta de senderismo en el sistema.
 * Esta clase se conecta con la tabla "ruta" en la base de datos y almacena
 * todos sus detalles técnicos como el nombre, descripción, desnivel, dificultad, 
 * altitud y la distancia, además de las quedadas asociadas a ella.
 *
 * @author Alex Fernandez Orta
 * @version 1.0
 */
@Entity
@Table(name = "ruta")
public class Ruta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private String descripcion;
    
    @Column(nullable = false)
    private Integer desnivel;
    
    @Column(nullable = false)
    private String dificultad;
    
    @Column(nullable = false)
    private Integer altitud;
    
    @Column(nullable = false)
    private Double distanciaKm;

    //Relacion directa con quedada
    @OneToMany(mappedBy = "ruta", cascade = CascadeType.ALL)
    private List<Quedada> quedadas;

    /**
     * Constructor vacío obligatorio para que el sistema de persistencia (JPA) 
     * pueda gestionar correctamente las instancias de la base de datos.
     */
    public Ruta() {
    }

    /**
     * Constructor con parámetros para crear una ruta con todas sus características técnicas.
     * <p>Al crearla, la lista de quedadas programadas se inicia vacía automáticamente.</p>
     *
     * @param nombre El nombre comercial o descriptivo de la ruta de senderismo.
     * @param descripcion Explicación detallada del recorrido o del paisaje.
     * @param desnivel La diferencia de altura acumulada en metros durante la ruta.
     * @param dificultad El nivel de esfuerzo o destreza necesario (por ejemplo: Baja, Media, Alta).
     * @param altitud El punto más alto en metros sobre el nivel del mar que se alcanza.
     * @param distanciaKm La longitud total de la ruta medida en kilómetros.
     */
    public Ruta(String nombre, String descripcion, Integer desnivel, String dificultad, Integer altitud, Double distanciaKm) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.desnivel = desnivel;
        this.dificultad = dificultad;
        this.altitud = altitud;
        this.distanciaKm = distanciaKm;
        this.quedadas = new ArrayList<>();;
    }

    /**
     * Obtiene el número de id único de la ruta.
     *
     * @return El número identificador.
     */
    public Long getId() {
        return id;
    }

    /**
     * Asigna el número de id a la ruta.
     *
     * @param id El nuevo id de la ruta.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de la ruta.
     *
     * @return El nombre del recorrido.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Cambia el nombre de la ruta.
     *
     * @param nombre El nuevo nombre para la ruta.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción detallada de la ruta.
     *
     * @return El texto explicativo de la ruta.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Modifica la descripción de la ruta.
     *
     * @param descripcion El nuevo texto con la explicación del trayecto.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el desnivel acumulado de la ruta.
     *
     * @return El desnivel medido en metros.
     */
    public Integer getDesnivel() {
        return desnivel;
    }

    /**
     * Cambia el desnivel acumulado de la ruta.
     *
     * @param desnivel Los nuevos metros de desnivel.
     */
    public void setDesnivel(Integer desnivel) {
        this.desnivel = desnivel;
    }

    /**
     * Obtiene el grado de dificultad asignado a la ruta.
     *
     * @return El nivel de dificultad.
     */
    public String getDificultad() {
        return dificultad;
    }

    /**
     * Cambia la valoración de la dificultad de la ruta.
     *
     * @param dificultad El nuevo nivel de dificultad.
     */
    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    /**
     * Obtiene la altitud máxima del trayecto.
     *
     * @return La altura máxima en metros.
     */
    public Integer getAltitud() {
        return altitud;
    }

    /**
     * Cambia la altitud máxima registrada para la ruta.
     *
     * @param altitud La nueva altura en metros.
     */
    public void setAltitud(Integer altitud) {
        this.altitud = altitud;
    }

    /**
     * Obtiene la distancia o longitud total de la ruta.
     *
     * @return La distancia medida en kilómetros.
     */
    public Double getDistanciaKm() {
        return distanciaKm;
    }

    /**
     * Modifica los kilómetros totales de la ruta.
     *
     * @param distanciaKm La nueva distancia en kilómetros.
     */
    public void setDistanciaKm(Double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    /**
     * Obtiene la lista de todas las quedadas organizadas para recorrer esta ruta.
     *
     * @return Una lista con las quedadas asociadas.
     */
    public List<Quedada> getQuedadas() {
        return quedadas;
    }

    /**
     * Asigna o actualiza el conjunto de quedadas que van a utilizar esta ruta.
     *
     * @param quedadas La nueva lista de quedadas vinculadas.
     */
    public void setQuedadas(List<Quedada> quedadas) {
        this.quedadas = quedadas;
    }
}