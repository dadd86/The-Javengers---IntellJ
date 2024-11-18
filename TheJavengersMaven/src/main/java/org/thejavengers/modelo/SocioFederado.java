package org.thejavengers.modelo;

public class SocioFederado extends Socio {
    private String nif;
    private Federacion federacion;

    private static final float DESCUENTO_CUOTA_FEDERADO = 0.05f; //Descuento del 5% en cuota mensual
    private static final float DESCUENTO_EXCURSIONES_FEDERADO = 0.1f; //Descuento del 10% en excursiones

    public SocioFederado(int idSocio, String nombre, String apellidos, String nif, Federacion federacion) {
        super(idSocio, nombre, apellidos);
        if (federacion == null) {
            throw new IllegalArgumentException("La federación no puede ser nula");
        }
        this.nif = nif;
        this.federacion = federacion;
    }

    public String getNif() {
        return nif;
    }

    public Federacion getFederacion() {
        return federacion;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public void setFederacion(Federacion federacion) {
        if (federacion == null) {
            throw new IllegalArgumentException("La federación no puede ser nula");
        }
        this.federacion = federacion;
    }

    @Override
    public float calcularCuotaMensual() {
        return CUOTA_MENSUAL * (1 - DESCUENTO_CUOTA_FEDERADO); //Aplica el descuento del 5%
    }

    @Override
    public float calcularPrecioExcursion(Excursion excursion) {
        return excursion.getPrecio() * (1 - DESCUENTO_EXCURSIONES_FEDERADO); //Descuento en excursiones del 10%
    }

    @Override
    public String toString() {
        return "Socio Federado: " + super.toString() +
                ", Federacion: " + federacion +
                ", Descuento cuota mensual: " + DESCUENTO_CUOTA_FEDERADO +
                ", Descuento cuota excursiones: " + DESCUENTO_EXCURSIONES_FEDERADO;
    }
}
