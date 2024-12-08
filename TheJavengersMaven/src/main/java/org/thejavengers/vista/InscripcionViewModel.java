package org.thejavengers.vista;

import javafx.beans.property.*;
import org.thejavengers.modelo.Inscripcion;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;

public class InscripcionViewModel {

    private final SimpleIntegerProperty idInscripcion;
    private final SimpleStringProperty nombreSocio; // Nombre del socio
    private final SimpleStringProperty descripcionExcursion; // Descripción de la excursión asociada
    private final SimpleStringProperty estado; // Estado de la inscripción
    private final SimpleStringProperty fechaInscripcion; // Fecha de la inscripción

    private final Inscripcion inscripcionOriginal; // Referencia al modelo original

    public InscripcionViewModel(Inscripcion inscripcion) {
        this.inscripcionOriginal = inscripcion;
        this.idInscripcion = new SimpleIntegerProperty(inscripcion.getIdInscripcion());
        this.nombreSocio = new SimpleStringProperty(inscripcion.getSocio().getNombre());
        this.descripcionExcursion = new SimpleStringProperty(inscripcion.getExcursion().getDescripcion());
        this.estado = new SimpleStringProperty(calcularEstado(inscripcion));

        // Formatear la fecha de inscripción
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.fechaInscripcion = new SimpleStringProperty(inscripcion.getFechaInscripcion().format(formatter));
    }

    private String calcularEstado(Inscripcion inscripcion) {
        // Si faltan menos de 24 horas o ya pasó la fecha de la excursión, la inscripción está cerrada
        LocalDate fechaExcursion = inscripcion.getExcursion().getFechaExcursion();
        LocalDate fechaActual = LocalDate.now();

        if (fechaExcursion.isBefore(fechaActual)) {
            return "Expirada";
        } else if (fechaActual.plusDays(1).isAfter(fechaExcursion)) {
            return "Cerrada";
        } else {
            return "Activa";
        }
    }

    public int getIdInscripcion() {
        return idInscripcion.get();
    }

    public SimpleIntegerProperty idInscripcionProperty() {
        return idInscripcion;
    }

    public String getNombreSocio() {
        return nombreSocio.get();
    }

    public SimpleStringProperty nombreSocioProperty() {
        return nombreSocio;
    }

    public String getDescripcionExcursion() {
        return descripcionExcursion.get();
    }

    public SimpleStringProperty descripcionExcursionProperty() {
        return descripcionExcursion;
    }

    public String getEstado() {
        return estado.get();
    }

    public SimpleStringProperty estadoProperty() {
        return estado;
    }

    public String getFechaInscripcion() {
        return fechaInscripcion.get();
    }

    public SimpleStringProperty fechaInscripcionProperty() {
        return fechaInscripcion;
    }

    public Inscripcion getInscripcionOriginal() {
        return inscripcionOriginal;
    }
}
