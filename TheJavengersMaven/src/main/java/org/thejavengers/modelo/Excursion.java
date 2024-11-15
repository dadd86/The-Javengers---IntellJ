package org.thejavengers.modelo;

import java.time.LocalDate;

/**
 * Clase que representa una excursión organizada por el centro excursionista.
 * Contiene información como el identificador de la excursión, descripción, fecha,
 * duración en días y el precio.
 */
public class Excursion {
    // Atributos
    private final int idExcursion;
    private String descripcion;
    private LocalDate fechaExcursion;
    private int numero_dias;
    private float precio;

    // Constructor
    /**
     * Constructor para inicializar una excursión con todos sus atributos.
     *
     * @param idExcursion   Identificador único de la excursión. No puede ser negativo.
     * @param descripcion   Descripción de la excursión. No puede ser nula o vacía.
     * @param fechaExcursion Fecha en la que se llevará a cabo la excursión. No puede ser nula.
     * @param numeroDias    Número de días que dura la excursión. Debe ser mayor a cero y menor que 365.
     * @param precio        Precio de la excursión. Debe ser mayor o igual a cero y menor que 10,000.
     * @throws IllegalArgumentException si alguno de los parámetros no cumple con las condiciones requeridas.
     */
    public Excursion(int idExcursion, String descripcion, LocalDate fechaExcursion, int numeroDias, float precio) {
        if (idExcursion < 0) {
            throw new IllegalArgumentException("El idExcursion no puede ser negativo");
        }
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede ser nula o vacía");
        }
        if (fechaExcursion == null) {
            throw new IllegalArgumentException("La fecha de la excursión no puede ser nula");
        }
        if (numeroDias <= 0 || numeroDias > 365) {
            throw new IllegalArgumentException("El número de días debe ser mayor a cero y menor o igual a 365");
        }
        if (precio < 0 || precio > 10000) {
            throw new IllegalArgumentException("El precio debe ser mayor o igual a cero y menor que 10,000");
        }

        this.idExcursion = idExcursion;
        this.descripcion = descripcion;
        this.fechaExcursion = fechaExcursion;
        this.numero_dias = numeroDias;
        this.precio = precio;
    }

    // Getters

    /**
     * Obtiene el identificador único de la excursión.
     *
     * @return El identificador de la excursión.
     */
    public int getIdExcursion() {
        return idExcursion;
    }

    /**
     * Obtiene la descripción de la excursión.
     *
     * @return La descripción de la excursión.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Obtiene la fecha de la excursión.
     *
     * @return La fecha de la excursión.
     */
    public LocalDate getFechaExcursion() {
        return fechaExcursion;
    }

    /**
     * Obtiene el número de días que dura la excursión.
     *
     * @return El número de días de la excursión.
     */
    public int getNumero_dias() {
        return numero_dias;
    }

    /**
     * Obtiene el precio de la excursión.
     *
     * @return El precio de la excursión.
     */
    public float getPrecio() {
        return precio;
    }

    // Setters con Validación

    /**
     * Establece una nueva descripción para la excursión.
     *
     * @param descripcion La nueva descripción. No puede ser nula o vacía.
     * @throws IllegalArgumentException si la descripción es nula o vacía.
     */
    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede ser nula o vacía");
        }
        this.descripcion = descripcion;
    }

    /**
     * Establece una nueva fecha para la excursión.
     *
     * @param fecha La nueva fecha de la excursión. No puede ser nula.
     * @throws IllegalArgumentException si la fecha es nula.
     */
    public void setFechaExcursion(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha de la excursión no puede ser nula");
        }
        this.fechaExcursion = fecha;
    }

    /**
     * Establece un nuevo número de días para la excursión.
     *
     * @param numero_dias El nuevo número de días. Debe ser mayor a cero y menor que 365.
     * @throws IllegalArgumentException si el número de días es inválido.
     */
    public void setNumero_dias(int numero_dias) {
        if (numero_dias <= 0 || numero_dias > 365) {
            throw new IllegalArgumentException("El número de días debe ser mayor a cero y menor o igual a 365");
        }
        this.numero_dias = numero_dias;
    }

    /**
     * Establece un nuevo precio para la excursión.
     *
     * @param precio El nuevo precio. Debe ser mayor o igual a cero y menor que 10,000.
     * @throws IllegalArgumentException si el precio es inválido.
     */
    public void setPrecio(float precio) {
        if (precio < 0 || precio > 10000) {
            throw new IllegalArgumentException("El precio debe ser mayor o igual a cero y menor que 10,000");
        }
        this.precio = precio;
    }

    // Método toString

    /**
     * Proporciona una representación en forma de cadena de caracteres del objeto Excursion.
     *
     * @return Una cadena de caracteres que contiene los datos de la excursión.
     */
    @Override
    public String toString() {
        return "Excursion{" +
                "idExcursion=" + idExcursion +
                ", descripcion='" + descripcion + '\'' +
                ", fechaExcursion=" + fechaExcursion +
                ", numeroDias=" + numero_dias +
                ", precio=" + precio +
                '}';
    }
}
