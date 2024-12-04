package org.thejavengers.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thejavengers.DAO.ExcursionDAO;
import org.thejavengers.DAO.ExcursionDAOImpl;
import org.thejavengers.modelo.Excursion;
import org.thejavengers.vista.gestionMenuPrincipal.SceneManager;

import java.time.LocalDate;

/**
 * Controlador para la vista de gestión de excursiones.
 *
 * <p>Gestiona la interacción entre la interfaz gráfica y la lógica de negocio relacionada
 * con excursiones, incluyendo validación de datos y cambios de vista mediante SceneManager.</p>
 */
public class ControladorExcursionesVista {

    private static final Logger logger = LoggerFactory.getLogger(ControladorExcursionesVista.class);

    // Campos de entrada en la interfaz
    @FXML
    private TextField descripcionField;

    @FXML
    private DatePicker fechaPicker;

    @FXML
    private TextField numeroDiasField;

    @FXML
    private TextField precioField;

    @FXML
    private VBox mainVBox;

    // DAO para gestionar la lógica de persistencia
    private final ExcursionDAO excursionDAO;
    private SceneManager sceneManager; // SceneManager para cambios de vista

    /**
     * Constructor que inicializa el DAO para la gestión de excursiones.
     */
    public ControladorExcursionesVista() {
        this.excursionDAO = new ExcursionDAOImpl();
    }

    /**
     * Establece el gestor de escenas para este controlador.
     *
     * @param sceneManager Instancia de SceneManager para cambiar vistas.
     */
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    /**
     * Maneja el evento del botón "Agregar Excursión".
     * Valida los datos de entrada y guarda la excursión mediante el DAO.
     */
    @FXML
    public void agregarExcursion() {
        try {
            logger.info("Intentando agregar una nueva excursión.");

            // Validar los campos de entrada
            String descripcion = descripcionField.getText().trim();
            if (descripcion.isEmpty()) {
                mostrarAlerta("Error de Validación", "La descripción no puede estar vacía.");
                return;
            }

            LocalDate fecha = fechaPicker.getValue();
            if (fecha == null || fecha.isBefore(LocalDate.now())) {
                mostrarAlerta("Error de Validación", "La fecha no puede ser nula ni estar en el pasado.");
                return;
            }

            int numeroDias;
            try {
                numeroDias = Integer.parseInt(numeroDiasField.getText().trim());
                if (numeroDias <= 0 || numeroDias > 365) {
                    mostrarAlerta("Error de Validación", "El número de días debe estar entre 1 y 365.");
                    return;
                }
            } catch (NumberFormatException e) {
                mostrarAlerta("Error de Validación", "El número de días debe ser un valor numérico.");
                return;
            }

            float precio;
            try {
                precio = Float.parseFloat(precioField.getText().trim());
                if (precio < 0 || precio > 10_000) {
                    mostrarAlerta("Error de Validación", "El precio debe estar entre 0 y 10,000.");
                    return;
                }
            } catch (NumberFormatException e) {
                mostrarAlerta("Error de Validación", "El precio debe ser un valor numérico.");
                return;
            }

            // Crear y guardar la excursión
            Excursion nuevaExcursion = new Excursion(0, descripcion, fecha, numeroDias, precio);
            excursionDAO.save(nuevaExcursion);

            logger.info("Excursión agregada exitosamente: {}", nuevaExcursion);
            mostrarAlerta("Éxito", "Excursión agregada correctamente.");
            limpiarCampos();
        } catch (Exception e) {
            logger.error("Error inesperado al agregar excursión.", e);
            mostrarAlertaError("Error", "Ocurrió un error inesperado.");
        }
    }

    /**
     * Cambia a la vista del menú principal.
     */
    @FXML
    public void volverAlMenu() {
        try {
            if (sceneManager == null) {
                throw new IllegalStateException("SceneManager no está configurado.");
            }
            logger.info("Volviendo al menú principal.");
            sceneManager.cambiarVista("/vistas/application.fxml", "Menú Principal", "/styles.css");
        } catch (Exception e) {
            logger.error("Error al intentar volver al menú principal.", e);
            mostrarAlertaError("Error de Navegación", "No se pudo volver al menú principal.");
        }
    }

    /**
     * Muestra una alerta de información al usuario.
     *
     * @param titulo  Título de la alerta.
     * @param mensaje Contenido del mensaje de la alerta.
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra una alerta de error al usuario.
     *
     * @param header  Título del mensaje de error.
     * @param content Detalle del mensaje de error.
     */
    private void mostrarAlertaError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Limpia los campos de entrada en la interfaz gráfica.
     */
    private void limpiarCampos() {
        descripcionField.clear();
        fechaPicker.setValue(null);
        numeroDiasField.clear();
        precioField.clear();
    }
}