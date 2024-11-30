package org.thejavengers.vista.gestionExcursiones;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thejavengers.vista.gestionMenuPrincipal.SceneManager;
import java.io.IOException;

/**
 * Controlador para la vista de gestión de excursiones.
 *
 * <p>Maneja las acciones de los botones en la interfaz gráfica de gestión de excursiones.</p>
 */
public class VistaGestionExcurciones {

    private static final Logger logger = LoggerFactory.getLogger(VistaGestionExcurciones.class);

    /**
     * Gestor centralizado para manejar vistas y estilos en la aplicación.
     */
    private SceneManager sceneManager;

    /**
     * Establece el gestor de escenas para este controlador.
     *
     * @param sceneManager Instancia del SceneManager.
     */
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    /**
     * Maneja la acción del botón "Añadir Excursión".
     */
    @FXML
    private void manejarAnadirExcursion() {
        logger.info("Intentando abrir la vista para añadir una excursión.");
        try {
            sceneManager.cambiarVista("/vistas/añadirExcursion.fxml", "Añadir Excursión", "/styles.css");
            logger.info("Vista de añadir excursión abierta correctamente.");
        } catch (Exception e) {
            logger.error("Error al abrir la vista de añadir excursión.", e);
            mostrarAlertaError("Error", "No se pudo abrir la vista de añadir excursión.");
        }
    }

    /**
     * Maneja la acción del botón "Filtrar Excursiones entre Fechas".
     */
    @FXML
    private void manejarFiltrarFechas() {
        logger.info("Intentando abrir la vista para filtrar excursiones entre fechas.");
        try {
            sceneManager.cambiarVista("/vistas/filtrarFechas.fxml", "Filtrar Excursiones", "/styles.css");
            logger.info("Vista de filtrar excursiones abierta correctamente.");
        } catch (Exception e) {
            logger.error("Error al abrir la vista de filtrar excursiones.", e);
            mostrarAlertaError("Error", "No se pudo abrir la vista para filtrar excursiones.");
        }
    }

    /**
     * Maneja la acción del botón "Mostrar Socios Inscritos".
     */
    @FXML
    private void manejarMostrarSocios() {
        logger.info("Intentando abrir la vista para mostrar socios inscritos.");
        try {
            sceneManager.cambiarVista("/vistas/mostrarSocios.fxml", "Mostrar Socios Inscritos", "/styles.css");
            logger.info("Vista de mostrar socios abierta correctamente.");
        } catch (Exception e) {
            logger.error("Error al abrir la vista de mostrar socios.", e);
            mostrarAlertaError("Error", "No se pudo abrir la vista para mostrar socios.");
        }
    }

    /**
     * Maneja la acción del botón "Volver al Menú Principal".
     */
    @FXML
    private void manejarVolverMenu() {
        logger.info("Intentando volver al menú principal.");
        if (sceneManager == null) {
            logger.error("El SceneManager no está configurado.");
            mostrarAlertaError("Error", "El gestor de escenas no está configurado. No se puede volver al menú principal.");
            return;
        }
        try {
            sceneManager.cambiarVista("/vistas/menuPrincipal.fxml", "Menú Principal", "/styles.css");
            logger.info("Vista del menú principal abierta correctamente.");
        } catch (Exception e) {
            logger.error("Error al volver al menú principal.", e);
            mostrarAlertaError("Error", "No se pudo volver al menú principal.");
        }
    }


    /**
     * Muestra una alerta de error informativa al usuario.
     *
     * @param titulo  Título de la alerta.
     * @param mensaje Contenido del mensaje de la alerta.
     */
    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
