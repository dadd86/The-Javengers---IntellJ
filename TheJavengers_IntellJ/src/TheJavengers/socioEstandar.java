package TheJavengers;

public class socioEstandar extends Socio {
    private String nif;
    private Seguro seguroContratado;

    public socioEstandar(String idSocio, String nombre, String apellidos, String nif, Seguro seguroContratado) {
        super(idSocio, nombre, apellidos);
        this.nif = nif;
        this.seguroContratado = seguroContratado;
    }

    // Método para obtener la cuota mensual total
    public double getCuotaMensualTotal() {
        return cuotaMensual + seguroContratado.getPrecio(); // Asumiendo que Seguro tiene un método getPrecio()
    }

    // Getters y setters
    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public Seguro getSeguroContratado() {
        return seguroContratado;
    }

    public void setSeguroContratado(Seguro seguroContratado) {
        this.seguroContratado = seguroContratado;
    }

    @Override
    public String toString() {
        return super.toString() +
                "NIF: " + this.nif + "\n" +
                "Seguro contratado: " + this.seguroContratado.getTipo() + "\n" +
                "Cuota mensual total: " + getCuotaMensualTotal() + "\n";
    }

}


