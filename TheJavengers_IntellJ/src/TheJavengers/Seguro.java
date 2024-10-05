package TheJavengers;

/**
 * Enum que define los tipos de seguro disponibles.
 * Los tipos de seguro pueden ser:
 * <ul>
 *     <li>{@link TipoSeguro#BASICO} - Seguro básico.</li>
 *     <li>{@link TipoSeguro#COMPLETO} - Seguro completo con mayor cobertura.</li>
 * </ul>
 */
enum TipoSeguro {
    BASICO,
    COMPLETO;
}

/**
 * Clase que representa un seguro para los socios.
 * Cada seguro tiene un tipo (básico o completo) y un precio.
 */
public class Seguro {

    private TipoSeguro tipo;
    private double precio;

    /**
     * Constructor que crea un nuevo seguro con un tipo y un precio.
     *
     * @param tipo El tipo de seguro (Básico o Completo).
     * @param precio El precio del seguro.
     */
    public Seguro(TipoSeguro tipo, double precio) {
        this.tipo = tipo;
        this.precio = precio;
    }

    /**
     * Obtiene el tipo de seguro.
     *
     * @return El tipo de seguro (Básico o Completo).
     */
    public TipoSeguro getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de seguro.
     *
     * @param tipo El nuevo tipo de seguro (Básico o Completo).
     */
    public void setTipo(TipoSeguro tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el precio del seguro.
     *
     * @return El precio del seguro.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del seguro.
     *
     * @param precio El nuevo precio del seguro.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Devuelve una representación en forma de cadena del seguro.
     *
     * @return Una cadena que describe el seguro.
     */
    @Override
    public String toString() {
        return "Seguro{" +
                "tipo=" + tipo +
                ", precio=" + precio +
                '}';
    }
}