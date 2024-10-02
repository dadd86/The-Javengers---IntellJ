package TheJavengers;

import java.util.List;

public class Socio {
    private String idSocio;
    private String nombre;
    protected static double cuotaMensual = 10.0;

    public Socio(String idSocio, String nombre){
        this.idSocio = idSocio;
        this.nombre = nombre;
    }

    // Método para obtener la cuota mensual total
    public double getCuotaMensualTotal() {
        return cuotaMensual;
    }

    // Getters y setters
    public String getIdSocio() {
        return idSocio;
    }

    public String getNombre() {
        return nombre;
    }

    // Método estático para buscar un socio en una lista de socios
    public static Socio buscarSocio(List<Socio> listaSocios, String idSocio) {
        for (Socio socio : listaSocios) {
            if (socio.getIdSocio().equals(idSocio)) {
                return socio;
            }
        }
        return null; // Si no se encuentra, retorna null
    }

    @Override
    public String toString() {
        return "Socio:\n" +
                "Nº de socio: " + this.idSocio + "\n" +
                "Nombre: " + this.nombre + "\n";
    }
}


