package org.thejavengers.modelo;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

public enum TipoSeguro {
    BASICO(5.0f),     // Seguro básico con un costo de 5€
    COMPLETO(15.0f);  // Seguro completo con un costo de 15€

    private final float costoSeguro;

    TipoSeguro(float costoSeguro) {
        this.costoSeguro = costoSeguro;
    }

    public float getCostoSeguro() {
        return costoSeguro;
    }

    /**
     * Convierte una cadena en un TipoSeguro.
     *
     * @param tipoSeguro El nombre del tipo de seguro (case-insensitive).
     * @return El valor del enum correspondiente.
     */
    public static TipoSeguro fromString(String tipoSeguro) {
        return TipoSeguro.valueOf(tipoSeguro.toUpperCase());
    }

    /**
     * Obtiene el nombre del enum como cadena para almacenar en la base de datos.
     *
     * @return El nombre del enum.
     */
    @Override
    public String toString() {
        return this.name();
    }

    /**
     * Converter para usar TipoSeguro directamente en JPA/Hibernate.
     */
    @Converter(autoApply = true)
    public static class TipoSeguroConverter implements AttributeConverter<TipoSeguro, String> {

        @Override
        public String convertToDatabaseColumn(TipoSeguro tipoSeguro) {
            return tipoSeguro != null ? tipoSeguro.name() : null;
        }

        @Override
        public TipoSeguro convertToEntityAttribute(String tipoSeguro) {
            return tipoSeguro != null ? TipoSeguro.fromString(tipoSeguro) : null;
        }
    }
}
