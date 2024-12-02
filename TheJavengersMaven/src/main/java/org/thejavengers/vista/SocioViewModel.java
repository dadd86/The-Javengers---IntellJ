package org.thejavengers.vista;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.thejavengers.modelo.*;

public class SocioViewModel {

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty nombre;
    private final SimpleStringProperty apellidos;
    private final SimpleStringProperty tipo;

    private final SimpleStringProperty seguro; // Propiedad específica de SocioEstandar
    private final SimpleStringProperty federacion; // Propiedad específica de SocioFederado
    private final SimpleIntegerProperty tutorId; // Propiedad específica de SocioInfantil

    private final Socio socioOriginal; // Referencia al objeto original

    public SocioViewModel(Socio socio) {
        this.socioOriginal = socio;
        this.id = new SimpleIntegerProperty(socio.getIdSocio());
        this.nombre = new SimpleStringProperty(socio.getNombre());
        this.apellidos = new SimpleStringProperty(socio.getApellidos());
        this.tipo = new SimpleStringProperty(socio.getClass().getSimpleName());

        // Propiedades específicas de las subclases
        if (socio instanceof SocioEstandar estandar) {
            this.seguro = new SimpleStringProperty(estandar.getSeguro().toString());
        } else {
            this.seguro = new SimpleStringProperty("N/A");
        }

        if (socio instanceof SocioFederado federado) {
            this.federacion = new SimpleStringProperty(federado.getFederacion().getNombre());
        } else {
            this.federacion = new SimpleStringProperty("N/A");
        }

        if (socio instanceof SocioInfantil infantil) {
            this.tutorId = new SimpleIntegerProperty(infantil.getIdSocioTutor());
        } else {
            this.tutorId = new SimpleIntegerProperty(-1);
        }
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public String getNombre() {
        return nombre.get();
    }

    public SimpleStringProperty nombreProperty() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos.get();
    }

    public SimpleStringProperty apellidosProperty() {
        return apellidos;
    }

    public String getTipo() {
        return tipo.get();
    }

    public SimpleStringProperty tipoProperty() {
        return tipo;
    }

    public String getSeguro() {
        return seguro.get();
    }

    public SimpleStringProperty seguroProperty() {
        return seguro;
    }

    public String getFederacion() {
        return federacion.get();
    }

    public SimpleStringProperty federacionProperty() {
        return federacion;
    }

    public int getTutorId() {
        return tutorId.get();
    }

    public SimpleIntegerProperty tutorIdProperty() {
        return tutorId;
    }

    public Socio getSocioOriginal() {
        return socioOriginal;
    }
}

