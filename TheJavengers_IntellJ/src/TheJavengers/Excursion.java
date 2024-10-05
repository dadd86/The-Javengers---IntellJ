package TheJavengers;

import java.util.Date;

/**
 * Clase que representa una excursión organizada por el centro excursionista.
 * Una excursión puede ser de uno o varios días, y tiene un precio de inscripción.
 */
public class Excursion {

    // Atributos de la clase
    private String codigo;
    private String descripcion;
    private Date fecha;
    private int numeroDias;
    private double precioInscripcion;

    /**
     * Constructor para crear una nueva excursión.
     *
     * @param codigo Código alfanumérico único de la excursión.
     * @param descripcion Descripción de la excursión.
     * @param fecha Fecha en que se realizará la excursión.
     * @param numeroDias Número de días que durará la excursión.
     * @param precioInscripcion Precio de la inscripción para participar en la excursión.
     */
    public Excursion(String codigo, String descripcion, Date fecha, int numeroDias, double precioInscripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.numeroDias = numeroDias;
        this.precioInscripcion = precioInscripcion;
    }

    /**
     * Obtiene el código de la excursión.
     *
     * @return El código de la excursión.
     */
    public String getCodigo() {
        return codigo;
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
     * Obtiene la fecha en que se realizará la excursión.
     *
     * @return La fecha de la excursión.
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Obtiene el número de días que durará la excursión.
     *
     * @return El número de días de la excursión.
     */
    public int getNumeroDias() {
        return numeroDias;
    }

    /**
     * Obtiene el precio de inscripción para la excursión.
     *
     * @return El precio de la inscripción.
     */
    public double getPrecioInscripcion() {
        return precioInscripcion;
    }

    /**
     * Establece el código de la excursión.
     *
     * @param codigo El nuevo código de la excursión.
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
     * Establece la fecha en que se realizará la excursión.
     *
     * @param fecha La nueva fecha de la excursión.
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Establece el número de días que durará la excursión.
     *
     * @param numeroDias El nuevo número de días de la excursión.
     */
    public void setNumeroDias(int numeroDias) {
        this.numeroDias = numeroDias;
    }

    /**
     * Establece el precio de inscripción para la excursión.
     *
     * @param precioInscripcion El nuevo precio de la inscripción.
     */
    public void setPrecioInscripcion(double precioInscripcion) {
        this.precioInscripcion = precioInscripcion;
    }

    /**
     * Devuelve una representación en forma de cadena de la excursión.
     *
     * @return Una cadena que describe la excursión.
     */
    @Override
    public String toString() {
        return "Excursion{" +
                "codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fecha=" + fecha +
                ", numeroDias=" + numeroDias +
                ", precioInscripcion=" + precioInscripcion +
                '}';
    }
}

