package org.thejavengers.modelo;

public enum TipoSeguro {
    //Atributos

    BASICO(5.0f), // Seguro básico con un costo de 5€
    COMPLETO(15.0f); //Seguro completo con un costo de 15€

    /** Cuota del seguro para el socio estándar dependiendo de su seguro. */
    private final float costoSeguro;

    //Constructor

    /**
     * Constructor de la enumeración TipoSeguro.
     *
     * @param costoSeguro El costo del tipo de seguro.
     */
    TipoSeguro(float costoSeguro) {
        this.costoSeguro = costoSeguro;
    }

    //Getters

    /**
     * Obtiene el costo del seguro.
     *
     * @return El costo del seguro.
     */
    public float getCostoSeguro() {
        return costoSeguro;
    }
}
