package org.thejavengers.vista;

import javafx.beans.property.*;
import org.thejavengers.modelo.Inscripcion;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;

public class InscripcionViewModel {

    private final SimpleIntegerProperty idInscripcion;
    private final SimpleStringProperty nombreSocio; // Nombre del socio
    private final SimpleStringProperty descripcionExcursion; // Descripción de la excursión asociada
    private final SimpleStringProperty fechaInscripcion; // Fecha de la inscripción

    private final Inscripcion inscripcionOriginal; // Referencia al modelo original

    public InscripcionViewModel(Inscripcion inscripcion) {
        this.inscripcionOriginal = inscripcion;
        this.idInscripcion = new SimpleIntegerProperty(inscripcion.getIdInscripcion());
        this.nombreSocio = new SimpleStringProperty(inscripcion.getSocio().getNombre());
        this.descripcionExcursion = new SimpleStringProperty(inscripcion.getExcursion().getDescripcion());

        // Formatear la fecha de inscripción
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.fechaInscripcion = new SimpleStringProperty(inscripcion.getFechaInscripcion().format(formatter));
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
