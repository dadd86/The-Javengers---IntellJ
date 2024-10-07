package TheJavengers.modelo;

/**
 * Clase que representa una Federación.
 * Incluye un código único y un nombre que identifica a la federación.
 */
public class Federacion {

    private String codigo;
    private String nombre;

    /**
     * Constructor que crea una nueva federación con un código y nombre.
     *
     * @param codigo Código único de la federación.
     * @param nombre Nombre de la federación.
     */
    public Federacion(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    // Getters y Setters

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
     * @param codigo El nuevo código.
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
     * @param nombre El nuevo nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Federacion{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
