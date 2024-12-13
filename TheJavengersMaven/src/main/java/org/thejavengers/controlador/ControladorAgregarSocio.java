package org.thejavengers.controlador;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.thejavengers.DAO.FederacionDAO;
import org.thejavengers.DAO.FederacionDAOImpl;
import org.thejavengers.DAO.SocioDAO;
import org.thejavengers.DAO.SocioDAOImpl;
import org.thejavengers.Excepciones.SceneManagerException;
import org.thejavengers.modelo.*;
import org.thejavengers.vista.gestionMenuPrincipal.SceneManager;

import java.io.IOException;
import java.util.List;

public class ControladorAgregarSocio {

    @FXML
    private TextField nombreField;

    @FXML
    private TextField apellidosField;

    @FXML
    private TextField nifField;

    @FXML
    private ChoiceBox<String> tipoSocioChoiceBox;

    @FXML
    private ChoiceBox<String> tipoSeguroChoiceBox;

    @FXML
    private ComboBox<Federacion> federacionComboBox;

    @FXML
    private TextField tutorIdField;

    @FXML
    private Button guardarButton;

    @FXML
    private Button cancelarButton;

    private final SocioDAO socioDAO;
    private final FederacionDAO federacionDAO;
    private SceneManager sceneManager; // Añadido para gestionar el cambio de escenas

    public ControladorAgregarSocio() {
        socioDAO = new SocioDAOImpl();
        federacionDAO = new FederacionDAOImpl();
    }
    public void setSceneManager(SceneManager sceneManager) { // Añadido para configurar el SceneManager
        this.sceneManager = sceneManager;
    }
    @FXML
    public void initialize() {

        tipoSocioChoiceBox.getItems().addAll("Estándar", "Federado", "Infantil");
        tipoSocioChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> ajustarCampos(newValue));

        tipoSeguroChoiceBox.getItems().addAll("Básico", "Completo");

        cargarFederaciones();

        guardarButton.setOnAction(event -> guardarSocio());
        cancelarButton.setOnAction(event -> volverAGestionSocios());
    }


    private void cargarFederaciones() {
        List<Federacion> federaciones = federacionDAO.findAll();
        federacionComboBox.setItems(FXCollections.observableArrayList(federaciones));
    }

    private void ajustarCampos(String tipoSocio) {
        tipoSeguroChoiceBox.setVisible("Estándar".equals(tipoSocio));
        federacionComboBox.setVisible("Federado".equals(tipoSocio));
        tutorIdField.setVisible("Infantil".equals(tipoSocio));
    }

    private void guardarSocio() {
        String nombre = nombreField.getText().trim();
        String apellidos = apellidosField.getText().trim();
        String tipoSocio = tipoSocioChoiceBox.getValue();

        if (nombre.isEmpty() || apellidos.isEmpty() || tipoSocio == null) {
            mostrarAlerta("Error de Validación", "Todos los campos obligatorios deben estar completos.");
            return;
        }

        try {
            Socio nuevoSocio = crearSocio(nombre, apellidos, tipoSocio);
            socioDAO.save(nuevoSocio);
            mostrarAlerta("Éxito", "Socio agregado correctamente.");
            volverAGestionSocios();
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al agregar socio: " + e.getMessage());
        }
    }

    private Socio crearSocio(String nombre, String apellidos, String tipoSocio) {
        switch (tipoSocio) {
            case "Estándar":
                String nif = nifField.getText().trim();
                String tipoSeguro = tipoSeguroChoiceBox.getValue();
                TipoSeguro seguro = "Básico".equals(tipoSeguro) ? TipoSeguro.BASICO : TipoSeguro.COMPLETO;
                return new SocioEstandar(0, nombre, apellidos, nif, seguro);

            case "Federado":
                Federacion federacionSeleccionada = federacionComboBox.getValue();
                if (federacionSeleccionada == null) {
                    throw new IllegalArgumentException("Debe seleccionar una federación.");
                }
                String nifFederado = nifField.getText().trim();
                return new SocioFederado(0, nombre, apellidos, nifFederado, federacionSeleccionada);

            case "Infantil":
                int tutorId = Integer.parseInt(tutorIdField.getText().trim());
                return new SocioInfantil(0, nombre, apellidos, tutorId);

            default:
                throw new IllegalArgumentException("Tipo de socio no válido.");
        }
    }

    @FXML
    public void volverAGestionSocios() {
        if (sceneManager != null) {
            try {
                // Cambiar a la vista de Gestión de Socios
                sceneManager.cambiarVista("/vistas/GestionSocios.fxml", "Gestión de Socios");
            } catch (SceneManagerException e) {
                e.printStackTrace();
                // Mostrar alerta si hay un error
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error al cargar la vista de Gestión de Socios.");
                alert.showAndWait();
            }
        } else {
            // Manejo si el SceneManager no está configurado
            Alert alert = new Alert(Alert.AlertType.ERROR, "SceneManager no configurado.");
            alert.showAndWait();
        }
    }


    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}