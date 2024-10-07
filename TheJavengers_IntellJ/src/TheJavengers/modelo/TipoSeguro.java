package TheJavengers.modelo;

/**
 * Enumeración que representa los tipos de seguro disponibles.
 * Cada tipo de seguro tiene un precio asociado.
 */
public enum TipoSeguro {

    BASICO(5.0f),
    COMPLETO(10.0f);

    private final float precio;

    /**
     * Constructor para asignar el precio a cada tipo de seguro.
     *
     * @param precio Precio del seguro.
     */
    TipoSeguro(float precio) {
        this.precio = precio;
    }

    /**
     * Obtiene el precio del seguro.
     *
     * @return El precio del seguro.
     */
    public float getPrecio() {
        return precio;
    }
}
