package TheJavengers;

/**
 * Clase que representa una Federación.
 * Las federaciones están asociadas a los socios federados, e incluyen un código
 * alfanumérico único y un nombre que identifica a la federación.
 */
public class Federacion {

    // Atributos de la clase
    private String codigo;
    private String nombre;

    /**
     * Constructor que crea una nueva federación con un código y nombre.
     *
     * @param codigo Código alfanumérico único de la federación.
     * @param nombre Nombre de la federación.
     */
    public Federacion(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    /**
     * Obtiene el código de la federación.
     *
     * @return El código de la federación.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establece un nuevo código para la federación.
     *
     * @param codigo El nuevo código de la federación.
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene el nombre de la federación.
     *
     * @return El nombre de la federación.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece un nuevo nombre para la federación.
     *
     * @param nombre El nuevo nombre de la federación.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve una representación en forma de cadena de la federación.
     *
     * @return Una cadena que describe la federación.
     */
    @Override
    public String toString() {
        return "Federacion{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
