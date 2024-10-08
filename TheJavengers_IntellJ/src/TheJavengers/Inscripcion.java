package TheJavengers;

import java.time.LocalDate;

public class Inscripcion {
    private int idInscripcion;
    private Socio socio;
    private Excursion excursion;
    private LocalDate fechaInscripcion;

    // Constructor
    public Inscripcion(int numeroInscripcion, Socio socio, Excursion excursion, LocalDate fechaInscripcion) {
        this.idInscripcion = numeroInscripcion;
        this.socio = socio;
        this.excursion = excursion;
        this.fechaInscripcion = fechaInscripcion;
    }

    // Getters y setters
    public int getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(int idInscripcion) {
        this.idInscripcion = idInscripcion;
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
        return fechaInscripcion.isBefore(fechaExcursion);
    }

    // Método para mostrar una inscripción
    public void mostrarInscripcion() {
        System.out.println(this);
        System.out.println("---------------------------");
    }

    // Método toString para mostrar la información de la inscripción
    @Override
    public String toString() {
        return "Número de inscripción: " + idInscripcion + "\n" +
                "Socio: " + socio.getNombre() + " (ID: " + socio.getidsocio() + ")\n" +
                "Excursión: " + excursion.getDescripcion() + "\n" +
                "Fecha de inscripción: " + fechaInscripcion;
    }
}

