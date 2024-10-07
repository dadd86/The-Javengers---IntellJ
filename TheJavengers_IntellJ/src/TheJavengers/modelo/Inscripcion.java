package TheJavengers.modelo;

import java.time.LocalDate;

/**
 * Clase que representa la inscripción de un socio en una excursión.
 */
public class Inscripcion {

    private int idInscripcion;
    private Socio socio;
    private Excursion excursion;
    private LocalDate fechaInscripcion;

    /**
     * Constructor para crear una inscripción.
     *
     * @param idInscripcion    Identificador de la inscripción.
     * @param socio            El socio que se inscribe.
     * @param excursion        La excursión en la que se inscribe.
     * @param fechaInscripcion Fecha de la inscripción.
     */
    public Inscripcion(int idInscripcion, Socio socio, Excursion excursion, LocalDate fechaInscripcion) {
        this.idInscripcion = idInscripcion;
        this.socio = socio;
        this.excursion = excursion;
        this.fechaInscripcion = fechaInscripcion;
    }

    // Getters y Setters

    /**
     * Obtiene el identificador de la inscripción.
     *
     * @return El identificador de la inscripción.
     */
    public int getIdInscripcion() {
        return idInscripcion;
    }

    /**
     * Establece el identificador de la inscripción.
     *
     * @param idInscripcion El nuevo identificador.
     */
    public void setIdInscripcion(int idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    /**
     * Obtiene el socio asociado a la inscripción.
     *
     * @return El socio.
     */
    public Socio getSocio() {
        return socio;
    }

    /**
     * Establece el socio de la inscripción.
     *
     * @param socio El nuevo socio.
     */
    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    /**
     * Obtiene la excursión asociada a la inscripción.
     *
     * @return La excursión.
     */
    public Excursion getExcursion() {
        return excursion;
    }

    /**
     * Establece la excursión de la inscripción.
     *
     * @param excursion La nueva excursión.
     */
    public void setExcursion(Excursion excursion) {
        this.excursion = excursion;
    }

    /**
     * Obtiene la fecha de inscripción.
     *
     * @return La fecha de inscripción.
     */
    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    /**
     * Establece la fecha de inscripción.
     *
     * @param fechaInscripcion La nueva fecha.
     */
    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    /**
     * Calcula el precio de la inscripción según el tipo de socio.
     *
     * @return El precio de la inscripción.
     */
    public float calcularPrecioInscripcion() {
        return socio.calcularPrecioExcursion(excursion);
    }

    /**
     * Verifica si es posible cancelar la inscripción.
     *
     * @param fechaExcursion Fecha de la excursión.
     * @param fechaActual    Fecha actual.
     * @return true si se puede cancelar, false en caso contrario.
     */
    public boolean cancelarInscripcion(LocalDate fechaExcursion, LocalDate fechaActual) {
        return fechaActual.isBefore(fechaExcursion);
    }

    @Override
    public String toString() {
        return "Inscripcion{" +
                "idInscripcion=" + idInscripcion +
                ", socio=" + socio +
                ", excursion=" + excursion +
                ", fechaInscripcion=" + fechaInscripcion +
                '}';
    }
}

