package org.thejavengers.vista.gestionMenuPrincipal;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase centralizada para gestionar transiciones entre vistas en la aplicación JavaFX.
 * <p>
 * Esta clase permite cambiar entre vistas definidas en archivos FXML, aplicar estilos mediante archivos CSS,
 * y configurar el controlador de la vista cargada.
 * </p>
 */
public class SceneManager {

    private final Stage primaryStage;
    private final String defaultStylesheet;
    private Object controlador; // Almacena el controlador actual

    /**
     * Constructor de SceneManager.
     *
     * @param primaryStage      El Stage principal de la aplicación.
     * @param defaultStylesheet Ruta del archivo CSS por defecto para la aplicación (puede ser null si no se usa un estilo por defecto).
     */
    public SceneManager(Stage primaryStage, String defaultStylesheet) {
        if (primaryStage == null) {
            throw new IllegalArgumentException("El Stage principal no puede ser null.");
        }
        this.primaryStage = primaryStage;
        this.defaultStylesheet = defaultStylesheet;
    }

    /**
     * Cambia la vista actual cargando un archivo FXML con el estilo por defecto.
     *
     * @param fxmlPath Ruta del archivo FXML que se va a cargar.
     * @param title    Título de la nueva ventana.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    public void cambiarVista(String fxmlPath, String title) throws IOException {
        cambiarVista(fxmlPath, title, defaultStylesheet);
    }

    /**
     * Cambia la vista actual cargando un archivo FXML, aplicando un archivo CSS y configurando un controlador.
     *
     * @param fxmlPath   Ruta del archivo FXML que se va a cargar.
     * @param title      Título de la nueva ventana.
     * @param stylesheet Ruta del archivo CSS para el estilo (puede ser null para omitir estilos).
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    public void cambiarVista(String fxmlPath, String title, String stylesheet) throws IOException {
        validarParametros(fxmlPath, title);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Almacenar el controlador cargado
            this.controlador = loader.getController();

            Scene scene = new Scene(root);

            // Aplicar el archivo CSS si se especifica
            if (stylesheet != null && !stylesheet.trim().isEmpty()) {
                String stylesheetPath = getClass().getResource(stylesheet).toExternalForm();
                scene.getStylesheets().add(stylesheetPath);
            }

            // Configurar el Stage principal
            primaryStage.setScene(scene);
            primaryStage.setTitle(title);
            primaryStage.show();
        } catch (IOException e) {
            throw new IOException("Error al cargar la vista desde " + fxmlPath, e);
        }
    }

    /**
     * Devuelve el controlador de la vista actualmente cargada.
     *
     * @return El controlador asociado con la vista cargada.
     */
    public Object getControlador() {
        if (controlador == null) {
            throw new IllegalStateException("No se ha cargado ninguna vista. El controlador es null.");
        }
        return controlador;
    }

    /**
     * Valida que los parámetros proporcionados no sean nulos o vacíos.
     *
     * @param fxmlPath Ruta del archivo FXML.
     * @param title    Título de la ventana.
     * @throws IllegalArgumentException Si alguno de los parámetros es nulo o vacío.
     */
    private void validarParametros(String fxmlPath, String title) {
        if (fxmlPath == null || fxmlPath.trim().isEmpty()) {
            throw new IllegalArgumentException("La ruta del archivo FXML no puede ser nula o vacía.");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("El título de la ventana no puede ser nulo o vacío.");
        }
    }
}
