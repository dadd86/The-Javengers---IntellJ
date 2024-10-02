package TheJavengers;

/**
 * Clase que representa a un socio infantil del centro excursionista.
 * Los socios infantiles tienen un 50% de descuento en la cuota mensual, pero no tienen descuento en las excursiones.
 * Están cubiertos por el seguro de su padre o madre.
 */
class SocioInfantil extends Socio {

    // Atributos de la clase
    private int numeroSocioPadre;

    /**
     * Constructor que crea un nuevo socio infantil con un número de socio, nombre y el número de socio del padre o madre.
     *
     * @param numeroSocio El número único del socio infantil.
     * @param nombre El nombre del socio infantil.
     * @param numeroSocioPadre El número de socio del padre o madre.
     */
    public SocioInfantil(int numeroSocio, String nombre, int numeroSocioPadre) {
        super(numeroSocio, nombre);
        this.numeroSocioPadre = numeroSocioPadre;
    }

    /**
     * Obtiene el número de socio del padre o madre del socio infantil.
     *
     * @return El número de socio del padre o madre.
     */
    public int getNumeroSocioPadre() {
        return numeroSocioPadre;
    }

    /**
     * Establece el número de socio del padre o madre del socio infantil.
     *
     * @param numeroSocioPadre El nuevo número de socio del padre o madre.
     */
    public void setNumeroSocioPadre(int numeroSocioPadre) {
        this.numeroSocioPadre = numeroSocioPadre;
    }

    /**
     * Calcula el descuento aplicado en una excursión para un socio infantil.
     * Los socios infantiles no tienen descuento en las excursiones.
     *
     * @param precio El precio base de la excursión.
     * @return El mismo precio, ya que no hay descuento en excursiones para socios infantiles.
     */
    @Override
    public double calcularDescuentoExcursion(double precio) {
        return precio; // No hay descuento en excursiones para socios infantiles
    }

    /**
     * Calcula el descuento aplicado en la cuota mensual para un socio infantil.
     * Los socios infantiles reciben un 50% de descuento en la cuota mensual.
     *
     * @param cuota El precio base de la cuota mensual.
     * @return El precio con un 50% de descuento aplicado.
     */
    @Override
    public double calcularDescuentoCuota(double cuota) {
        return cuota * 0.50; // 50% de descuento en la cuota mensual
    }

    /**
     * Devuelve una representación en forma de cadena del socio infantil.
     *
     * @return Una cadena que describe al socio infantil, incluyendo el número de socio del padre o madre.
     */
    @Override
    public String toString() {
        return super.toString() + ", SocioInfantil{" +
                "numeroSocioPadre=" + numeroSocioPadre +
                '}';
    }
}
