package TheJavengers;

import java.util.List;

public class Socio {
    private String idSocio;
    private String nombre;
    private String apellidos;
    protected static float cuotaMensual = 10.0F;

    public Socio(String idSocio, String nombre, String apellido) {
        this.idSocio = idSocio;
        this.nombre = nombre;
        this.apellidos = apellido;
    }

    // Getters y setters
    public String getidsocio() {
        return idSocio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setIdSocio(String idSocio) {
        this.idSocio = idSocio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {this.apellidos = apellidos;}

    // Método estático para buscar un socio en una lista de socios
    public static Socio buscarSocio(List<Socio> listaSocios, String idSocio) {
        for (Socio socio : listaSocios) {
            if (socio.getidsocio().equals(idSocio)) {
                return socio;
            }
        }
        return null; // Si no se encuentra, retorna null
    }

    // Método para obtener la cuota mensual total
    public double getCuotaMensualTotal() {
        return cuotaMensual;
    }

    @Override
    public String toString() {
        return "Socio:\n" +
                "Nº de socio: " + this.idSocio + "\n" +
                "Nombre: " + this.nombre + "\n" +
                "Apellidos: " + this.apellidos + "\n";
    }
}


