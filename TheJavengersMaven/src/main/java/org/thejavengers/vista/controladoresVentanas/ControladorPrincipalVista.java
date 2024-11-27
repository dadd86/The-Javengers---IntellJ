package org.thejavengers.vista.controladoresVentanas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import java.io.IOException;

public class ControladorPrincipalVista {

    private static final Logger logger = LoggerFactory.getLogger(ControladorPrincipalVista.class);
    // Referencia al botón de "Salir", para cerrar la ventana actual.
    @FXML
    private Button btnSalir;

    /**
     * Abre la ventana de Gestión de Excursiones.
     * Este método carga la vista definida en `ExcursionesVista.fxml` y abre una nueva ventana.
     */
    @FXML
    public void abrirExcursionesVista() {
        try {
            // Cargar la vista de Excursiones desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ExcursionesVista.fxml"));
            Parent root = loader.load();

            // Aplicar el estilo definido en styles.css
            root.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

            // Configurar la nueva ventana
            Stage excursionesStage = new Stage();
            excursionesStage.setScene(new Scene(root));
            excursionesStage.setTitle("Gestión de Excursiones");
            excursionesStage.show();

            // Log de acción exitosa
            logger.info("Ventana de Gestión de Excursiones abierta correctamente.");
        } catch (IOException e) {
            // Log de error
            logger.error("Error al abrir la ventana de Gestión de Excursiones.", e);

            // Mostrar alerta de error al usuario
            mostrarAlertaError("No se pudo abrir la ventana de Gestión de Excursiones.", e.getMessage());
        }
    }

    @FXML
    private void abrirExcursionistas(ActionEvent event) {
        logger.info("Navegando a la ventana de Gestión de Excursionistas.");
        cargarVista("/ExcursionistaVista.fxml", "Gestión de Excursionistas");
    }

    @FXML
    private void abrirInscripciones(ActionEvent event) {
        logger.info("Navegando a la ventana de Gestión de Inscripciones.");
        cargarVista("/InscripcionesVista.fxml", "Gestión de Inscripciones");
    }

    @FXML
    private void abrirSocios(ActionEvent event) {
        logger.info("Navegando a la ventana de Gestión de Socios.");
        cargarVista("/SociosVista.fxml", "Gestión de Socios");
    }

    @FXML
    private void salirAplicacion(ActionEvent event) {
        logger.info("Saliendo de la aplicación.");
        System.exit(0);
    }

    /**
     * Método auxiliar para cargar una nueva vista.
     *
     * @param fxmlPath Ruta del archivo FXML de la vista.
     * @param titulo   Título de la ventana.
     */
    private void cargarVista(String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            logger.error("Error al cargar la vista: {}", fxmlPath, e);
        }
    }
    /**
     * Muestra una alerta de error genérica al usuario.
     *
     * @param header  Título de la alerta.
     * @param content Detalle del error.
     */
    private void mostrarAlertaError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    /**
     * Cierra la aplicación al presionar el botón "Salir".
     * Este método cierra la ventana actual.
     */
    @FXML
    public void salirAplicacion() {
        try {
            logger.info("El usuario ha seleccionado salir de la aplicación.");

            // Obtener el Stage actual y cerrarlo
            Stage stage = (Stage) btnSalir.getScene().getWindow();
            stage.close();

            // Log de cierre exitoso
            logger.info("Aplicación cerrada correctamente.");
        } catch (Exception e) {
            // Log de error inesperado
            logger.error("Error al intentar cerrar la aplicación.", e);

            // Mostrar alerta de error al usuario
            mostrarAlertaError("Error al salir", "No se pudo cerrar la ventana principal.");
        }
    }
}
