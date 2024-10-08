package TheJavengers;

import java.time.LocalDate;

public class Excursion {
    private String idExcursion; // Código alfanumérico
    private String descripcion;
    private LocalDate fecha;
    private int numeroDias;
    private double precioExcursion;

    // Constructor
    public Excursion(String codigo, String descripcion, LocalDate fecha, int numeroDias, double precioExcursion) {
        this.idExcursion = codigo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.numeroDias = numeroDias;
        this.precioExcursion = precioExcursion;
    }

    // Getters y setters
    public String getIdExcursion() {
        return idExcursion;
    }

    public void setIdExcursion(String idExcursion) {
        this.idExcursion = idExcursion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getNumeroDias() {
        return numeroDias;
    }

    public void setNumeroDias(int numeroDias) {
        this.numeroDias = numeroDias;
    }

    public double getPrecioExcursion() {
        return precioExcursion;
    }

    public void setPrecioExcursion(double precioExcursion) {
        this.precioExcursion = precioExcursion;
    }

    // Método para calcular el costo total de la excursión
    public double calcularCostoTotal() {
        return this.precioExcursion;
    }

    // Método toString para mostrar la información de la excursión
    @Override
    public String toString() {
        return "Excursión:\n" +
                "Código: " + idExcursion + "\n" +
                "Descripción: " + descripcion + "\n" +
                "Fecha: " + fecha + "\n" +
                "Número de días: " + numeroDias + "\n" +
                "Precio de inscripción: " + precioExcursion + "€\n";
    }
}

