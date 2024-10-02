package TheJavengers;

import java.util.Date;

/**
 * Clase que representa una inscripción de un socio a una excursión.
 * Cada inscripción está vinculada a un socio y a una excursión específica,
 * y tiene un número único de identificación.
 */
public class Inscripcion {

    // Atributos de la clase
    private int numeroInscripcion;
    private Socio socio;
    private Excursion excursion;

    /**
     * Constructor para crear una nueva inscripción de un socio en una excursión.
     *
     * @param numeroInscripcion Número único de la inscripción.
     * @param socio El socio que realiza la inscripción.
     * @param excursion La excursión a la que se inscribe el socio.
     */
    public Inscripcion(int numeroInscripcion, Socio socio, Excursion excursion) {
        this.numeroInscripcion = numeroInscripcion;
        this.socio = socio;
        this.excursion = excursion;
    }

    /**
     * Obtiene el número único de la inscripción.
     *
     * @return El número de inscripción.
     */
    public int getNumeroInscripcion() {
        return numeroInscripcion;
    }

    /**
     * Establece un nuevo número de inscripción.
     *
     * @param numeroInscripcion El nuevo número de inscripción.
     */
    public void setNumeroInscripcion(int numeroInscripcion) {
        this.numeroInscripcion = numeroInscripcion;
    }

    /**
     * Obtiene el socio que ha realizado la inscripción.
     *
     * @return El socio que ha realizado la inscripción.
     */
    public Socio getSocio() {
        return socio;
    }

    /**
     * Establece el socio que realiza la inscripción.
     *
     * @param socio El nuevo socio que realiza la inscripción.
     */
    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    /**
     * Obtiene la excursión a la que el socio se ha inscrito.
     *
     * @return La excursión a la que el socio está inscrito.
     */
    public Excursion getExcursion() {
        return excursion;
    }

    /**
     * Establece una nueva excursión para la inscripción.
     *
     * @param excursion La nueva excursión de la inscripción.
     */
    public void setExcursion(Excursion excursion) {
        this.excursion = excursion;
    }

    /**
     * Cancela la inscripción si la fecha actual es anterior a la fecha de la excursión.
     *
     * @param fechaActual La fecha actual para verificar si se puede cancelar la inscripción.
     * @return {@code true} si la inscripción se cancela exitosamente, {@code false} si no se puede cancelar.
     */
    public boolean cancelarInscripcion(Date fechaActual) {
        if (fechaActual.before(excursion.getFecha())) {
            // Lógica para cancelar la inscripción
            return true;
        }
        return false;
    }

    /**
     * Devuelve una representación en forma de cadena de la inscripción.
     *
     * @return Una cadena que describe la inscripción.
     */
    @Override
    public String toString() {
        return "Inscripcion{" +
                "numeroInscripcion=" + numeroInscripcion +
                ", socio=" + socio +
                ", excursion=" + excursion +
                '}';
    }
}
