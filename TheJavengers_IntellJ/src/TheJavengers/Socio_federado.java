package TheJavengers;

public class Socio_federado extends Socio {
    private String nif;
    private Federacion federacion;

    public Socio_federado(String idSocio, String nombre, String nif, Federacion federacion) {
        super(idSocio, nombre);
        this.nif = nif;
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
