package org.thejavengers.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thejavengers.DAO.ExcursionDAOImpl;
import org.thejavengers.Excepciones.SceneManagerException;
import org.thejavengers.modelo.Excursion;
import org.thejavengers.modelo.Inscripcion;
import org.thejavengers.modelo.Socio;
import org.thejavengers.utils.HibernateUtil;
import org.thejavengers.vista.gestionExcursiones.VistaExcursionesPorFechas;
import org.thejavengers.vista.gestionMenuPrincipal.SceneManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ControladorFiltrarExcursiones {
    private SceneManager sceneManager; // Agregar referencia al SceneManager
    @FXML
    private TableView<VistaExcursionesPorFechas> tablaExcursiones;

    @FXML
    private TableColumn<VistaExcursionesPorFechas, Number> idExcursionColumn;

    @FXML
    private TableColumn<VistaExcursionesPorFechas, String> descripcionColumn;

    @FXML
    private TableColumn<VistaExcursionesPorFechas, LocalDate> fechaExcursionColumn;

    @FXML
    private TableColumn<VistaExcursionesPorFechas, Integer> numero_diasColumn;

    @FXML
    private TableColumn<VistaExcursionesPorFechas, String> precioColumn;

    @FXML
    private Button botonFiltrarPorFechas;

    @FXML
    private Button botonVolver;

    @FXML
    private Button botonExcursiones;

    @FXML
    private DatePicker fechaInicio;

    @FXML
    private DatePicker fechaFin;


    private final ExcursionDAOImpl excursionDAOImpl;

    private ObservableList<VistaExcursionesPorFechas> listaExcursiones;
    private List<Inscripcion> inscripciones;

    public ControladorFiltrarExcursiones() {
        this.excursionDAOImpl = new ExcursionDAOImpl();
        listaExcursiones = FXCollections.observableArrayList();
    }

    // Metodo para configurar el SceneManager
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @FXML
    public void initialize() {
        // Configurar columnas de la tabla
        idExcursionColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        descripcionColumn.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
        fechaExcursionColumn.setCellValueFactory(cellData -> cellData.getValue().fechaExcursionProperty());

        fechaExcursionColumn.setCellFactory(col -> new TableCell<VistaExcursionesPorFechas, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    setText(formatter.format(item));  // Formatea el LocalDate a String con el formato deseado
                } else {
                    setText(null);
                }
            }
        });


        numero_diasColumn.setCellValueFactory(cellData -> cellData.getValue().numeroDiasProperty().asObject());
        precioColumn.setCellValueFactory(data -> data.getValue().precioProperty().asObject().map(value -> String.format("%.2f €", value)));


        // Cargar datos iniciales en la tabla
        cargarExcursiones();

        // Configurar acciones de botones
        botonFiltrarPorFechas.setOnAction(event -> filtrarPorFechas());
        botonVolver.setOnAction(event -> volverAlMenuPrincipal());
        botonExcursiones.setOnAction(event -> volverAlMenuExcursion());

        fechaInicio.setPromptText("Escoge una fecha");
        fechaFin.setPromptText("Escoge una fecha");

        tablaExcursiones.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {  // Asegúrate de que es un solo clic
                VistaExcursionesPorFechas excursionSeleccionada = tablaExcursiones.getSelectionModel().getSelectedItem();
                if (excursionSeleccionada != null) {
                    mostrarSociosInscritos(excursionSeleccionada);
                }
            }
        });
    }

    /**
     * Metodo inicial para cargar las Excursiones que existen en la base de datos
     */
    private void cargarExcursiones() {
        ExcursionDAOImpl excursionDAOImpl = new ExcursionDAOImpl();
        List<Excursion> excursiones = excursionDAOImpl.findAll();

        listaExcursiones.clear();
        for (Excursion Excursion : excursiones) {
            listaExcursiones.add(new VistaExcursionesPorFechas(Excursion));
        }
        tablaExcursiones.setItems(listaExcursiones);
    }

    /**
     * Metodo para filtrar las excursiones existentes por fechas que introducirá el usuario
     */
    @FXML
    public void filtrarPorFechas() {
        LocalDate fechaInicioSeleccionada = fechaInicio.getValue();
        LocalDate fechaFinSeleccionada = fechaFin.getValue();

        if (fechaInicioSeleccionada == null && fechaFinSeleccionada == null) {
            cargarExcursiones();
        } else if (fechaInicioSeleccionada != null && fechaFinSeleccionada != null) {

            List<VistaExcursionesPorFechas> excursionesFiltradas = listaExcursiones.stream()
                    .filter(excursion -> {
                        LocalDate fechaExcursion = excursion.getFechaExcursion(); // Obtener la fecha de la excursión
                        return !fechaExcursion.isBefore(fechaInicioSeleccionada) && !fechaExcursion.isAfter(fechaFinSeleccionada);
                    })
                    .collect(Collectors.toList());  // Usar Collectors para recolectar el stream

            tablaExcursiones.setItems(FXCollections.observableList(excursionesFiltradas));
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Por favor, selecciona ambas fechas para filtrar.");
            alert.showAndWait();
        }
    }

    public List<Inscripcion> findByExcursion(Integer idExcursion) {
        List<Inscripcion> inscripciones = new ArrayList<>();
        String hql = "FROM Inscripcion i WHERE i.excursion.idExcursion = :idExcursion";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Inscripcion> query = session.createQuery(hql, Inscripcion.class);
            query.setParameter("idExcursion", idExcursion);

            inscripciones = query.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener inscripciones para la excursión: " + e.getMessage(), e);
        }

        return inscripciones;
    }

    public void cargarInscripciones(List<Inscripcion> inscripcionesCargadas) {
        this.inscripciones = inscripcionesCargadas;
    }

    /**
     * Metodo para mostrar los socios inscritos en una excursión mediante un pop-up/alert
     */
    @FXML
    private void mostrarSociosInscritos(VistaExcursionesPorFechas excursionSeleccionada) {
        final Logger logger = LoggerFactory.getLogger(ControladorFiltrarExcursiones.class);

        try {
            // Verificar si se seleccionó una excursión en la tabla
            if (excursionSeleccionada == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Por favor, selecciona una excursión.");
                alert.showAndWait();
                return;
            }

            // Obtener el ID de la excursión seleccionada
            int idExcursion = excursionSeleccionada.getId();

            // Filtrar las inscripciones en la lista cargada o cargar desde la base de datos si es necesario
            List<Inscripcion> inscripcionesFiltradas;
            if (inscripciones != null) {
                inscripcionesFiltradas = inscripciones.stream()
                        .filter(inscripcion -> inscripcion.getExcursion().getIdExcursion() == idExcursion)
                        .collect(Collectors.toList());
            } else {
                // Si no se han cargado inscripciones previamente, obtenlas desde la base de datos
                inscripcionesFiltradas = findByExcursion(idExcursion);
            }

            // Validar si hay inscripciones
            if (inscripcionesFiltradas.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No hay inscripciones para la excursión seleccionada.");
                alert.showAndWait();
                logger.info("No hay inscripciones para la excursión con ID: {}", idExcursion);
                return;
            }

            // Construir la lista de socios inscritos
            StringBuilder sociosText = new StringBuilder("Socios inscritos:\n");
            for (Inscripcion inscripcion : inscripcionesFiltradas) {
                Socio socio = inscripcion.getSocio(); // Obtener el socio asociado
                sociosText.append(socio.getNombre()).append(" ").append(socio.getApellidos()).append("\n");
            }

            // Mostrar los socios en un pop-up
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Socios Inscritos");
            alert.setHeaderText("Excursión: " + excursionSeleccionada.getDescripcion());
            alert.setContentText(sociosText.toString());
            alert.showAndWait();

            logger.info("Se encontraron {} socios inscritos en la excursión con ID: {}", inscripcionesFiltradas.size(), idExcursion);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al mostrar socios inscritos: " + e.getMessage());
            alert.showAndWait();
            logger.error("Error inesperado al mostrar socios inscritos.", e);
        }
    }

    /**
     * Metodo para volver al menú principal.
     */
    private void volverAlMenuPrincipal() {
        if (sceneManager != null) {
            try {
                sceneManager.cambiarVista("/vistas/application.fxml", "Menú Principal");
            } catch (SceneManagerException e) {
                throw new RuntimeException(e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "SceneManager no configurado.");
            alert.showAndWait();
        }
    }

    @FXML
    public void volverAlMenuExcursion() {
        final Logger logger = LoggerFactory.getLogger(ControladorFiltrarExcursiones.class);
        logger.info("Intentando volver al menú excursiones.");

        if (sceneManager == null) {
            logger.error("El SceneManager no está configurado.");
            return;
        }
        try {
            sceneManager.cambiarVista("/vistas/gestionExcursiones.fxml", "Menú Excursiones", "/styles.css");
            logger.info("Vista del menú excursiones abierta correctamente.");
        } catch (Exception e) {
            logger.error("Error al volver al menú excursiones.", e);
        }
    }

}