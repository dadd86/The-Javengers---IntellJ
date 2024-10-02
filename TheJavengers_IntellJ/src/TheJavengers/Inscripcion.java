package TheJavengers;

import java.time.LocalDate;

public class Inscripcion {
    private int numeroInscripcion;
    private Socio socio;
    private Excursion excursion;
    private LocalDate fechaInscripcion;

    // Constructor
    public Inscripcion(int numeroInscripcion, Socio socio, Excursion excursion, LocalDate fechaInscripcion) {
        this.numeroInscripcion = numeroInscripcion;
        this.socio = socio;
        this.excursion = excursion;
        this.fechaInscripcion = fechaInscripcion;
    }

    // Getters y setters
    public int getNumeroInscripcion() {
        return numeroInscripcion;
    }

    public void setNumeroInscripcion(int numeroInscripcion) {
        this.numeroInscripcion = numeroInscripcion;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public Excursion getExcursion() {
        return excursion;
    }

    public void setExcursion(Excursion excursion) {
        this.excursion = excursion;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    // Método para cancelar inscripción
    public boolean cancelarInscripcion() {
        LocalDate fechaExcursion = excursion.getFecha();
        return fechaInscripcion.isBefore(fechaExcursion);  // Solo se puede cancelar si la fecha de inscripción es anterior a la fecha de la excursión
    }

    // Método para mostrar una inscripción
    public void mostrarInscripcion() {
        System.out.println(this);
        System.out.println("---------------------------");
    }

    // Método toString para mostrar la información de la inscripción
    @Override
    public String toString() {
        return "Número de inscripción: " + numeroInscripcion + "\n" +
                "Socio: " + socio.getNombre() + " (ID: " + socio.getIdSocio() + ")\n" +
                "Excursión: " + excursion.getDescripcion() + "\n" +
                "Fecha de inscripción: " + fechaInscripcion;
    }
}

