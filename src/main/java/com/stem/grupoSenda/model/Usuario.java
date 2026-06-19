package com.stem.grupoSenda.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

/**
 * Entidad que representa a un usuario o excursionista en el sistema.
 * Esta clase se conecta con la tabla "usuario" en la base de datos y guarda
 * sus datos personales (nombre, apellido, email), su nivel de experiencia, 
 * así como las listas de las quedadas a las que asiste y los comentarios que escribe.
 *
 * @author Alex Fernandez Orta
 * @version 1.0
 */
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private Double nivelExperiencia;

    @Column(nullable = false)
    private String email;

    // Relacion n:m con ruta
    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "usuarios_quedadas", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_quedada"))
    private List<Quedada> quedadas;

    // Relacion directa con comentario
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("usuario")
    private List<Comentario> comentarios;

    /**
     * Constructor vacío obligatorio para que el sistema de persistencia (JPA) 
     * pueda gestionar correctamente las instancias de la base de datos.
     */
    public Usuario() {
    }

    /**
     * Constructor con parámetros para crear un usuario con sus datos principales.
     * <p>Al crear al usuario, las listas de quedadas y comentarios se inician vacías automáticamente.</p>
     *
     * @param nombre El nombre de pila del usuario.
     * @param apellido El apellido o apellidos del usuario.
     * @param nivelExperiencia La valoración numérica de su habilidad o experiencia en senderismo.
     * @param email La dirección de correo electrónico de contacto.
     */
    public Usuario(String nombre, String apellido, Double nivelExperiencia, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nivelExperiencia = nivelExperiencia;
        this.email = email;
        this.quedadas = new ArrayList<>();
        this.comentarios = new ArrayList<>();
    }

    /**
     * Une al usuario a una quedada concreta evitando que se añada dos veces la misma.
     * <p>El método recorre la lista de quedadas actuales del usuario para comprobar si el id 
     * de la nueva quedada ya está apuntado. Si no se encuentra repetida, la añade a su lista.</p>
     * * @param quedadaAniadir El objeto de la quedada a la que quiere asistir el usuario.
     */
    public void irAQuedada(Quedada quedadaAniadir) {
        boolean duplicado = false;
        for (Quedada quedada : quedadas) {
            if (quedada.getId() == quedadaAniadir.getId()) {
                duplicado = true;
            }
        }
        if (!duplicado) {
            this.quedadas.add(quedadaAniadir);
        }
    }

    /**
     * Obtiene el número de id único del usuario.
     *
     * @return El número identificador.
     */
    public Long getId() {
        return id;
    }

    /**
     * Asigna el número de id al usuario.
     *
     * @param id El nuevo id del usuario.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return El nombre de pila.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Cambia el nombre del usuario.
     *
     * @param nombre El nuevo nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el apellido del usuario.
     *
     * @return El apellido o apellidos.
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Cambia el apellido del usuario.
     *
     * @param apellido El nuevo apellido.
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Obtiene la puntuación de la experiencia que tiene el usuario en montaña.
     *
     * @return El nivel de experiencia como decimal.
     */
    public Double getNivelExperiencia() {
        return nivelExperiencia;
    }

    /**
     * Modifica el nivel de experiencia del usuario.
     *
     * @param nivelExperiencia El nuevo nivel de experiencia.
     */
    public void setNivelExperiencia(Double nivelExperiencia) {
        this.nivelExperiencia = nivelExperiencia;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return La dirección de email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Cambia el correo electrónico del usuario.
     *
     * @param email La nueva dirección de correo.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la lista de todas las quedadas a las que se ha apuntado este usuario.
     *
     * @return Una lista con las quedadas del usuario.
     */
    public List<Quedada> getQuedadas() {
        return quedadas;
    }

    /**
     * Asigna o actualiza la lista completa de quedadas asociadas al usuario.
     *
     * @param quedadas La nueva lista de quedadas.
     */
    public void setQuedadas(List<Quedada> quedadas) {
        this.quedadas = quedadas;
    }

    /**
     * Obtiene la lista de todos los comentarios publicados por este usuario.
     *
     * @return Una lista con sus comentarios.
     */
    public List<Comentario> getComentarios() {
        return comentarios;
    }

    /**
     * Asigna la lista de comentarios escritos por el usuario.
     *
     * @param comentarios Los nuevos comentarios a guardar.
     */
    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    /**
     * Devuelve una cadena de texto con un resumen de los datos principales del usuario.
     *
     * @return Un texto con la información de los atributos del usuario.
     */
    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", nivelExperiencia="
                + nivelExperiencia + ", email=" + email + "]";
    }
}