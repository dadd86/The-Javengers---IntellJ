package TheJavengers.modelo;

/**
 * Clase que representa un Socio Federado.
 * Incluye información adicional como NIF y federación a la que pertenece.
 */
public class SocioFederado extends Socio {

    private String nif;
    private Federacion federacion;
    private static final float DESCUENTO_CUOTA = 0.05f;      // 5% de descuento
    private static final float DESCUENTO_EXCURSION = 0.10f;  // 10% de descuento

    /**
     * Constructor para crear un socio federado.
     *
     * @param idSocio    Identificador del socio.
     * @param nombre     Nombre del socio.
     * @param apellidos  Apellidos del socio.
     * @param nif        NIF del socio.
     * @param federacion Federación a la que pertenece.
     */
    public SocioFederado(String idSocio, String nombre, String apellidos, String nif, Federacion federacion) {
        super(idSocio, nombre, apellidos);
        this.nif = nif;
        this.federacion = federacion;
    }

    // Getters y Setters

    /**
     * Obtiene el NIF del socio.
     *
     * @return El NIF.
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
     * Obtiene la federación del socio.
     *
     * @return La federación.
     */
    public Federacion getFederacion() {
        return federacion;
    }

    /**
     * Establece la federación del socio.
     *
     * @param federacion La nueva federación.
     */
    public void setFederacion(Federacion federacion) {
        this.federacion = federacion;
    }

    // Implementación de métodos abstractos

    /**
     * Calcula la cuota mensual del socio federado con el descuento correspondiente.
     *
     * @return La cuota mensual con descuento.
     */
    @Override
    public float calcularCuotaMensual() {
        return cuotaMensual * (1 - DESCUENTO_CUOTA);
    }

    /**
     * Calcula el precio de una excursión para el socio federado con el descuento correspondiente.
     *
     * @param excursion La excursión a calcular.
     * @return El precio con descuento.
     */
    @Override
    public float calcularPrecioExcursion(Excursion excursion) {
        return excursion.getPrecio() * (1 - DESCUENTO_EXCURSION);
    }

    @Override
    public String toString() {
        return super.toString() + ", SocioFederado{" +
                "nif='" + nif + '\'' +
                ", federacion=" + federacion +
                '}';
    }
}



