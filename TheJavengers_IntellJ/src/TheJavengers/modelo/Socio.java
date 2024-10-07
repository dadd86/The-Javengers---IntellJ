package TheJavengers.modelo;

/**
 * Clase abstracta que representa un Socio.
 * Define atributos y métodos comunes a todos los tipos de socios.
 */
public abstract class Socio {

    protected String idSocio;
    protected String nombre;
    protected String apellidos;
    protected static final float cuotaMensual = 10.0f;

    /**
     * Constructor para crear un nuevo socio.
     *
     * @param idSocio   Identificador del socio.
     * @param nombre    Nombre del socio.
     * @param apellidos Apellidos del socio.
     */
    public Socio(String idSocio, String nombre, String apellidos) {
        this.idSocio = idSocio;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    // Getters y Setters

    /**
     * Obtiene el identificador del socio.
     *
     * @return El identificador del socio.
     */
    public String getIdSocio() {
        return idSocio;
    }

    /**
     * Establece el identificador del socio.
     *
     * @param idSocio El nuevo identificador.
     */
    public void setIdSocio(String idSocio) {
        this.idSocio = idSocio;
    }

    /**
     * Obtiene el nombre del socio.
     *
     * @return El nombre del socio.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del socio.
     *
     * @param nombre El nuevo nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene los apellidos del socio.
     *
     * @return Los apellidos del socio.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los apellidos del socio.
     *
     * @param apellidos Los nuevos apellidos.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Calcula la cuota mensual del socio.
     *
     * @return La cuota mensual.
     */
    public abstract float calcularCuotaMensual();

    /**
     * Calcula el precio de una excursión para el socio.
     *
     * @param excursion La excursión a calcular.
     * @return El precio de la excursión.
     */
    public abstract float calcularPrecioExcursion(Excursion excursion);

    @Override
    public String toString() {
        return "Socio{" +
                "idSocio='" + idSocio + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                '}';
    }
}


