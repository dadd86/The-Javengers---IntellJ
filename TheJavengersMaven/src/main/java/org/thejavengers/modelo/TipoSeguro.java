package org.thejavengers.modelo;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
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

    public static TipoSeguro fromString(String tipoSeguro) {
        return TipoSeguro.valueOf(tipoSeguro.toUpperCase());
    }

    @Override
    public String toString() {
        return this.name();
    }

    @Converter(autoApply = true)
    public class TipoSeguroConverter implements AttributeConverter<TipoSeguro, String> {

        @Override
        public String convertToDatabaseColumn(TipoSeguro tipoSeguro) {
            if (tipoSeguro == null) {
                return null;
            }
            return tipoSeguro.name();
        }

        @Override
        public TipoSeguro convertToEntityAttribute(String dbData) {
            if (dbData == null || dbData.isEmpty()) {
                return null;
            }
            return TipoSeguro.valueOf(dbData);
        }
    }


}
