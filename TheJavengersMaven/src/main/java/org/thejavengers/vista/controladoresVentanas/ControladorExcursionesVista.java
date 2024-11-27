package org.thejavengers.vista.controladoresVentanas;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

/**
 * Controlador para la vista de gestión de excursiones.
 * Maneja la interacción del usuario con la interfaz gráfica.
 */
public class ControladorExcursionesVista {

    private static final Logger logger = LoggerFactory.getLogger(ControladorExcursionesVista.class);

    @FXML
    private TextField descripcionField;

    @FXML
    private DatePicker fechaPicker;

    @FXML
    private TextField numeroDiasField;

    @FXML
    private TextField precioField;

    /**
     * Método llamado al presionar el botón "Agregar Excursión".
     * Valida las entradas del usuario y simula el proceso de agregar una excursión.
     */
    @FXML
    public void agregarExcursion() {
        try {
            // Validar descripción
            String descripcion = descripcionField.getText().trim();
            if (descripcion.isEmpty()) {
                mostrarAlerta("Error de Validación", "La descripción no puede estar vacía.");
                return;
            }

            // Validar fecha
            LocalDate fecha = fechaPicker.getValue();
            if (fecha == null || fecha.isBefore(LocalDate.now())) {
                mostrarAlerta("Error de Validación", "La fecha no puede ser nula ni estar en el pasado.");
                return;
            }

            // Validar número de días
            int numeroDias = Integer.parseInt(numeroDiasField.getText().trim());
            if (numeroDias <= 0 || numeroDias > 365) {
                mostrarAlerta("Error de Validación", "El número de días debe estar entre 1 y 365.");
                return;
            }

            // Validar precio
            float precio = Float.parseFloat(precioField.getText().trim());
            if (precio < 0 || precio > 10_000) {
                mostrarAlerta("Error de Validación", "El precio debe ser mayor o igual a 0 y menor o igual a 10,000.");
                return;
            }

            // Simular agregar excursión
            logger.info("Excursión agregada: Descripción={}, Fecha={}, Días={}, Precio={}",
                    descripcion, fecha, numeroDias, precio);
            mostrarAlerta("Éxito", "Excursión agregada correctamente.");
            limpiarCampos();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error de Validación", "El número de días y el precio deben ser valores numéricos.");
            logger.warn("Entrada inválida: {}", e.getMessage());
        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error inesperado.");
            logger.error("Error inesperado al agregar excursión.", e);
        }
    }

    /**
     * Método para volver al menú principal.
     */
    @FXML
    public void volverAlMenu() {
        logger.info("Volviendo al menú principal.");
        // Lógica para cambiar a otra escena o cerrar la ventana actual
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
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Limpia los campos de entrada de la interfaz.
     */
    private void limpiarCampos() {
        descripcionField.clear();
        fechaPicker.setValue(null);
        numeroDiasField.clear();
        precioField.clear();
    }
}
