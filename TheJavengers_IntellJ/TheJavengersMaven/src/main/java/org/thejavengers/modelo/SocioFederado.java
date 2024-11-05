package org.thejavengers.modelo;

/**
 * Clase que representa a un socio federado.
 * Hereda de la clase Socio y añade un recargo a la cuota mensual por los beneficios adicionales.
 */
public class SocioFederado extends Socio {
    //Atributos

    private String nif;
    private Federacion federacion;

    //Descuento en cuota para socios federados.
    private static final float DESCUENTO_CUOTA_FEDERADO = 0.05f; //Descuento del 5% en cuota mensual

    //Descuento en excursiones para socios federados.
    private static final float DESCUENTO_EXCURSIONES_FEDERADO = 0.1f; //Descuento del 10% en cuota excursiones

    //Constructor

    /**
     * Constructor para inicializar un objeto de tipo SocioFederado.
     *
     * @param idSocio   Identificador único del socio.
     * @param nombre    Nombre del socio.
     * @param apellidos Apellidos del socio.
     * @param nif       NIF del socio.
     * @param federacion Federación a la que pertenece el socio.
     */
    public SocioFederado(String idSocio, String nombre, String apellidos, String nif, Federacion federacion) {
        super(idSocio, nombre, apellidos);
        if (federacion == null) {
            //throw new IllegalArgumentException("La federación no puede ser nula");
        }

        this.nif = nif;
        this.federacion = federacion;
    }

    //Getters

    /**
     * Obtiene el NIF del socio federado.
     *
     * @return El NIF del socio.
     */
    public String getNif() {return nif;}

    /**
     * Obtiene los datos de la federación a la que pertenece el socio.
     *
     * @return Datos de la federación del socio.
     */
    public Federacion getFederacion() {return federacion;}

    //Setters

    /**
     * Establece un nuevo NIF para el socio.
     *
     * @param nif El NIF asociado al socio.
     */
    public void setNif(String nif) {this.nif = nif;}

    /**
     * Establece una nueva federación para el socio federado.
     *
     * @param federacion La nueva federación. No puede ser nula.
     * @throws IllegalArgumentException si la federación es nula.
     */
    public void setFederacion(Federacion federacion) {
        if (federacion == null) {
            throw new IllegalArgumentException("La federación no puede ser nula");
        }
        this.federacion = federacion;
    }

    //Métodos

    /**
     * Calcula la cuota mensual del socio federado.
     * En este caso, la cuota mensual es la cuota fija con un descuento.
     *
     * @return La cuota mensual del socio federado.
     */
    @Override
    public float calcularCuotaMensual() {
        return CUOTA_MENSUAL * (1 - DESCUENTO_CUOTA_FEDERADO); //Aplica el descuento del 5%
    }

    //REPASAR ESTE METODO

    /**
     * Calcula la cuota por excursión del socio federado.
     * En este caso, la cuota es la cuota fija para las excursiones con un descuento.
     *
     * @return La cuota para excursiones del socio federado.
     */
    @Override
    public float calcularPrecioExcursion(Excursion excursion) {
        return excursion.getPrecio() * (1 - DESCUENTO_EXCURSIONES_FEDERADO);
    }



    @Override
    public String toString() {
        return "Socio Federado: " + super.toString() +
                ", Federacion: " + federacion +
                ", Descuento cuota mensual: " + DESCUENTO_CUOTA_FEDERADO +
                ", Descuento cuota excursiones: " + DESCUENTO_EXCURSIONES_FEDERADO;
    }
}
