package org.thejavengers.vista.gestionSocios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.thejavengers.DAO.SocioDAO;
import org.thejavengers.DAO.SocioDAOImpl;
import org.thejavengers.Excepciones.SceneManagerException;
import org.thejavengers.modelo.Socio;
import org.thejavengers.modelo.SocioEstandar;
import org.thejavengers.modelo.TipoSeguro;
import org.thejavengers.vista.SocioViewModel;
import org.thejavengers.vista.gestionMenuPrincipal.SceneManager;

import java.util.List;
import java.util.Optional;
/**
 * Controlador para la gestión de socios.
 * <p>
 * Este controlador maneja las acciones relacionadas con la gestión de socios,
 * incluyendo la visualización, eliminación, modificación del seguro, y la visualización
 * de facturas mensuales. También proporciona navegación entre vistas.
 * </p>
 *
 * <p><strong>Mejoras:</strong></p>
 * <ul>
 *     <li>Validación robusta de entradas para evitar errores de ejecución.</li>
 *     <li>Mensajes descriptivos para guiar al usuario en caso de errores o acciones exitosas.</li>
 *     <li>Uso de una lista observable para sincronizar los datos de la interfaz gráfica.</li>
 * </ul>
 */
public class ControladorVistaSocios {
    private SceneManager sceneManager; // Agregar referencia al SceneManager
    @FXML
    private TableView<SocioViewModel> tablaSocios;

    @FXML
    private TableColumn<SocioViewModel, Number> idColumn;

    @FXML
    private TableColumn<SocioViewModel, String> nombreColumn;

    @FXML
    private TableColumn<SocioViewModel, String> apellidosColumn;

    @FXML
    private TableColumn<SocioViewModel, String> tipoColumn;

    @FXML
    private TableColumn<SocioViewModel, String> seguroColumn;

    @FXML
    private TableColumn<SocioViewModel, String> federacionColumn;

    @FXML
    private TableColumn<SocioViewModel, Integer> tutorColumn;

    @FXML
    private Button botonAgregar;

    @FXML
    private Button botonEliminar;

    @FXML
    private Button botonModificarSeguro;

    @FXML
    private Button botonMostrarFactura;

    @FXML
    private Button botonVolver;

    private final SocioDAO socioDAO;

    private ObservableList<SocioViewModel> listaSocios;
    /**
     * Constructor del controlador. Inicializa el DAO y la lista observable de socios.
     */
    public ControladorVistaSocios() {
        socioDAO = new SocioDAOImpl();
        listaSocios = FXCollections.observableArrayList();
    }
    /**
     * Configura el gestor de escenas para la navegación entre vistas.
     *
     * @param sceneManager Objeto de tipo SceneManager para gestionar vistas.
     */
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
    /**
     * Método de inicialización llamado automáticamente al cargar la vista FXML.
     * Configura las columnas de la tabla y las acciones de los botones.
     */
    @FXML
    public void initialize() {
        // Configurar columnas de la tabla
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        apellidosColumn.setCellValueFactory(cellData -> cellData.getValue().apellidosProperty());
        tipoColumn.setCellValueFactory(cellData -> cellData.getValue().tipoProperty());
        seguroColumn.setCellValueFactory(data -> data.getValue().seguroProperty());
        federacionColumn.setCellValueFactory(data -> data.getValue().federacionProperty());
        tutorColumn.setCellValueFactory(data -> data.getValue().tutorIdProperty().asObject());
        // Cargar datos iniciales en la tabla
        cargarSocios();

        // Configurar acciones de botones
        botonAgregar.setOnAction(event -> irAMenuAgregarSocio());
        botonEliminar.setOnAction(event -> eliminarSocioSeleccionado());
        botonModificarSeguro.setOnAction(event -> modificarSeguro());
        botonMostrarFactura.setOnAction(event -> mostrarFactura());
        botonVolver.setOnAction(event -> volverAlMenuPrincipal());
    }
    /**
     * Carga los datos de los socios desde la base de datos y actualiza la tabla.
     */
    private void cargarSocios() {
        List<Socio> socios = socioDAO.findAll();
        listaSocios.clear();
        for (Socio socio : socios) {
            listaSocios.add(new SocioViewModel(socio));
        }
        tablaSocios.setItems(listaSocios);
    }
    /**
     * Navega a la vista para agregar un nuevo socio.
     */
    @FXML
    private void irAMenuAgregarSocio() {
        if (sceneManager != null) {
            try {
                // Cambiar a la vista de agregar socio
                sceneManager.cambiarVista("/vistas/AgregarSocio.fxml", "Agregar Socio");
            } catch (SceneManagerException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error al cargar la vista de Agregar Socio.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "SceneManager no configurado.");
            alert.showAndWait();
        }
    }
    /**
     * Elimina el socio seleccionado en la tabla.
     */

    private void eliminarSocioSeleccionado() {
        SocioViewModel seleccionado = tablaSocios.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            socioDAO.delete(seleccionado.getId());
            cargarSocios();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Seleccione un socio para eliminar.");
            alert.showAndWait();
        }
    }
    /**
     * Modifica el seguro del socio estándar seleccionado.
     */
    private void modificarSeguro() {
        SocioViewModel seleccionado = tablaSocios.getSelectionModel().getSelectedItem();
        if (seleccionado == null || !(seleccionado.getSocioOriginal() instanceof SocioEstandar)) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Seleccione un socio estándar para modificar su seguro.");
            alert.showAndWait();
            return;
        }

        SocioEstandar socioEstandar = (SocioEstandar) seleccionado.getSocioOriginal();

        // Mostrar opciones para seleccionar un nuevo seguro
        ChoiceDialog<TipoSeguro> dialog = new ChoiceDialog<>(socioEstandar.getSeguro(), TipoSeguro.values());
        dialog.setTitle("Modificar Seguro");
        dialog.setHeaderText("Seleccionar nuevo tipo de seguro");
        dialog.setContentText("Seleccione:");

        Optional<TipoSeguro> resultado = dialog.showAndWait();
        resultado.ifPresent(nuevoSeguro -> {
            socioEstandar.setSeguro(nuevoSeguro);
            socioDAO.update(socioEstandar);
            cargarSocios();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Seguro actualizado con éxito.");
            alert.showAndWait();
        });
    }
    /**
     * Muestra la factura mensual del socio seleccionado.
     */
    private void mostrarFactura() {
        SocioViewModel seleccionado = tablaSocios.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            float factura = socioDAO.obtenerFacturaMensual(seleccionado.getId());
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Factura Mensual del Socio: " + factura + "€");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Seleccione un socio para mostrar su factura.");
            alert.showAndWait();
        }
    }
    /**
     * Navega al menú principal.
     */
    private void volverAlMenuPrincipal() {
        if (sceneManager != null) {
            try {
                sceneManager.cambiarVista("/vistas/application.fxml", "Menú Principal");
            } catch (SceneManagerException e) {
                throw new RuntimeException(e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "SceneManager no configurado.");
            alert.showAndWait();
        }
    }

}