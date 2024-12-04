package org.thejavengers.vista.gestionExcursiones;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thejavengers.DAO.ExcursionDAO;
import org.thejavengers.DAO.ExcursionDAOImpl;
import org.thejavengers.Excepciones.SceneManagerException;
import org.thejavengers.modelo.Excursion;
import org.thejavengers.vista.gestionMenuPrincipal.SceneManager;
import org.thejavengers.Excepciones.*;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Controlador para la vista de "Añadir Excursión".
 *
 * <p>Gestiona las acciones realizadas en la vista de añadir excursiones,
 * incluyendo validación de datos y cierre de la ventana actual.</p>
 */
public class VistaAñadirExcursion {

    private static final Logger logger = LoggerFactory.getLogger(VistaAñadirExcursion.class);

    @FXML
    private TextField descripcionField;

    @FXML
    private DatePicker fechaPicker;

    @FXML
    private TextField numeroDiasField;

    @FXML
    private TextField precioField;

    private final ExcursionDAO excursionDAO;

    /**
     * Constructor que inicializa el DAO para la gestión de excursiones.
     */
    public VistaAñadirExcursion() {
        this.excursionDAO = new ExcursionDAOImpl();
    }

    /**
     * Maneja la acción del botón "Agregar Excursión".
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
     * Cierra la ventana actual.
     */
    @FXML
    public void cerrarVentana() {
        logger.info("Cerrando la ventana de 'Añadir Excursión'.");
        Stage stage = (Stage) descripcionField.getScene().getWindow();
        if (stage != null) {
            stage.close();
        } else {
            logger.error("No se pudo cerrar la ventana. El Stage es nulo.");
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
     * Limpia los campos de entrada de la interfaz gráfica.
     */
    private void limpiarCampos() {
        descripcionField.clear();
        fechaPicker.setValue(null);
        numeroDiasField.clear();
        precioField.clear();
    }
    /**
     * Maneja la acción del botón "Volver al Menú Principal".
     */
    @FXML
    private void manejarVolverMenu() {
        logger.info("Intentando volver al menú principal.");
        try {
            logger.info("Vista del menú principal abierta correctamente.");
        } catch (Exception e) {
            logger.error("Error inesperado al volver al menú principal.", e);
            mostrarAlertaError("Error", "Ocurrió un error inesperado.");
        }
    }
}
