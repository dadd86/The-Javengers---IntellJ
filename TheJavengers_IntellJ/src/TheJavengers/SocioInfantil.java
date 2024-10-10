package TheJavengers;
/**
 * Clase que representa a un socio infantil.
 * Hereda de la clase Socio y define un descuento en la cuota mensual para este tipo de socio.
 */
public class SocioInfantil extends Socio {
    //Atributos

    private String idSocioTutor;
    private float descuentoCuota;

    //Descuento en cuota para socios infantiles.
    private static final float DESCUENTO_INFANTIL = 0.5f; // 50% de descuento

    //Constructor
    /**
     * Constructor para inicializar un objeto de tipo SocioInfantil.
     *
     * @param idSocio   Identificador único del socio.
     * @param nombre    Nombre del socio.
     * @param apellidos Apellidos del socio.
     * @param idSocioTutor Identificador único del socio estándar tutor del infantil.
     * @param descuentoCuota Descuento aplicado al socio infantil.
     */
    public SocioInfantil(String idSocio, String nombre, String apellidos, String idSocioTutor, float descuentoCuota) {
        super(idSocio, nombre, apellidos);
        this.idSocioTutor = idSocioTutor;
        this.descuentoCuota = descuentoCuota;
    }

    //Getters

    /**
     * Obtiene el identificador único del socio estándar responsable del infantil.
     *
     * @return El identificador del socio estándar.
     */
    public String getIdSocioTutor() {return idSocioTutor;}

    /**
     * Obtiene el descuento asociado al socio infantil.
     *
     * @return El descuento a aplicar.
     */
    public float getDescuentoCuota() {return descuentoCuota;}

    //Setters
    /**
     * Establece el identificador asociado al socio estándar.
     *
     * @param idSocioTutor El identificador asociado al socio.
     */
    public void setIdSocioTutor(String idSocioTutor) {this.idSocioTutor = idSocioTutor;}

    /**
     * Establece el descuento que se aplica en la cuota del socio infantil.
     *
     * @param descuentoCuota El descuento asociado al socio.
     */
    public void setDescuentoCuota(float descuentoCuota) {this.descuentoCuota = descuentoCuota;}

    //Métodos

    /**
     * Calcula la cuota mensual del socio infantil.
     * En este caso, la cuota mensual es la cuota fija con un descuento.
     *
     * @return La cuota mensual del socio infantil.
     */
    @Override
    public float calcularCuotaMensual() {
        return CUOTA_MENSUAL * (1 - DESCUENTO_INFANTIL);// Aplica el descuento del 50%
    }

    //ToString

    @Override
    public String toString() {
        return "Socio Infantil: " + super.toString();
    }

}
