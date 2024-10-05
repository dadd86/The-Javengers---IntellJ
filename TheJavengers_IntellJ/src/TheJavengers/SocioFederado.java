package TheJavengers;

/**
 * Clase que representa a un socio federado del centro excursionista.
 * Los socios federados están afiliados a una federación y reciben descuentos en excursiones y cuotas mensuales.
 */
class SocioFederado extends Socio {

    // Atributos de la clase
    private String nif;
    private Federacion federacion;

    /**
     * Constructor que crea un nuevo socio federado con un número de socio, nombre, NIF y una federación.
     *
     * @param numeroSocio El número único del socio.
     * @param nombre El nombre del socio.
     * @param nif El NIF del socio.
     * @param federacion La federación a la que pertenece el socio.
     */
    public SocioFederado(int numeroSocio, String nombre, String nif, Federacion federacion) {
        super(numeroSocio, nombre);
        this.nif = nif;
        this.federacion = federacion;
    }

    /**
     * Obtiene el NIF del socio federado.
     *
     * @return El NIF del socio.
     */
    public String getNif() {
        return nif;
    }

    /**
     * Establece el NIF del socio federado.
     *
     * @param nif El nuevo NIF del socio.
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * Obtiene la federación a la que pertenece el socio.
     *
     * @return La federación del socio.
     */
    public Federacion getFederacion() {
        return federacion;
    }

    /**
     * Establece la federación a la que pertenece el socio.
     *
     * @param federacion La nueva federación del socio.
     */
    public void setFederacion(Federacion federacion) {
        this.federacion = federacion;
    }

    /**
     * Calcula el descuento aplicado en una excursión para un socio federado.
     * Los socios federados reciben un 10% de descuento en las excursiones.
     *
     * @param precio El precio base de la excursión.
     * @return El precio con un descuento del 10% aplicado.
     */
    @Override
    public double calcularDescuentoExcursion(double precio) {
        return precio * 0.90; // 10% de descuento
    }

    /**
     * Calcula el descuento aplicado en la cuota mensual para un socio federado.
     * Los socios federados reciben un 5% de descuento en la cuota mensual.
     *
     * @param cuota El precio base de la cuota mensual.
     * @return El precio con un descuento del 5% aplicado.
     */
    @Override
    public double calcularDescuentoCuota(double cuota) {
        return cuota * 0.95; // 5% de descuento
    }

    /**
     * Devuelve una representación en forma de cadena del socio federado.
     *
     * @return Una cadena que describe al socio federado con su NIF y federación.
     */
    @Override
    public String toString() {
        return super.toString() + ", SocioFederado{" +
                "nif='" + nif + '\'' +
                ", federacion=" + federacion +
                '}';
    }
}
