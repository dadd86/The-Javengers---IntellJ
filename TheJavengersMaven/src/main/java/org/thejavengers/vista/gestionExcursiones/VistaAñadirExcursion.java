package org.thejavengers.vista.gestionExcursiones;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thejavengers.controlador.ControladorExcursionesVista;

/**
 * Clase encargada de gestionar la interacción de la interfaz gráfica de "Añadir Excursión".
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

    private final ControladorExcursionesVista controlador;

    /**
     * Constructor que inicializa el controlador de excursiones.
     */
    public VistaAñadirExcursion() {
        this.controlador = new ControladorExcursionesVista();
    }

    /**
     * Maneja la acción del botón "Agregar Excursión".
     */
    @FXML
    public void agregarExcursion() {
        try {
            logger.info("Iniciando el proceso para agregar una nueva excursión.");
            controlador.validarYGuardarExcursion(
                    descripcionField.getText(),
                    fechaPicker.getValue(),
                    numeroDiasField.getText(),
                    precioField.getText()
            );
            mostrarAlerta("Éxito", "Excursión agregada correctamente.");
            limpiarCampos();
        } catch (IllegalArgumentException e) {
            mostrarAlerta("Error de Validación", e.getMessage());
            logger.warn("Error de validación al agregar excursión: {}", e.getMessage());
        } catch (Exception e) {
            mostrarAlertaError("Error", "Ocurrió un error inesperado.");
            logger.error("Error inesperado al agregar excursión.", e);
        }
    }

    /**
     * Maneja la acción del botón "Volver al Menú Principal".
     */
    @FXML
    public void manejarVolverMenu() {
        logger.info("Cerrando la vista de 'Añadir Excursión' y volviendo al menú principal.");
        Stage stage = (Stage) descripcionField.getScene().getWindow();
        if (stage != null) {
            stage.close();
        } else {
            logger.error("No se pudo cerrar la ventana. El Stage es nulo.");
        }
    }

    /**
     * Muestra una alerta informativa al usuario.
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
}
