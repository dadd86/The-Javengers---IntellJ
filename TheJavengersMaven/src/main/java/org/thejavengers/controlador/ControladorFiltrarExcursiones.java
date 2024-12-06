package org.thejavengers.controlador;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.thejavengers.DAO.ExcursionDAOImpl;
import org.thejavengers.modelo.Excursion;
import org.thejavengers.vista.gestionExcursiones.VistaExcursionesPorFechas;
import org.thejavengers.vista.gestionMenuPrincipal.SceneManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ControladorFiltrarExcursiones {
    private SceneManager sceneManager; // Agregar referencia al SceneManager
    @FXML
    private TableView<VistaExcursionesPorFechas> tablaExcursiones;

    @FXML
    private TableColumn<VistaExcursionesPorFechas, Number> idExcursionColumn;

    @FXML
    private TableColumn<VistaExcursionesPorFechas, String> descripcionColumn;

    @FXML
    private TableColumn<VistaExcursionesPorFechas, LocalDate> fechaExcursionColumn;

    @FXML
    private TableColumn<VistaExcursionesPorFechas, Integer> numero_diasColumn;

    @FXML
    private TableColumn<VistaExcursionesPorFechas, String> precioColumn;

    @FXML
    private Button botonFiltrarPorFechas;

    @FXML
    private Button botonFiltrarPorSocio;

    @FXML
    private Button botonVolver;

    @FXML
    private DatePicker fechaInicio;

    @FXML
    private DatePicker fechaFin;


    private final ExcursionDAOImpl excursionDAOImpl;

    private ObservableList<VistaExcursionesPorFechas> listaExcursiones;

    public ControladorFiltrarExcursiones() {
        this.excursionDAOImpl = new ExcursionDAOImpl();
        listaExcursiones = FXCollections.observableArrayList();
    }

    // Metodo para configurar el SceneManager
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @FXML
    public void initialize() {
        // Configurar columnas de la tabla
        idExcursionColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        descripcionColumn.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
        fechaExcursionColumn.setCellValueFactory(cellData -> cellData.getValue().fechaExcursionProperty());

        fechaExcursionColumn.setCellFactory(col -> new TableCell<VistaExcursionesPorFechas, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    setText(formatter.format(item));  // Formatea el LocalDate a String con el formato deseado
                } else {
                    setText(null);
                }
            }
        });


        numero_diasColumn.setCellValueFactory(cellData -> cellData.getValue().numeroDiasProperty().asObject());
        precioColumn.setCellValueFactory(data -> data.getValue().precioProperty().asObject().map(value -> String.format("%.2f €", value)));


        // Cargar datos iniciales en la tabla
        cargarExcursiones();

        // Configurar acciones de botones
        botonFiltrarPorFechas.setOnAction(event -> filtrarPorFechas());
        botonVolver.setOnAction(event -> volverAlMenuPrincipal());

        fechaInicio.setPromptText("Escoge una fecha");
        fechaFin.setPromptText("Escoge una fecha");
    }

    /**
     * Metodo inicial para cargar las Excursiones que existen en la base de datos
     */
    private void cargarExcursiones() {
        ExcursionDAOImpl excursionDAOImpl = new ExcursionDAOImpl();
        List<Excursion> excursiones = excursionDAOImpl.findAll();

        listaExcursiones.clear();
        for (Excursion Excursion : excursiones) {
            listaExcursiones.add(new VistaExcursionesPorFechas(Excursion));
        }
        tablaExcursiones.setItems(listaExcursiones);
    }

    /**
     * Metodo para filtrar las excursiones existentes por fechas que introducirá el usuario
     */
    @FXML
    public void filtrarPorFechas() {
        LocalDate fechaInicioSeleccionada = fechaInicio.getValue();
        LocalDate fechaFinSeleccionada = fechaFin.getValue();

        if (fechaInicioSeleccionada == null && fechaFinSeleccionada == null) {
            cargarExcursiones();
        } else if (fechaInicioSeleccionada != null && fechaFinSeleccionada != null) {

            List<VistaExcursionesPorFechas> excursionesFiltradas = listaExcursiones.stream()
                    .filter(excursion -> {
                        LocalDate fechaExcursion = excursion.getFechaExcursion(); // Obtener la fecha de la excursión
                        return !fechaExcursion.isBefore(fechaInicioSeleccionada) && !fechaExcursion.isAfter(fechaFinSeleccionada);
                    })
                    .collect(Collectors.toList());  // Usar Collectors para recolectar el stream

            tablaExcursiones.setItems(FXCollections.observableList(excursionesFiltradas));
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Por favor, selecciona ambas fechas para filtrar.");
            alert.showAndWait();
        }
    }

    /**
     * Metodo para volver al menú principal.
     */
    private void volverAlMenuPrincipal() {
        if (sceneManager != null) {
            try {
                sceneManager.cambiarVista("/vistas/application.fxml", "Menú Principal");
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error al cargar el menú principal.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "SceneManager no configurado.");
            alert.showAndWait();
        }
    }
}
