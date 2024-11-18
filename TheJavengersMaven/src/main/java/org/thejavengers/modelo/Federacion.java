package org.thejavengers.modelo;

/**
 * Clase que representa una federación.
 * Contiene el código y el nombre de la federación.
 */
public class Federacion {
    // Atributos

    private int codigo;
    private String nombre;

    // Constructor

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
