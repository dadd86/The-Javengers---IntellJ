package org.thejavengers.vista.gestionExcursiones;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thejavengers.Excepciones.SceneManagerException;
import org.thejavengers.vista.gestionMenuPrincipal.*;
import org.thejavengers.vista.gestionMenuPrincipal.SceneManager;
import org.thejavengers.Excepciones.*;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;



/**
 * Controlador para la vista de gestión de excursiones.
 *
 * <p>Maneja las acciones de los botones en la interfaz gráfica de gestión de excursiones.</p>
 */
public class VistaGestionExcursiones {

    private static final Logger logger = LoggerFactory.getLogger(VistaGestionExcursiones.class);

    /**
     * Gestor centralizado para manejar vistas y estilos en la aplicación.
     */
    public SceneManager sceneManager;

    /**
     * Establece el gestor de escenas para este controlador.
     *
     * @param sceneManager Instancia del SceneManager.
     */
    public void setSceneManager(SceneManager sceneManager) {
        if (sceneManager == null) {
            throw new IllegalArgumentException("El SceneManager no puede ser null.");
        }
        this.sceneManager = sceneManager;
        logger.info("SceneManager configurado correctamente para VistaGestionExcurciones.");
    }

    /**
     * Verifica si el SceneManager está configurado correctamente.*
     * @throws IllegalStateException Si el SceneManager no está configurado.
     * */

    private void validarSceneManager() {
        if (sceneManager == null) {
            logger.error("El SceneManager no está configurado. No se puede cambiar la vista.");
            throw new IllegalStateException("El SceneManager no está configurado.");}}
    /**
     * Maneja la acción del botón "Añadir Excursión".
     */
    @FXML
    private void manejarAnadirExcursion() {
        logger.info("Intentando abrir la vista para añadir una excursión.");
        try {
            sceneManager.cambiarVista("/vistas/agregarExcursion.fxml", "Añadir Excursión", "/styles.css");
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
            validarSceneManager();
            sceneManager.cambiarVista("/vistas/filtrarFechas.fxml", "Filtrar Excursiones", "/styles.css");
            logger.info("Vista de filtrar excursiones abierta correctamente.");
        } catch (SceneManagerException e) {
            logger.error("Error al cambiar la vista para filtrar excursiones.", e);
            mostrarAlertaError("Error de Navegación", e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al abrir la vista de filtrar excursiones.", e);
            mostrarAlertaError("Error", "Ocurrió un error inesperado.");
        }
    }
    /**
     * Maneja la acción del botón "Mostrar Socios Inscritos".
     */
    @FXML
    private void manejarMostrarSocios() {
        logger.info("Intentando abrir la vista para mostrar socios inscritos.");
        try {
            validarSceneManager();
            sceneManager.cambiarVista("/vistas/mostrarSocios.fxml", "Mostrar Socios Inscritos", "/styles.css");
            logger.info("Vista de mostrar socios abierta correctamente.");
        } catch (SceneManagerException e) {
            logger.error("Error al cambiar la vista para mostrar socios.", e);
            mostrarAlertaError("Error de Navegación", e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al abrir la vista de mostrar socios.", e);
            mostrarAlertaError("Error", "Ocurrió un error inesperado.");
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
            sceneManager.cambiarVista("/vistas/application.fxml", "Menú Principal", "/styles.css");
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
