package TheJavengers;

public class socioInfantil extends Socio {
    private String idSocioTutor;

    public socioInfantil(String idSocio, String nombre, String apellidos, String numeroSocioPadre) {
        super(idSocio, nombre, apellidos);
        this.idSocioTutor = numeroSocioPadre;
    }

    // Getters y setters
    public String getIdSocioTutor() {
        return idSocioTutor;
    }

    public void setIdSocioTutor(String idSocioTutor) {
        this.idSocioTutor = idSocioTutor;
    }

    // Método para obtener la cuota mensual total
    public double getCuotaMensualTotal() {
        return cuotaMensual * 0.50; // Aplicar el 50% de descuento
    }

    @Override
    public String toString() {
        return super.toString() +
                "Número de socio del padre: " + this.idSocioTutor + "\n" +
                "Cuota mensual total: " + getCuotaMensualTotal() + "\n";
    }
}

