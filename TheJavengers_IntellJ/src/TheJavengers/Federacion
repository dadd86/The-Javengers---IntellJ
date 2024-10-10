package TheJavengers;

import java.util.ArrayList;

public class Federacion {
    private String codigo;
    private String nombre;

    // Constructor
    public Federacion(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    // Getters y setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Método estático para buscar federación en una lista
    public static Federacion buscarFederacion(ArrayList<Federacion> listaFederaciones, String nombre) {
        for (Federacion federacion : listaFederaciones) {
            if (federacion.getNombre().equalsIgnoreCase(nombre)) {
                return federacion;
            }
        }
        return null;
    }

    // Método toString para mostrar la información de la federación
    @Override
    public String toString() {
        return "Federación:\n" +
                "Código: " + codigo + "\n" +
                "Nombre: " + nombre + "\n";
    }
}

