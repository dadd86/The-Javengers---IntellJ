package TheJavengers;

/**
 * Clase que representa a un socio estándar del centro excursionista.
 * Los socios estándar deben proporcionar un NIF y tener un seguro contratado.
 */
class SocioEstandar extends Socio {

    // Atributos de la clase
    private String nif;
    private Seguro seguro;

    /**
     * Constructor que crea un nuevo socio estándar con un número de socio, nombre, NIF y seguro.
     *
     * @param numeroSocio El número único del socio.
     * @param nombre El nombre del socio.
     * @param nif El NIF del socio.
     * @param seguro El seguro asociado al socio.
     */
    public SocioEstandar(int numeroSocio, String nombre, String nif, Seguro seguro) {
        super(numeroSocio, nombre);
        this.nif = nif;
        this.seguro = seguro;
    }

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
     * @param nif El nuevo NIF del socio.
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * Obtiene el seguro asociado al socio.
     *
     * @return El seguro del socio.
     */
    public Seguro getSeguro() {
        return seguro;
    }

    /**
     * Establece el seguro del socio.
     *
     * @param seguro El nuevo seguro del socio.
     */
    public void setSeguro(Seguro seguro) {
        this.seguro = seguro;
    }

    /**
     * Calcula el descuento aplicado en una excursión para un socio estándar.
     * Los socios estándar no tienen descuento en las excursiones.
     *
     * @param precio El precio base de la excursión.
     * @return El mismo precio, ya que no hay descuento.
     */
    @Override
    public double calcularDescuentoExcursion(double precio) {
        return precio; // No hay descuento para socios estándar
    }

    /**
     * Calcula el descuento aplicado en la cuota mensual para un socio estándar.
     * Los socios estándar no tienen descuento en la cuota mensual.
     *
     * @param cuota El precio base de la cuota mensual.
     * @return El mismo precio, ya que no hay descuento.
     */
    @Override
    public double calcularDescuentoCuota(double cuota) {
        return cuota; // No hay descuento para socios estándar
    }

    /**
     * Devuelve una representación en forma de cadena del socio estándar.
     *
     * @return Una cadena que describe al socio estándar con su NIF y seguro.
     */
    @Override
    public String toString() {
        return super.toString() + ", SocioEstandar{" +
                "nif='" + nif + '\'' +
                ", seguro=" + seguro +
                '}';
    }
}
