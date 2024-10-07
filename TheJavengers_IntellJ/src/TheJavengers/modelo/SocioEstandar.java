package TheJavengers.modelo;

/**
 * Clase que representa un Socio Estándar.
 * Incluye información adicional como NIF y tipo de seguro.
 */
public class SocioEstandar extends Socio {

    private String nif;
    private TipoSeguro seguro;

    /**
     * Constructor para crear un socio estándar.
     *
     * @param idSocio   Identificador del socio.
     * @param nombre    Nombre del socio.
     * @param apellidos Apellidos del socio.
     * @param nif       NIF del socio.
     * @param seguro    Tipo de seguro.
     */
    public SocioEstandar(String idSocio, String nombre, String apellidos, String nif, TipoSeguro seguro) {
        super(idSocio, nombre, apellidos);
        this.nif = nif;
        this.seguro = seguro;
    }

    // Getters y Setters

    /**
     * Obtiene el NIF del socio.
     *
     * @return El NIF del socio.
     */
    public String getNif() {
        return nif;
    }

    /**
     * Establece el NIF del socio.
     *
     * @param nif El nuevo NIF.
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * Obtiene el tipo de seguro del socio.
     *
     * @return El tipo de seguro.
     */
    public TipoSeguro getSeguro() {
        return seguro;
    }

    /**
     * Establece el tipo de seguro del socio.
     *
     * @param seguro El nuevo tipo de seguro.
     */
    public void setSeguro(TipoSeguro seguro) {
        this.seguro = seguro;
    }

    // Implementación de métodos abstractos

    /**
     * Calcula la cuota mensual del socio estándar.
     *
     * @return La cuota mensual sin descuento.
     */
    @Override
    public float calcularCuotaMensual() {
        return cuotaMensual;
    }

    /**
     * Calcula el precio de una excursión para el socio estándar.
     * Incluye el costo del seguro.
     *
     * @param excursion La excursión a calcular.
     * @return El precio total de la excursión.
     */
    @Override
    public float calcularPrecioExcursion(Excursion excursion) {
        return excursion.getPrecio() + seguro.getPrecio();
    }

    @Override
    public String toString() {
        return super.toString() + ", SocioEstandar{" +
                "nif='" + nif + '\'' +
                ", seguro=" + seguro +
                '}';
    }
}
