package TheJavengers.modelo;

import java.time.LocalDate;

/**
 * Clase que representa una Excursión.
 * Incluye información sobre el identificador, descripción, fecha, número de días y precio.
 */
public class Excursion {

    private String idExcursion;
    private String descripcion;
    private LocalDate fechaExcursion;
    private int numeroDias;
    private float precio;

    /**
     * Constructor para crear una nueva excursión.
     *
     * @param idExcursion   Identificador de la excursión.
     * @param descripcion   Descripción de la excursión.
     * @param fechaExcursion Fecha de la excursión.
     * @param numeroDias    Número de días de la excursión.
     * @param precio        Precio de la excursión.
     */
    public Excursion(String idExcursion, String descripcion, LocalDate fechaExcursion, int numeroDias, float precio) {
        this.idExcursion = idExcursion;
        this.descripcion = descripcion;
        this.fechaExcursion = fechaExcursion;
        this.numeroDias = numeroDias;
        this.precio = precio;
    }

    // Getters y Setters

    /**
     * Obtiene el identificador de la excursión.
     *
     * @return El identificador de la excursión.
     */
    public String getIdExcursion() {
        return idExcursion;
    }

    /**
     * Establece el identificador de la excursión.
     *
     * @param idExcursion El nuevo identificador de la excursión.
     */
    public void setIdExcursion(String idExcursion) {
        this.idExcursion = idExcursion;
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
     * Establece la descripción de la excursión.
     *
     * @param descripcion La nueva descripción de la excursión.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
     * Establece la fecha de la excursión.
     *
     * @param fechaExcursion La nueva fecha de la excursión.
     */
    public void setFechaExcursion(LocalDate fechaExcursion) {
        this.fechaExcursion = fechaExcursion;
    }

    /**
     * Obtiene el número de días de la excursión.
     *
     * @return El número de días.
     */
    public int getNumeroDias() {
        return numeroDias;
    }

    /**
     * Establece el número de días de la excursión.
     *
     * @param numeroDias El nuevo número de días.
     */
    public void setNumeroDias(int numeroDias) {
        this.numeroDias = numeroDias;
    }

    /**
     * Obtiene el precio de la excursión.
     *
     * @return El precio de la excursión.
     */
    public float getPrecio() {
        return precio;
    }

    /**
     * Establece el precio de la excursión.
     *
     * @param precio El nuevo precio de la excursión.
     */
    public void setPrecio(float precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Excursion{" +
                "idExcursion='" + idExcursion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaExcursion=" + fechaExcursion +
                ", numeroDias=" + numeroDias +
                ", precio=" + precio +
                '}';
    }
}
