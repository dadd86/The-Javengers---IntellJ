package org.thejavengers.controlador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thejavengers.modelo.Excursion;
import org.thejavengers.modelo.SistemaExcursionista;
import org.thejavengers.vista.VistaExcursiones;
import org.thejavengers.DAO.ExcursionDAO;
import org.thejavengers.DAO.ExcursionDAOImpl;
import org.thejavengers.Excepciones.ExcursionYaExisteException;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador para gestionar las excursiones en el sistema excursionista.
 *
 * <p>Esta clase es responsable de manejar las interacciones entre el modelo
 * {@link SistemaExcursionista}, la vista {@link VistaExcursiones}, y la capa de
 * datos {@link ExcursionDAO}.</p>
 *
 * <p><strong>Mejoras en Robustez y Seguridad:</strong></p>
 * <ul>
 *     <li>Validación exhaustiva de entradas antes de procesarlas.</li>
 *     <li>Uso optimizado de {@link Logger} para registro detallado.</li>
 *     <li>Manejo adecuado de excepciones para garantizar la estabilidad del sistema.</li>
 * </ul>
 */
public class ControladorExcursiones {

    private static final Logger logger = LoggerFactory.getLogger(ControladorExcursiones.class);

    private final SistemaExcursionista sistema;
    private final VistaExcursiones vistaExcursiones;
    private final ExcursionDAO excursionDAO;

    /**
     * Constructor del ControladorExcursiones.
     *
     * @param sistema          Modelo del sistema excursionista.
     * @param vistaExcursiones Vista para interactuar con el usuario sobre excursiones.
     * @throws IllegalArgumentException Si alguno de los parámetros es {@code null}.
     */
    public ControladorExcursiones(SistemaExcursionista sistema, VistaExcursiones vistaExcursiones) {
        if (sistema == null || vistaExcursiones == null) {
            logger.error("Intento de inicializar ControladorExcursiones con parámetros nulos.");
            throw new IllegalArgumentException("El sistema y la vista no pueden ser nulos.");
        }
        this.sistema = sistema;
        this.vistaExcursiones = vistaExcursiones;
        this.excursionDAO = new ExcursionDAOImpl();
        logger.info("ControladorExcursiones inicializado correctamente.");
    }

    /**
     * Agrega una nueva excursión al sistema.
     *
     * <p>Este método solicita los datos al usuario, los valida, y los guarda
     * en la base de datos mediante {@link ExcursionDAO}.</p>
     */
    public void agregarExcursion() {
        try {
            logger.info("Iniciando proceso para agregar una nueva excursión.");

            String descripcion = vistaExcursiones.pedirTexto("Introduce descripción:");
            if (descripcion == null || descripcion.trim().isEmpty()) {
                vistaExcursiones.mostrarMensaje("La descripción no puede estar vacía.");
                logger.warn("Descripción vacía proporcionada por el usuario.");
                return;
            }

            LocalDate fecha = vistaExcursiones.pedirFecha("Introduce la fecha (dd/MM/yyyy):");
            if (fecha.isBefore(LocalDate.now())) {
                vistaExcursiones.mostrarMensaje("La fecha no puede estar en el pasado.");
                logger.warn("Fecha inválida proporcionada: {}", fecha);
                return;
            }

            int numeroDias = Integer.parseInt(vistaExcursiones.pedirTexto("Introduce el número de días:"));
            if (numeroDias <= 0 || numeroDias > 365) {
                vistaExcursiones.mostrarMensaje("El número de días debe ser mayor a 0 y menor o igual a 365.");
                logger.warn("Número de días inválido proporcionado: {}", numeroDias);
                return;
            }

            float precio = Float.parseFloat(vistaExcursiones.pedirTexto("Introduce el precio:"));
            if (precio < 0 || precio > 10_000) {
                vistaExcursiones.mostrarMensaje("El precio debe ser mayor o igual a 0 y menor o igual a 10,000.");
                logger.warn("Precio inválido proporcionado: {}", precio);
                return;
            }

            Excursion excursion = new Excursion(0, descripcion, fecha, numeroDias, precio);
            logger.debug("Datos de la excursión a agregar: {}", excursion);

            excursionDAO.save(excursion);
            vistaExcursiones.mostrarMensaje("Excursión agregada correctamente.");
            logger.info("Excursión agregada exitosamente: {}", excursion);

        } catch (NumberFormatException e) {
            vistaExcursiones.mostrarMensaje("Los valores numéricos deben ser válidos.");
            logger.warn("Entrada inválida proporcionada por el usuario: {}", e.getMessage());
        } catch (Exception e) {
            vistaExcursiones.mostrarMensaje("Error al agregar excursión: " + e.getMessage());
            logger.error("Error inesperado al agregar excursión.", e);
        }
    }

    /**
     * Filtra y muestra excursiones en un rango de fechas especificado por el usuario.
     */
    public void filtrarExcursionesEntreFechas() {
        try {
            logger.info("Iniciando proceso de filtrado de excursiones por rango de fechas.");

            LocalDate fechaInicio = vistaExcursiones.pedirFecha("Introduce la fecha de inicio (dd/MM/yyyy):");
            LocalDate fechaFin = vistaExcursiones.pedirFecha("Introduce la fecha de fin (dd/MM/yyyy):");

            if (fechaInicio.isAfter(fechaFin)) {
                vistaExcursiones.mostrarMensaje("La fecha de inicio no puede ser posterior a la fecha de fin.");
                logger.warn("Rango de fechas inconsistente: Inicio {} Fin {}", fechaInicio, fechaFin);
                return;
            }

            List<Excursion> excursionesFiltradas = excursionDAO.findAll().stream()
                    .filter(exc -> !exc.getFechaExcursion().isBefore(fechaInicio) &&
                            !exc.getFechaExcursion().isAfter(fechaFin))
                    .toList();

            if (excursionesFiltradas.isEmpty()) {
                vistaExcursiones.mostrarMensaje("No se encontraron excursiones en el rango especificado.");
                logger.info("Sin resultados para el rango: Inicio {} Fin {}", fechaInicio, fechaFin);
            } else {
                excursionesFiltradas.forEach(exc -> vistaExcursiones.mostrarMensaje(exc.toString()));
                logger.info("Se encontraron {} excursiones en el rango: Inicio {} Fin {}",
                        excursionesFiltradas.size(), fechaInicio, fechaFin);
            }
        } catch (Exception e) {
            vistaExcursiones.mostrarMensaje("Error al filtrar excursiones: " + e.getMessage());
            logger.error("Error inesperado al filtrar excursiones.", e);
        }
    }

    /**
     * Muestra los socios inscritos en una excursión específica.
     *
     * <p>Solicita el ID de la excursión al usuario y muestra la lista de socios inscritos.</p>
     */
    public void mostrarSociosInscritos() {
        try {
            logger.info("Iniciando proceso para mostrar socios inscritos en una excursión.");

            int idExcursion = Integer.parseInt(vistaExcursiones.pedirTexto("Introduce el ID de la excursión:"));

            Excursion excursion = excursionDAO.findById(idExcursion);
            if (excursion == null) {
                vistaExcursiones.mostrarMensaje("No se encontró ninguna excursión con ese ID.");
                logger.warn("Excursión no encontrada con ID: {}", idExcursion);
                return;
            }

            logger.debug("Excursión encontrada: {}", excursion);

            List<org.thejavengers.modelo.Socio> socios = sistema.listarSociosEnExcursion(idExcursion);
            if (socios.isEmpty()) {
                vistaExcursiones.mostrarMensaje("No hay socios inscritos en esta excursión.");
                logger.info("No hay socios inscritos en la excursión con ID: {}", idExcursion);
            } else {
                socios.forEach(socio -> vistaExcursiones.mostrarMensaje(socio.toString()));
                logger.info("Se encontraron {} socios inscritos en la excursión con ID: {}", socios.size(), idExcursion);
            }
        } catch (NumberFormatException e) {
            vistaExcursiones.mostrarMensaje("El ID debe ser un número válido.");
            logger.warn("Entrada inválida proporcionada por el usuario: {}", e.getMessage());
        } catch (Exception e) {
            vistaExcursiones.mostrarMensaje("Error al mostrar socios inscritos: " + e.getMessage());
            logger.error("Error inesperado al mostrar socios inscritos.", e);
        }
    }
}
