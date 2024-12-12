package org.thejavengers.vista.gestionExcursiones;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thejavengers.vista.gestionMenuPrincipal.SceneManager;

/**
 * Controlador para la vista de Gestión de Excursiones.
 *
 * <p>Esta clase maneja la interacción del usuario con las opciones de gestión de excursiones,
 * como añadir excursiones, navegar a otras secciones y salir de la aplicación.</p>
 */
public class VistaAñadirExcursion {

    private static final Logger logger = LoggerFactory.getLogger(VistaAñadirExcursion.class);

    @FXML
    private Button btnSalir;

    // Instancia de SceneManager para manejar cambios de vistas
    private SceneManager sceneManager;

    /**
     * Establece el gestor de escenas para el controlador.
     *
     * @param sceneManager Instancia del gestor centralizado de escenas.
     */
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    /**
     * Navega a la ventana de Gestión de Excursiones.
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
     * Navega a la ventana de Gestión de Excursionistas.
     */
    @FXML
    public void abrirExcursionistas(ActionEvent event) {
        logger.info("Navegando a la ventana de Gestión de Excursionistas.");
        try {
            sceneManager.cambiarVista("/vistas/excursionistaVista.fxml", "Gestión de Excursionistas", "/styles.css");
            logger.info("Ventana de Gestión de Excursionistas abierta correctamente.");
        } catch (Exception e) {
            logger.error("Error al abrir la ventana de Gestión de Excursionistas.", e);
            mostrarAlertaError("No se pudo abrir la ventana de Gestión de Excursionistas.", e.getMessage());
        }
    }

    /**
     * Navega a la ventana de Gestión de Inscripciones.
     */
    @FXML
    public void abrirInscripciones(ActionEvent event) {
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
     * Navega a la ventana de Gestión de Socios.
     */
    @FXML
    public void abrirSocios(ActionEvent event) {
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
     * Maneja la acción del botón "Volver al Menú Principal".
     */
    @FXML
    private void manejarVolverMenu() {
        logger.info("Intentando volver al menú principal.");
        try {
            sceneManager.cambiarVista("/vistas/application.fxml", "Menú Principal", "/styles.css");
            logger.info("Vista del menú principal abierta correctamente.");
        } catch (Exception e) {
            logger.error("Error al volver al menú principal.", e);
            mostrarAlertaError("Error", "No se pudo volver al menú principal.");
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
