package org.thejavengers.vista.gestionMenuPrincipal;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thejavengers.Excepciones.SceneManagerException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Clase centralizada para gestionar transiciones entre vistas en la aplicación JavaFX.
 *
 * <p>Esta clase facilita el manejo de vistas en la aplicación mediante:
 * <ul>
 *     <li>Carga de archivos FXML.</li>
 *     <li>Aplicación de estilos CSS predefinidos.</li>
 *     <li>Asignación de controladores a las vistas cargadas.</li>
 *     <li>Configuración de dimensiones fijas para garantizar un diseño uniforme.</li>
 * </ul>
 * </p>
 */
public class SceneManager {

    private static final Logger logger = LoggerFactory.getLogger(SceneManager.class);

    private final Stage primaryStage;        // Escenario principal de la aplicación
    private final String defaultStylesheet; // Hoja de estilo CSS predeterminada
    private Object controlador;             // Controlador asociado a la vista actual
    private final double fixedWidth;        // Ancho fijo de las ventanas
    private final double fixedHeight;       // Alto fijo de las ventanas
    private final boolean resizable;        // Configuración de si la ventana es redimensionable

    /**
     * Constructor de SceneManager.
     *
     * @param primaryStage      Stage principal de la aplicación.
     * @param defaultStylesheet Ruta del archivo CSS por defecto (puede ser null para omitir).
     * @param fixedWidth        Ancho fijo para todas las ventanas (en píxeles).
     * @param fixedHeight       Alto fijo para todas las ventanas (en píxeles).
     * @param resizable         Define si la ventana debe ser redimensionable.
     * @throws IllegalArgumentException Si los argumentos son inválidos.
     */
    public SceneManager(Stage primaryStage, String defaultStylesheet, double fixedWidth, double fixedHeight, boolean resizable) {
        if (primaryStage == null) {
            throw new IllegalArgumentException("El Stage principal no puede ser null.");
        }
        if (fixedWidth <= 0 || fixedHeight <= 0) {
            throw new IllegalArgumentException("Las dimensiones de la ventana deben ser mayores que 0.");
        }

        this.primaryStage = primaryStage;
        this.defaultStylesheet = defaultStylesheet;
        this.fixedWidth = fixedWidth;
        this.fixedHeight = fixedHeight;
        this.resizable = resizable;
    }

    /**
     * Cambia la vista actual cargando un archivo FXML con el estilo por defecto.
     *
     * @param fxmlPath Ruta del archivo FXML que se va a cargar.
     * @param title    Título de la nueva ventana.
     * @throws SceneManagerException Si ocurre un error al cargar la vista.
     */
    public void cambiarVista(String fxmlPath, String title) throws SceneManagerException {
        cambiarVista(fxmlPath, title, defaultStylesheet);
    }

    /**
     * Cambia la vista actual cargando un archivo FXML, aplicando un archivo CSS y configurando un controlador.
     *
     * @param fxmlPath   Ruta del archivo FXML que se va a cargar.
     * @param title      Título de la nueva ventana.
     * @param stylesheet Ruta del archivo CSS para el estilo (puede ser null para omitir estilos).
     * @throws SceneManagerException Si ocurre un error durante el cambio de vista.
     */
    public void cambiarVista(String fxmlPath, String title, String stylesheet) throws SceneManagerException {
        // Validar los parámetros de entrada
        validarParametros(fxmlPath, title);

        try {
            // Cargar el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Almacenar el controlador cargado
            this.controlador = loader.getController();
            // Configurar el SceneManager si el controlador tiene un método setSceneManager
            try {
                var setSceneManagerMethod = controlador.getClass().getMethod("setSceneManager", SceneManager.class);
                setSceneManagerMethod.invoke(controlador, this);
            } catch (NoSuchMethodException e) {
                System.err.println("El método setSceneManager no existe en el controlador: " + e.getMessage());
            } catch (IllegalAccessException e) {
                System.err.println("No se pudo acceder al método setSceneManager: " + e.getMessage());
            } catch (InvocationTargetException e) {
                System.err.println("Error al invocar el método setSceneManager: " + e.getTargetException());
            }

            // Crear una nueva escena con dimensiones fijas
            Scene scene = new Scene(root, fixedWidth, fixedHeight);

            // Aplicar la hoja de estilos CSS, si se especifica
            if (stylesheet != null && !stylesheet.trim().isEmpty()) {
                String stylesheetPath = getClass().getResource(stylesheet).toExternalForm();
                scene.getStylesheets().add(stylesheetPath);
            }

            // Configurar el Stage principal
            primaryStage.setScene(scene);
            primaryStage.setTitle(title);
            primaryStage.setResizable(false); // Evitar que la ventana sea redimensionable
            primaryStage.show();

        } catch (IOException e) {
            // Si ocurre un error al cargar la vista, lanzar una excepción personalizada
            throw new SceneManagerException("Error al cargar la vista desde: " + fxmlPath, e);
        } catch (Exception e) {
            // Captura cualquier otra excepción no esperada
            throw new SceneManagerException("Error inesperado al intentar cambiar de vista.", e);
        }
    }


    /**
     * Devuelve el controlador de la vista actualmente cargada.
     *
     * @return El controlador asociado con la vista cargada.
     * @throws IllegalStateException Si no se ha cargado ninguna vista.
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

    /**
     * Muestra un error al usuario mediante un cuadro de diálogo.
     *
     * @param titulo  Título del error.
     * @param mensaje Mensaje de descripción del error.
     */
    private void mostrarErrorUsuario(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Crítico");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
