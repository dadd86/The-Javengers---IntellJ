package TheJavengers;
/**
 * Clase abstracta que representa un socio del centro excursionista.
 * Proporciona la estructura básica para definir un socio, incluyendo los atributos comunes
 * como idSocio, nombre, apellidos, y la cuota mensual fija.
 * Subclases específicas deben implementar los métodos abstractos para calcular la cuota mensual
 * y el precio de una excursión.
 */
public abstract class Socio {
    // Atributos

    /** Identificador único del socio. */
    private final String idSocio;

    /** Nombre del socio. */
    private String nombre;

    /** Apellidos del socio. */
    private String apellidos;

    /** Cuota mensual fija para todos los socios. */
    public static final float CUOTA_MENSUAL = 10.0f;

    // Constructor

    /**
     * Constructor para inicializar un objeto de tipo Socio.
     *
     * @param idSocio   Identificador único del socio. No puede ser nulo o vacío.
     * @param nombre    Nombre del socio. No puede ser nulo o vacío.
     * @param apellidos Apellidos del socio. No puede ser nulo o vacío.
     * @throws IllegalArgumentException si alguno de los parámetros es nulo o vacío.
     */
    public Socio(String idSocio, String nombre, String apellidos) {
        if (idSocio == null || idSocio.trim().isEmpty()) {
            throw new IllegalArgumentException("El idSocio no puede ser nulo o vacío");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
        if (apellidos == null || apellidos.trim().isEmpty()) {
            throw new IllegalArgumentException("Los apellidos no pueden ser nulos o vacíos");
        }

        this.idSocio = idSocio;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    // Getters

    /**
     * Obtiene el identificador único del socio.
     *
     * @return El identificador del socio.
     */
    public String getIdSocio() {
        return idSocio;
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
     * Obtiene los apellidos del socio.
     *
     * @return Los apellidos del socio.
     */
    public String getApellidos() {
        return apellidos;
    }

    // Setters con Validación

    /**
     * Establece el nombre del socio.
     *
     * @param nombre El nuevo nombre del socio. No puede ser nulo o vacío.
     * @throws IllegalArgumentException si el nombre es nulo o vacío.
     */
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
        this.nombre = nombre;
    }

    /**
     * Establece los apellidos del socio.
     *
     * @param apellidos Los nuevos apellidos del socio. No pueden ser nulos o vacíos.
     * @throws IllegalArgumentException si los apellidos son nulos o vacíos.
     */
    public void setApellidos(String apellidos) {
        if (apellidos == null || apellidos.trim().isEmpty()) {
            throw new IllegalArgumentException("Los apellidos no pueden ser nulos o vacíos");
        }
        this.apellidos = apellidos;
    }

    // Métodos Abstractos

    /**
     * Calcula la cuota mensual del socio.
     * Este método debe ser implementado por las subclases para proporcionar la lógica específica
     * del cálculo de la cuota mensual según el tipo de socio.
     *
     * @return La cuota mensual del socio.
     */
    public abstract float calcularCuotaMensual();
    /**
     * Calcula el precio de una excursión para el socio.
     *
     * @param excursion La excursión a calcular.
     * @return El precio de la excursión.
     */
    public abstract float calcularPrecioExcursion(Excursion excursion);


    // Método toString

    /**
     * Proporciona una representación en forma de cadena de caracteres del objeto Socio.
     *
     * @return Una cadena de caracteres que contiene los datos del socio.
     */
    @Override
    public String toString() {
        return "Socio{" +
                "idSocio='" + idSocio + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                '}';
    }

    // Métodos equals y hashCode

    /**
     * Compara este objeto con otro objeto para verificar si son iguales.
     * Dos objetos Socio son iguales si tienen el mismo identificador de socio.
     *
     * hashCode(): Implementado para asegurar que los objetos Socio puedan
     * ser utilizados correctamente en colecciones como HashSet.
     *
     * @param obj El objeto a comparar.
     * @return true si los objetos son iguales; false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Socio socio = (Socio) obj;
        return idSocio.equals(socio.idSocio);
    }

    /**
     * Calcula el código hash para este objeto Socio.
     *
     * equals(): Implementado para comparar objetos Socio basados en el idSocio.
     * Esto es útil para verificar la igualdad lógica, por ejemplo, en listas.
     *
     * Utiliza el identificador de socio para generar el código hash.
     *
     * @return El código hash del objeto.
     */
    @Override
    public int hashCode() {
        return idSocio.hashCode();
    }
}
