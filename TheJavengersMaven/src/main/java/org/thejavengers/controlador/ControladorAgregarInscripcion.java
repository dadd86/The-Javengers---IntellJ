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
    @FXML
    private ComboBox<Socio> comboBoxSocios;

    @FXML
    private ComboBox<Excursion> comboBoxExcursiones;

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
        cargarSocios();
        cargarExcursiones();
    }
    /**
     * Carga la lista de socios desde la base de datos al ComboBox.
     */
    private void cargarSocios() {
        try {
            List<Socio> socios = socioDAO.findAll();
            if (socios != null && !socios.isEmpty()) {
                comboBoxSocios.setItems(FXCollections.observableArrayList(socios));
            } else {
                mostrarAlerta("Información", "No hay socios disponibles para inscribir.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudieron cargar los socios.");
        }
    }

    /**
     * Carga la lista de excursiones desde la base de datos al ComboBox.
     */
    private void cargarExcursiones() {
        try {
            List<Excursion> excursiones = excursionDAO.findAll();
            if (excursiones != null && !excursiones.isEmpty()) {
                comboBoxExcursiones.setItems(FXCollections.observableArrayList(excursiones));
            } else {
                mostrarAlerta("Información", "No hay excursiones disponibles para inscribir.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudieron cargar las excursiones.");
        }
    }
    @FXML
    private void guardarInscripcion() {
        try {
            // Obtener los datos seleccionados en los ComboBox
            Socio socioSeleccionado = comboBoxSocios.getSelectionModel().getSelectedItem();
            Excursion excursionSeleccionada = comboBoxExcursiones.getSelectionModel().getSelectedItem();

            // Validar que se haya seleccionado un socio y una excursión
            if (socioSeleccionado == null) {
                mostrarAlerta("Error de Validación", "Debe seleccionar un socio.");
                return;
            }

            if (excursionSeleccionada == null) {
                mostrarAlerta("Error de Validación", "Debe seleccionar una excursión.");
                return;
            }

            // Crear y guardar la inscripción
            Inscripcion inscripcion = new Inscripcion(0, socioSeleccionado, excursionSeleccionada, LocalDate.now());
            inscripcionDAO.save(inscripcion);

            // Mostrar confirmación
            mostrarAlerta("Éxito", "La inscripción se guardó correctamente.");

            // Volver a la vista de gestión de inscripciones
            cancelar();
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
    @FXML
    private void cancelar() {
        if (sceneManager != null) {
            try {
                sceneManager.cambiarVista("/vistas/VistaInscripciones.fxml", "Gestión de Inscripciones");
            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta("Error", "No se pudo volver a la vista de gestión de inscripciones.");
            }
        } else {
            mostrarAlerta("Error", "El SceneManager no está configurado.");
        }
    }

}