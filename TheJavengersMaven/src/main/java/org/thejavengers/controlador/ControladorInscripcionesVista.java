package org.thejavengers.controlador;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.thejavengers.DAO.InscripcionDAO;
import org.thejavengers.DAO.InscripcionDAOImpl;
import org.thejavengers.modelo.Inscripcion;
import org.thejavengers.vista.gestionMenuPrincipal.SceneManager;

import java.time.LocalDate;
import java.util.List;

public class ControladorInscripcionesVista {

    @FXML
    private TableView<Inscripcion> tablaInscripciones;

    @FXML
    private TableColumn<Inscripcion, Integer> idInscripcion;

    @FXML
    private TableColumn<Inscripcion, String> nombreSocio;

    @FXML
    private TableColumn<Inscripcion, Integer> idSocio;

    @FXML
    private TableColumn<Inscripcion, String> descripcionExcursion;

    @FXML
    private TableColumn<Inscripcion, String> fechaInscripcion;

    @FXML
    private TextField filtroSocioField;

    @FXML
    private DatePicker filtroFechaInicio;

    @FXML
    private DatePicker filtroFechaFin;

    private final InscripcionDAO inscripcionDAO;
    private ObservableList<Inscripcion> inscripcionesObservableList;
    private SceneManager sceneManager;

    public ControladorInscripcionesVista() {
        this.inscripcionDAO = new InscripcionDAOImpl();
        this.inscripcionesObservableList = FXCollections.observableArrayList();
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @FXML
    public void initialize() {
        // Configurar columnas de la tabla
        idInscripcion.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getIdInscripcion()).asObject());
        idSocio.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getSocio().getIdSocio()).asObject());
        nombreSocio.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSocio().getNombre()));
        descripcionExcursion.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getExcursion().getDescripcion()));
        fechaInscripcion.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFechaInscripcion().toString()));

        cargarInscripciones(null, null, null);
    }

    private void cargarInscripciones(Integer idSocio, LocalDate fechaInicio, LocalDate fechaFin) {
        List<Inscripcion> inscripciones = inscripcionDAO.findAll(idSocio, fechaInicio, fechaFin);
        inscripcionesObservableList.setAll(inscripciones);
        tablaInscripciones.setItems(inscripcionesObservableList);
    }

    @FXML
    private void aplicarFiltro() {
        Integer idSocio = null;
        if (!filtroSocioField.getText().trim().isEmpty()) {
            try {
                idSocio = Integer.parseInt(filtroSocioField.getText());
            } catch (NumberFormatException e) {
                mostrarAlerta("Error", "El ID del socio debe ser un número.");
                return;
            }
        }

        LocalDate fechaInicio = filtroFechaInicio.getValue();
        LocalDate fechaFin = filtroFechaFin.getValue();

        cargarInscripciones(idSocio, fechaInicio, fechaFin);

        mostrarAlerta("Éxito", "Filtro aplicado correctamente.");
    }

    @FXML
    private void irAMenuAgregarInscripcion() {
        if (sceneManager != null) {
            try {
                sceneManager.cambiarVista("/vistas/AgregarInscripcion.fxml", "Agregar Inscripción");
            } catch (Exception e) {
                mostrarAlerta("Error", "No se pudo abrir la vista de agregar inscripción.");
            }
        }
    }

    @FXML
    private void eliminarInscripcion() {
        Inscripcion seleccionada = tablaInscripciones.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            inscripcionDAO.deleteById(seleccionada.getIdInscripcion());
            inscripcionesObservableList.remove(seleccionada);
            mostrarAlerta("Éxito", "Inscripción eliminada.");
        } else {
            mostrarAlerta("Advertencia", "Seleccione una inscripción para eliminar.");
        }
    }

    @FXML
    private void volverAlMenu() {
        if (sceneManager != null) {
            try {
                sceneManager.cambiarVista("/vistas/application.fxml", "Menú Principal");
            } catch (Exception e) {
                mostrarAlerta("Error", "No se pudo volver al menú principal.");
            }
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
