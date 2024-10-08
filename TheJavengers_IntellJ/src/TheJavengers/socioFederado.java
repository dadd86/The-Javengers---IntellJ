package TheJavengers;

public class socioFederado extends Socio {
    private String nif;
    private Federacion federacion;

    public socioFederado(String idSocio, String nombre, String apellidos, String nif, Federacion federacion) {
        super(idSocio, nombre, apellidos);
        this.nif = nif;
        this.federacion = federacion;
    }

    // Getters y setters
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
        this.federacion = federacion;
    }

    // Método para obtener la cuota mensual total
    public double getCuotaMensual() {
        return cuotaMensual * 0.95;
    }

    @Override
    public String toString() {
        return super.toString() +
                "NIF: " + this.nif + "\n" +
                "Federado en: " + this.federacion.getNombre() + "\n" +
                "Cuota mensual total: " + getCuotaMensual() + "\n";
    }
}
