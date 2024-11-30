package org.thejavengers.vista.gestionMenuPrincipal;

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
import org.thejavengers.vista.gestionMenuPrincipal.SceneManager;
/**
 * Controlador para la vista del menú principal.
 *
 * <p>Gestiona las acciones del usuario en la interfaz gráfica del menú principal, incluyendo
 * la navegación a otras vistas y la salida de la aplicación.</p>
 */
public class VistaGestionMenuPrincipal {

    private static final Logger logger = LoggerFactory.getLogger(VistaGestionMenuPrincipal.class);

    /**
     * Botón para salir de la aplicación.
     */
    @FXML
    private Button btnSalir;

    /**
     * Gestor centralizado para el manejo de vistas en la aplicación.
     */
    private SceneManager sceneManager;

    /**
     * Establece el gestor de escenas para este controlador.
     *
     * @param sceneManager El gestor centralizado de escenas.
     */
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    /**
     * Abre la vista de Gestión de Excursiones.
     */
    @FXML
    public void abrirExcursionesVista() {
        logger.info("Navegando a la ventana de Gestión de Excursiones.");
        try {
            sceneManager.cambiarVista("/vistas/gestionExcursiones.fxml", "Gestión de Excursiones", "/styles.css");
            logger.info("Ventana de Gestión de Excursiones abierta correctamente.");
        } catch (Exception e) {
            logger.error("Error al abrir la ventana de Gestión de Excursiones.", e);
            mostrarAlertaError("No se pudo abrir la ventana de Gestión de Excursiones.", e.getMessage());
        }
    }

    /**
     * Abre la vista de Gestión de Inscripciones.
     */
    @FXML
    private void abrirInscripciones(ActionEvent event) {
        logger.info("Navegando a la ventana de Gestión de Inscripciones.");
        try {
            sceneManager.cambiarVista("/vistas/gestionInscripciones.fxml", "Gestión de Inscripciones", "/styles.css");
            logger.info("Ventana de Gestión de Inscripciones abierta correctamente.");
        } catch (Exception e) {
            logger.error("Error al abrir la ventana de Gestión de Inscripciones.", e);
            mostrarAlertaError("No se pudo abrir la ventana de Gestión de Inscripciones.", e.getMessage());
        }
    }

    /**
     * Abre la vista de Gestión de Socios.
     */
    @FXML
    private void abrirSocios(ActionEvent event) {
        logger.info("Navegando a la ventana de Gestión de Socios.");
        try {
            sceneManager.cambiarVista("/vistas/gestionSocios.fxml", "Gestión de Socios", "/styles.css");
            logger.info("Ventana de Gestión de Socios abierta correctamente.");
        } catch (Exception e) {
            logger.error("Error al abrir la ventana de Gestión de Socios.", e);
            mostrarAlertaError("No se pudo abrir la ventana de Gestión de Socios.", e.getMessage());
        }
    }

    /**
     * Sale de la aplicación cerrando el Stage principal.
     */
    @FXML
    public void salirAplicacion() {
        logger.info("El usuario ha seleccionado salir de la aplicación.");
        try {
            // Obtener el Stage actual desde el btnSalir
            Stage stage = (Stage) btnSalir.getScene().getWindow();
            if (stage != null) {
                stage.close();
                logger.info("Aplicación cerrada correctamente.");
            } else {
                throw new IllegalStateException("El Stage actual no está disponible.");
            }
        } catch (Exception e) {
            logger.error("Error al intentar cerrar la aplicación.", e);
            mostrarAlertaError("Error al salir", "No se pudo cerrar la ventana principal.");
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
}