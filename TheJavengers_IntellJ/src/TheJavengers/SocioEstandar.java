package TheJavengers;
/**
 * Clase que representa a un socio estándar.
 * Hereda de la clase Socio y define la cuota mensual para este tipo de socio,
 * además de incluir un seguro que puede ser básico o completo.
 */
public class SocioEstandar extends Socio {
    //Atributos

    private String nif;
    private TipoSeguro seguro;

   //Constructor

    /**
     * Constructor para inicializar un objeto de tipo SocioEstandar.
     *
     * @param idSocio   Identificador único del socio.
     * @param nombre    Nombre del socio.
     * @param apellidos Apellidos del socio.
     * @param nif       NIF del socio, utilizado para el seguro.
     * @param seguro    El tipo de seguro seleccionado por el socio.
     */
   public SocioEstandar(String idSocio, String nombre, String apellidos, String nif, TipoSeguro seguro) {
       super(idSocio, nombre, apellidos);
       this.nif = nif;
       this.seguro = seguro;
   }

   //Getters

    /**
     * Obtiene el NIF del socio para el seguro.
     *
     * @return El NIF del asegurado.
     */
    public String getNif() {return nif;}

    /**
     * Obtiene el tipo de seguro seleccionado por el socio.
     *
     * @return El tipo de seguro.
     */
    public TipoSeguro getSeguro() {return seguro;}

    //Setters

    /**
     * Establece un nuevo NIF para el socio.
     *
     * @param nif El nuevo NIF asociado al socio.
     */
    public void setNif(String nif) {this.nif = nif;}

    /**
     * Establece un nuevo tipo de seguro para el socio.
     *
     * @param seguro El nuevo tipo de seguro a establecer.
     */
    public void setSeguro(TipoSeguro seguro) {this.seguro = seguro;}

    //Métodos

    /**
     * Calcula la cuota mensual del socio estándar.
     * En este caso, la cuota mensual es la cuota fija definida más el costo del seguro seleccionado.
     *
     * @return La cuota mensual del socio estándar.
     */
    @Override
    public float calcularCuotaMensual() {
        return CUOTA_MENSUAL + seguro.getCostoSeguro();
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
        return excursion.getPrecio() + seguro.getCostoSeguro();
    }

    @Override
    public String toString() {
        return "Socio Estandar: " + super.toString() +
                ", Tipo de Seguro: " + seguro +
                ", Costo del Seguro: " + seguro.getCostoSeguro();
    }
}
