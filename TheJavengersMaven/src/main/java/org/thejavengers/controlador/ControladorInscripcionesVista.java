package org.thejavengers.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thejavengers.DAO.ExcursionDAO;
import org.thejavengers.DAO.ExcursionDAOImpl;
import org.thejavengers.DAO.InscripcionDAO;
import org.thejavengers.DAO.InscripcionDAOImpl;
import org.thejavengers.DAO.SocioDAO;
import org.thejavengers.DAO.SocioDAOImpl;
import org.thejavengers.Excepciones.SceneManagerException;
import org.thejavengers.modelo.Excursion;
import org.thejavengers.modelo.Inscripcion;
import org.thejavengers.modelo.Socio;
import org.thejavengers.vista.InscripcionViewModel;
import org.thejavengers.vista.SocioViewModel;
import org.thejavengers.vista.gestionMenuPrincipal.SceneManager;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador para la vista de gestión de inscripciones.
 * Maneja la interacción del usuario con la interfaz gráfica.
 */
public class ControladorInscripcionesVista {

    private static final Logger logger = LoggerFactory.getLogger(ControladorInscripcionesVista.class);

    private SceneManager sceneManager; // Agregar referencia al SceneManager

    @FXML
    private TableView<InscripcionViewModel> tablaInscripciones;

    @FXML
    private TableColumn<InscripcionViewModel, Integer> idInscripcion;

    @FXML
    private TableColumn<InscripcionViewModel,Integer> idSocio;

    @FXML
    private TableColumn<SocioViewModel, String> apellidosSocio;

    @FXML
    private TableColumn<InscripcionViewModel, String> descripcionExcursion;

    @FXML
    private TableColumn<InscripcionViewModel, String> fechaInscripcion;

    @FXML
    private TextField idSocioField;

    @FXML
    private ComboBox<Socio> sociosComboBox;

    @FXML
    private ComboBox<Excursion> excursionesComboBox;

    @FXML
    private VBox mainVBox;

    @FXML
    private Button botonInscribir;

    @FXML
    private Button botonEliminar;

    @FXML
    private Button botonModificar;

    @FXML
    private Button botonConsultar;

    @FXML
    private Button botonCancelar;

    private final InscripcionDAO inscripcionDAO;

    private ObservableList<InscripcionViewModel> inscripcionesObservableList;

    /**
     * Constructor que inicializa los DAOs necesarios.
     */
    public ControladorInscripcionesVista() {
        this.inscripcionDAO = new InscripcionDAOImpl();
    }

    /**
     * Establece el gestor de escenas para este controlador.
     *
     * @param sceneManager Instancia del SceneManager.
     */
    public void setSceneManager(SceneManager sceneManager) {this.sceneManager = sceneManager;}

    /**
     * Inicializa la vista y los datos al cargar la interfaz.
     * * Configura las columnas de la tabla de inscripciones.
     */
    @FXML
    public void initialize() {
        idInscripcion.setCellValueFactory(cellData -> cellData.getValue().idInscripcionProperty().asObject());
        fechaInscripcion.setCellValueFactory(cellData -> cellData.getValue().fechaInscripcionProperty());

        inscripcionesObservableList = FXCollections.observableArrayList();
        tablaInscripciones.setItems(inscripcionesObservableList);

        // Configurar acciones de botones
        botonInscribir.setOnAction(event -> irAMenuAgregarInscripcion());
        botonEliminar.setOnAction(event -> eliminarInscripcion());
        botonModificar.setOnAction(event -> modificarInscripcion());
        botonConsultar.setOnAction(event -> consultarInscripcion());
        botonCancelar.setOnAction(event -> cancelarInscripcion());

        cargarInscripciones();
    }

    /**
     * Carga las inscripciones desde la base de datos y las muestra en la tabla.
     */
    private void cargarInscripciones() {
        try {
            List<Inscripcion> inscripciones = inscripcionDAO.findAll();

            // Validar si la lista obtenida es nula o vacía
            if (inscripciones == null || inscripciones.isEmpty()) {
                inscripcionesObservableList.clear(); // Limpiar la lista observable si no hay inscripciones
                System.out.println("No se encontraron inscripciones.");
                return;
            }

            // Asegurarse de que la lista observable esté inicializada
            if (inscripcionesObservableList == null) {
                inscripcionesObservableList = FXCollections.observableArrayList();
            }

            // Limpiar la lista observable antes de agregar nuevas inscripciones
            inscripcionesObservableList.clear();

            // Convertir cada inscripción a InscripcionViewModel y agregarla a la lista observable
            for (Inscripcion inscripcion : inscripciones) {
                try {
                    inscripcionesObservableList.add(new InscripcionViewModel(inscripcion));
                } catch (Exception e) {
                    System.err.println("Error al convertir inscripción: " + inscripcion + ". " + e.getMessage());
                }
            }
        } catch (Exception e) {
            // Manejar cualquier excepción que ocurra durante la carga
            System.err.println("Error al cargar inscripciones: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void irAMenuAgregarInscripcion() {
        if (sceneManager != null) {
            try {
                // Cambiar a la vista de agregar inscripción
                sceneManager.cambiarVista("/vistas/AgregarInscripcion.fxml", "Agregar Inscripcion");
            } catch (SceneManagerException e) {
                throw new RuntimeException(e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "SceneManager no configurado.");
            alert.showAndWait();
        }
    }


    /**
     * Maneja la acción del botón "Eliminar".
     */
    @FXML
    public void eliminarInscripcion() {
        // Obtener la inscripción seleccionada de la tabla
        InscripcionViewModel seleccionada = tablaInscripciones.getSelectionModel().getSelectedItem();

        // Verificar si hay una inscripción seleccionada
        if (seleccionada != null) {
            // Eliminar la inscripción del DAO
            inscripcionDAO.deleteById(seleccionada.getIdInscripcion());

            // Actualizar la tabla eliminando la inscripción de la lista observable
            inscripcionesObservableList.remove(seleccionada);

            // Mostrar un mensaje de éxito
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Inscripción eliminada con éxito.");
            alert.showAndWait();
        } else {
            // Si no hay inscripción seleccionada, mostrar un mensaje de advertencia
            Alert alert = new Alert(Alert.AlertType.WARNING, "Seleccione una inscripción para eliminar.");
            alert.showAndWait();
        }
    }


    /**
     * Maneja la acción del botón "Modificar".
     */
    @FXML
    public void modificarInscripcion() {
        InscripcionViewModel seleccionada = tablaInscripciones.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una inscripción para modificar.");
            return;
        }

        try {
            Inscripcion inscripcionOriginal = seleccionada.getInscripcionOriginal();
            // Mostrar los detalles de la inscripción en un formulario (podría ser otro diálogo o cambiar los campos)
            // Suponiendo que tengas campos de texto o un formulario para permitir que el usuario modifique los detalles

            // Ejemplo de modificación: Cambiar la excursión seleccionada
            Excursion nuevaExcursion = excursionesComboBox.getSelectionModel().getSelectedItem();
            if (nuevaExcursion == null) {
                mostrarAlerta("Error de Validación", "Debe seleccionar una nueva excursión.");
                return;
            }

            // Actualizar la inscripción con los nuevos detalles
            inscripcionOriginal.setExcursion(nuevaExcursion);
            // Guardar los cambios en la base de datos
            inscripcionDAO.save(inscripcionOriginal);
            // Actualizar la lista observable para reflejar el cambio
            int index = inscripcionesObservableList.indexOf(seleccionada);
            inscripcionesObservableList.set(index, new InscripcionViewModel(inscripcionOriginal));

            logger.info("Inscripción modificada con éxito: {}", inscripcionOriginal.getIdInscripcion());
            mostrarAlerta("Éxito", "La inscripción se modificó correctamente.");
        } catch (Exception e) {
            logger.error("Error al modificar la inscripción", e);
            mostrarAlerta("Error", "Ocurrió un error al intentar modificar la inscripción.");
        }
    }

    @FXML
    public void consultarInscripcion() {
        InscripcionViewModel seleccionada = tablaInscripciones.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una inscripción para consultar.");
            return;
        }

        try {
            Inscripcion inscripcionOriginal = seleccionada.getInscripcionOriginal();
            String detalles = String.format("ID Inscripción: %d\nSocio: %s %s\nExcursión: %s\nFecha Inscripción: %s\nEstado: %s",
                    inscripcionOriginal.getIdInscripcion(),
                    inscripcionOriginal.getSocio().getNombre(),
                    inscripcionOriginal.getSocio().getApellidos(),
                    inscripcionOriginal.getExcursion().getDescripcion(),
                    inscripcionOriginal.getFechaInscripcion().toString());

            mostrarAlerta("Detalles de Inscripción", detalles);
        } catch (Exception e) {
            logger.error("Error al consultar la inscripción", e);
            mostrarAlerta("Error", "Ocurrió un error al intentar consultar la inscripción.");
        }
    }


    /**
     * Maneja la acción del botón "Cancelar".
     * Permite eliminar una inscripción seleccionada.
     */
    @FXML
    public void cancelarInscripcion() {
        InscripcionViewModel seleccionada = tablaInscripciones.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una inscripción para cancelar.");
            return;
        }

        try {
            Inscripcion inscripcionOriginal = seleccionada.getInscripcionOriginal();

            // Corregir: obtener el ID de la inscripción y usarlo para eliminar
            inscripcionDAO.deleteById(inscripcionOriginal.getIdInscripcion());
            inscripcionesObservableList.remove(seleccionada);

            logger.info("Inscripción cancelada con éxito: {}", inscripcionOriginal.getIdInscripcion());
            mostrarAlerta("Éxito", "La inscripción se canceló correctamente.");
        } catch (Exception e) {
            logger.error("Error al cancelar la inscripción", e);
            mostrarAlerta("Error", "Ocurrió un error al intentar cancelar la inscripción.");
        }
    }

    /**
     * Metodo para volver al menú principal.
     */
    @FXML
    public void volverAlMenu() {
        logger.info("Intentando volver al menú principal.");

        if (sceneManager == null) {
            logger.error("El SceneManager no está configurado.");
            mostrarAlerta("Error", "El gestor de escenas no está configurado. No se puede volver al menú principal.");
            return;
        }
        try {
            sceneManager.cambiarVista("/vistas/application.fxml", "Menú Principal", "/styles.css");
            logger.info("Vista del menú principal abierta correctamente.");
        } catch (Exception e) {
            logger.error("Error al volver al menú principal.", e);
            mostrarAlerta("Error", "No se pudo volver al menú principal.");
        }
    }

    /**
     * Muestra una alerta informativa al usuario.
     *
     * @param titulo   Título de la alerta.
     * @param mensaje  Mensaje de la alerta.
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}