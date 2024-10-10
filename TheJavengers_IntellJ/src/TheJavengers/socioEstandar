package TheJavengers;

/**
 * La clase {@code socioEstandar} representa un socio estándar de un club, que tiene un NIF
 * y un seguro contratado. Hereda de la clase {@code Socio} y añade la funcionalidad para
 * manejar el NIF y el tipo de seguro contratado.
 */
public class socioEstandar extends Socio {
    
    private String nif;
    private Seguro seguroContratado;

    /**
     * Constructor para crear un socio estándar con un ID de socio, nombre, apellidos, NIF y seguro contratado.
     *
     * @param idSocio el ID del socio
     * @param nombre el nombre del socio
     * @param apellidos los apellidos del socio
     * @param nif el NIF del socio
     * @param seguroContratado el seguro contratado por el socio
     */
    public socioEstandar(String idSocio, String nombre, String apellidos, String nif, Seguro seguroContratado) {
        super(idSocio, nombre, apellidos);
        this.nif = nif;
        this.seguroContratado = seguroContratado;
    }

    /**
     * Obtiene la cuota mensual total del socio estándar, que incluye la cuota mensual básica y el costo del seguro.
     *
     * @return la cuota mensual total del socio
     */
    public double getCuotaMensualTotal() {
        return cuotaMensual + seguroContratado.getPrecio();
    }

    /**
     * Obtiene el NIF del socio estándar.
     *
     * @return el NIF del socio
     */
    public String getNif() {
        return nif;
    }

    /**
     * Establece un nuevo NIF para el socio estándar.
     *
     * @param nif el nuevo NIF del socio
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * Obtiene el seguro contratado por el socio estándar.
     *
     * @return el seguro contratado por el socio
     */
    public Seguro getSeguroContratado() {
        return seguroContratado;
    }

    /**
     * Establece un nuevo seguro para el socio estándar.
     *
     * @param seguroContratado el nuevo seguro que se contratará
     */
    public void setSeguroContratado(Seguro seguroContratado) {
        this.seguroContratado = seguroContratado;
    }

    /**
     * Devuelve una representación en cadena del socio estándar, incluyendo su NIF, tipo de seguro
     * y la cuota mensual total.
     *
     * @return una cadena con los detalles del socio estándar
     */
    @Override
    public String toString() {
        return super.toString() +
                "NIF: " + this.nif + "\n" +
                "Seguro contratado: " + this.seguroContratado.getTipo() + "\n" +
                "Cuota mensual total: " + getCuotaMensualTotal() + "\n";
    }
}
