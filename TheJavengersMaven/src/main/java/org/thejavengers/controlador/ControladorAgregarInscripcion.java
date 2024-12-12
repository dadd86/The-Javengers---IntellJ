package org.thejavengers.controlador;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.thejavengers.DAO.ExcursionDAO;
import org.thejavengers.DAO.ExcursionDAOImpl;
import org.thejavengers.DAO.InscripcionDAO;
import org.thejavengers.DAO.InscripcionDAOImpl;
import org.thejavengers.DAO.SocioDAO;
import org.thejavengers.DAO.SocioDAOImpl;
import org.thejavengers.modelo.Excursion;
import org.thejavengers.modelo.Inscripcion;
import org.thejavengers.modelo.Socio;
import org.thejavengers.vista.gestionMenuPrincipal.SceneManager;

import java.time.LocalDate;
import java.io.IOException;
import java.util.List;

public class ControladorAgregarInscripcion {

    @FXML
    private TextField idInscripcion;

    @FXML
    private TextField nombreSocio;

    @FXML
    private TextField apellidosSocio;

    @FXML
    private ComboBox<String> descripcionExcursion;

    @FXML
    private TextField estadoInscripcion;

    @FXML
    private ComboBox<String> fechaInscripcion;

    @FXML
    private Button botonGuardar;

    @FXML
    private Button botonCancelar;

    private final InscripcionDAO inscripcionDAO;
    private final SocioDAO socioDAO;
    private final ExcursionDAO excursionDAO;
    private SceneManager sceneManager; // Añadir referencia al SceneManager

    public ControladorAgregarInscripcion() {
        this.inscripcionDAO = new InscripcionDAOImpl();
        this.socioDAO = new SocioDAOImpl();
        this.excursionDAO = new ExcursionDAOImpl();
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @FXML
    public void initialize() {
        botonGuardar.setOnAction(event -> guardarInscripcion());
        botonCancelar.setOnAction(event -> volverAGestionInscripciones());
    }

    /**
     * Guarda la inscripción en la base de datos.
     */
    private void guardarInscripcion() {
        try {
            // Obtener los datos del socio desde los campos de texto
            String nombre = nombreSocio.getText();
            String apellidos = apellidosSocio.getText();

            // Obtener todos los socios
            List<Socio> socios = socioDAO.findAll();

            // Buscar el socio que coincida con el nombre y los apellidos
            Socio socioSeleccionado = null;
            for (Socio socio : socios) {
                if (socio.getNombre().equalsIgnoreCase(nombre) && socio.getApellidos().equalsIgnoreCase(apellidos)) {
                    socioSeleccionado = socio;
                    break; // Salir del bucle cuando encontremos el socio
                }
            }

            // Obtener todos las excursiones
            List<Excursion> excursiones = excursionDAO.findAll();

            // Obtener la descripción seleccionada en el ComboBox
            String descripcionExcursionSeleccionada = descripcionExcursion.getSelectionModel().getSelectedItem();

            // Buscar la excursión que coincida con la descripción
            Excursion excursionSeleccionada = null;
            for (Excursion excursion : excursiones) {
                if (excursion.getDescripcion().equalsIgnoreCase(descripcionExcursionSeleccionada)) {
                    excursionSeleccionada = excursion;
                    break; // Salir del bucle cuando encontremos la excursión
                }
            }

            // Validar que se haya seleccionado un socio y una excursión
            if (socioSeleccionado == null) {
                mostrarAlerta("Error de Validación", "No se encontró un socio con ese nombre y apellidos.");
                return;
            }

            if (excursionSeleccionada == null) {
                mostrarAlerta("Error de Validación", "No se encontró una excursión con esa descripción.");
                return;
            }

            // Crear y guardar la inscripción
            Inscripcion inscripcion = new Inscripcion(0, socioSeleccionado, excursionSeleccionada, LocalDate.now());
            inscripcionDAO.save(inscripcion);

            mostrarAlerta("Éxito", "La inscripción se guardó correctamente.");

            // Volver a la vista de gestión de inscripciones
            volverAGestionInscripciones();
        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error al guardar la inscripción.");
        }
    }

    /**
     * Vuelve a la vista de gestión de inscripciones.
     */
    @FXML
    public void volverAGestionInscripciones() {
        if (sceneManager != null) {
            try {
                sceneManager.cambiarVista("/vistas/InscripcionesVista.fxml", "Gestión de Inscripciones");
            } catch (Exception e) {
                mostrarAlerta("Error", "Ocurrió un error al volver a la gestión de inscripciones.");
            }
        } else {
            mostrarAlerta("Error", "SceneManager no configurado.");
        }
    }

    /**
     * Muestra una alerta al usuario.
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}