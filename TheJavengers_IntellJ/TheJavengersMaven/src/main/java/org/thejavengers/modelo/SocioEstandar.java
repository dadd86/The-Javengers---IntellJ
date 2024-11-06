package org.thejavengers.modelo;

public class SocioEstandar extends Socio {
    private String nif;
    private TipoSeguro seguro;

    public SocioEstandar(int idSocio, String nombre, String apellidos, String nif, TipoSeguro seguro) {
        super(idSocio, nombre, apellidos);
        this.nif = nif;
        this.seguro = seguro;
    }

    public String getNif() {
        return nif;
    }

    public TipoSeguro getSeguro() {
        return seguro;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public void setSeguro(TipoSeguro seguro) {
        this.seguro = seguro;
    }

    @Override
    public float calcularCuotaMensual() {
        return CUOTA_MENSUAL + seguro.getCostoSeguro();
    }

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
