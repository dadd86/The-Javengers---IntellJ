package TheJavengers.modelo;

/**
 * Clase que representa un Socio Infantil.
 * Incluye el identificador del socio tutor.
 */
public class SocioInfantil extends Socio {

    private String idSocioTutor;
    private static final float DESCUENTO_CUOTA = 0.50f; // 50% de descuento

    /**
     * Constructor para crear un socio infantil.
     *
     * @param idSocio       Identificador del socio.
     * @param nombre        Nombre del socio.
     * @param apellidos     Apellidos del socio.
     * @param idSocioTutor  Identificador del socio tutor.
     */
    public SocioInfantil(String idSocio, String nombre, String apellidos, String idSocioTutor) {
        super(idSocio, nombre, apellidos);
        this.idSocioTutor = idSocioTutor;
    }

    // Getter y Setter

    /**
     * Obtiene el identificador del socio tutor.
     *
     * @return El identificador del tutor.
     */
    public String getIdSocioTutor() {
        return idSocioTutor;
    }

    /**
     * Establece el identificador del socio tutor.
     *
     * @param idSocioTutor El nuevo identificador.
     */
    public void setIdSocioTutor(String idSocioTutor) {
        this.idSocioTutor = idSocioTutor;
    }

    // Implementación de métodos abstractos

    /**
     * Calcula la cuota mensual del socio infantil con el descuento correspondiente.
     *
     * @return La cuota mensual con descuento.
     */
    @Override
    public float calcularCuotaMensual() {
        return cuotaMensual * (1 - DESCUENTO_CUOTA);
    }

    /**
     * Calcula el precio de una excursión para el socio infantil.
     * No aplica descuentos adicionales.
     *
     * @param excursion La excursión a calcular.
     * @return El precio de la excursión.
     */
    @Override
    public float calcularPrecioExcursion(Excursion excursion) {
        return excursion.getPrecio();
    }

    @Override
    public String toString() {
        return super.toString() + ", SocioInfantil{" +
                "idSocioTutor='" + idSocioTutor + '\'' +
                '}';
    }
}



