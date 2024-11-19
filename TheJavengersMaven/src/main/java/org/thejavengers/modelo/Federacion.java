package org.thejavengers.modelo;

import jakarta.persistence.*;

/**
 * Clase que representa una federación.
 * Contiene el código y el nombre de la federación.
 */
@Entity
@Table(name = "federaciones") // Nombre de la tabla en la base de datos
public class Federacion {

    // Atributos mapeados
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del código
    @Column(name = "codigo", nullable = false, unique = true) // El código debe ser único
    private int codigo;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    /**
     * Constructor vacío requerido por Hibernate.
     */
    public Federacion() {
    }

    /**
     * Constructor para inicializar una federación.
     *
     * @param codigo El código único de la federación.
     * @param nombre El nombre de la federación.
     * @throws IllegalArgumentException si el código o el nombre son nulos o vacíos.
     */
    public Federacion(int codigo, String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
        this.codigo = codigo;
        this.nombre = nombre;
    }

    // Getters

    /**
     * Obtiene el código de la federación.
     *
     * @return El código de la federación.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Obtiene el nombre de la federación.
     *
     * @return El nombre de la federación.
     */
    public String getNombre() {
        return nombre;
    }

    // Setters con validación

    /**
     * Establece el código de la federación.
     *
     * @param codigo El nuevo código de la federación. No puede ser nulo o vacío.
     * @throws IllegalArgumentException si el código es nulo o vacío.
     */
    public void setCodigo(int codigo) {
        if (codigo <= 0) {  // Validación del código
            throw new IllegalArgumentException("El código debe ser mayor que cero");
        }
        this.codigo = codigo;
    }

    /**
     * Establece el nombre de la federación.
     *
     * @param nombre El nuevo nombre de la federación. No puede ser nulo o vacío.
     * @throws IllegalArgumentException si el nombre es nulo o vacío.
     */
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
        this.nombre = nombre;
    }

    // Método ToString

    @Override
    public String toString() {
        return "Federacion{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}