package org.thejavengers.vista.gestionExcursiones;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.thejavengers.modelo.*;
import java.time.LocalDate;

public class VistaExcursionesPorFechas {

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty descripcion;
    private final SimpleObjectProperty<LocalDate> fechaExcursion;
    private final SimpleIntegerProperty numeroDias;
    private final SimpleDoubleProperty precio;

    private final Excursion excursion;

    public VistaExcursionesPorFechas(Excursion excursion) {
        this.excursion = excursion;
        this.id = new SimpleIntegerProperty(excursion.getIdExcursion());
        this.descripcion = new SimpleStringProperty(excursion.getDescripcion());
        this.fechaExcursion = new SimpleObjectProperty<>(excursion.getFechaExcursion());
        this.numeroDias = new SimpleIntegerProperty(excursion.getNumeroDias());
        this.precio = new SimpleDoubleProperty(excursion.getPrecio());

    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public SimpleStringProperty descripcionProperty() {
        return descripcion;
    }

    public LocalDate getFechaExcursion() {
        return fechaExcursion.get();
    }

    public SimpleObjectProperty<LocalDate> fechaExcursionProperty() {
        return fechaExcursion;
    }

    public int getNumeroDias() {
        return numeroDias.get();
    }

    public SimpleIntegerProperty numeroDiasProperty() {
        return numeroDias;
    }

    public double getPrecio() {
        return precio.get();
    }

    public SimpleDoubleProperty precioProperty() {
        return precio;
    }

    public Excursion getExcursionOriginal() {
        return excursion;
    }

}
