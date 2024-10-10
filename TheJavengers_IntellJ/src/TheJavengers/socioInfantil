package TheJavengers;

/**
 * La clase {@code socioInfantil} representa a un socio infantil, que está asociado a un tutor (otro socio).
 * Hereda de la clase {@code Socio} e incluye un atributo adicional para el ID del socio tutor.
 */
public class socioInfantil extends Socio {
    
    private String idSocioTutor;

    /**
     * Constructor para crear un socio infantil con ID de socio, nombre, apellidos y el ID del socio tutor.
     *
     * @param idSocio el ID del socio infantil
     * @param nombre el nombre del socio infantil
     * @param apellidos los apellidos del socio infantil
     * @param idSocioTutor el ID del socio tutor (padre o tutor legal)
     */
    public socioInfantil(String idSocio, String nombre, String apellidos, String idSocioTutor) {
        super(idSocio, nombre, apellidos);
        this.idSocioTutor = idSocioTutor;
    }

    /**
     * Obtiene el ID del socio tutor del socio infantil.
     *
     * @return el ID del socio tutor
     */
    public String getIdSocioTutor() {
        return idSocioTutor;
    }

    /**
     * Calcula la cuota mensual total del socio infantil, aplicando un descuento del 50%.
     *
     * @return la cuota mensual total con el descuento aplicado
     */
    public double getCuotaMensualTotal() {
        return cuotaMensual * 0.50;
    }

    /**
     * Devuelve una representación en cadena del socio infantil, incluyendo el ID del socio tutor y la cuota mensual total.
     *
     * @return una cadena con los detalles del socio infantil
     */
    @Override
    public String toString() {
        return super.toString() +
                "Número de socio del tutor: " + this.idSocioTutor + "\n" +
                "Cuota mensual total: " + getCuotaMensualTotal() + "\n";
    }
}
