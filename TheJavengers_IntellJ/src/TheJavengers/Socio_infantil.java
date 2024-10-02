package TheJavengers;

public class Socio_infantil extends Socio {
    private String numeroSocioPadre;

    public Socio_infantil(String idSocio, String nombre, String numeroSocioPadre) {
        super(idSocio, nombre);
        this.numeroSocioPadre = numeroSocioPadre;
    }

    // Método para obtener la cuota mensual total
    public double getCuotaMensualTotal() {
        return cuotaMensual * 0.50; // Aplicar el 50% de descuento
    }

    @Override
    public String toString() {
        return super.toString() +
                "Número de socio del padre: " + this.numeroSocioPadre + "\n" +
                "Cuota mensual total: " + getCuotaMensualTotal() + "\n";
    }
}

