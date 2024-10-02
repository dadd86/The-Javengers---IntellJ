package TheJavengers;

import java.time.LocalDate;

public class Excursion {
    private String codigo; // Código alfanumérico
    private String descripcion;
    private LocalDate fecha;
    private int numeroDias;
    private double precioInscripcion;

    // Constructor
    public Excursion(String codigo, String descripcion, LocalDate fecha, int numeroDias, double precioInscripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.numeroDias = numeroDias;
        this.precioInscripcion = precioInscripcion;
    }

    // Getters y setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public double getPrecioInscripcion() {
        return precioInscripcion;
    }

    public void setPrecioInscripcion(double precioInscripcion) {
        this.precioInscripcion = precioInscripcion;
    }

    // Método para calcular el costo total de la excursión (si fuera necesario, por ejemplo, si depende del número de días)
    public double calcularCostoTotal() {
        return this.precioInscripcion; // En este caso, es simplemente el precio de inscripción
    }

    // Método toString para mostrar la información de la excursión
    @Override
    public String toString() {
        return "Excursión:\n" +
                "Código: " + codigo + "\n" +
                "Descripción: " + descripcion + "\n" +
                "Fecha: " + fecha + "\n" +
                "Número de días: " + numeroDias + "\n" +
                "Precio de inscripción: " + precioInscripcion + "€\n";
    }
}

