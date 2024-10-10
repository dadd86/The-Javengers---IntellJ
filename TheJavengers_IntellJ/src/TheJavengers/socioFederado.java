package TheJavengers;

/**
 * La clase {@code socioFederado} representa a un socio federado que está asociado a una federación
 * deportiva. Hereda de la clase {@code Socio} e incluye atributos adicionales como el NIF y la federación.
 */
public class socioFederado extends Socio {
    
    private String nif;
    private Federacion federacion;

    /**
     * Constructor para crear un socio federado con ID de socio, nombre, apellidos, NIF y federación.
     *
     * @param idSocio el ID del socio
     * @param nombre el nombre del socio
     * @param apellidos los apellidos del socio
     * @param nif el NIF del socio
     * @param federacion la federación a la que está asociado el socio
     */
    public socioFederado(String idSocio, String nombre, String apellidos, String nif, Federacion federacion) {
        super(idSocio, nombre, apellidos);
        this.nif = nif;
        this.federacion = federacion;
    }

    /**
     * Obtiene el NIF del socio federado.
     *
     * @return el NIF del socio
     */
    public String getNif() {
        return nif;
    }

    /**
     * Establece un nuevo NIF para el socio federado.
     *
     * @param nif el nuevo NIF del socio
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * Obtiene la federación a la que está asociado el socio federado.
     *
     * @return la federación del socio
     */
    public Federacion getFederacion() {
        return federacion;
    }

    /**
     * Establece una nueva federación para el socio federado.
     *
     * @param federacion la nueva federación del socio
     */
    public void setFederacion(Federacion federacion) {
        this.federacion = federacion;
    }

    /**
     * Calcula la cuota mensual total del socio federado. Este tipo de socio tiene un descuento del 5% en la cuota mensual.
     *
     * @return la cuota mensual total con descuento aplicado
     */
    public double getCuotaMensual() {
        return cuotaMensual * 0.95;
    }

    /**
     * Devuelve una representación en cadena del socio federado, incluyendo su NIF, la federación a la que pertenece
     * y la cuota mensual total.
     *
     * @return una cadena con los detalles del socio federado
     */
    @Override
    public String toString() {
        return super.toString() +
                "NIF: " + this.nif + "\n" +
                "Federado en: " + this.federacion.getNombre() + "\n" +
                "Cuota mensual total: " + getCuotaMensual() + "\n";
    }
}
