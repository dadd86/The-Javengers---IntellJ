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
import java.util.stream.Collectors;

/**
 * Controlador para gestionar las excursiones en el sistema excursionista.
 * Este controlador interactúa con el modelo {@link SistemaExcursionista} y la vista {@link VistaExcursiones}
 * para realizar operaciones CRUD sobre excursiones utilizando Hibernate.
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
     * @throws IllegalArgumentException Si los parámetros son nulos.
     */
    public ControladorExcursiones(SistemaExcursionista sistema, VistaExcursiones vistaExcursiones) {
        if (sistema == null || vistaExcursiones == null) {
            throw new IllegalArgumentException("El sistema y la vista no pueden ser nulos.");
        }
        this.sistema = sistema;
        this.vistaExcursiones = vistaExcursiones;
        this.excursionDAO = new ExcursionDAOImpl(); // Instancia del DAO de excursiones
    }

    /**
     * Agrega una nueva excursión al sistema.
     * Los datos son solicitados al usuario y se valida que sean correctos antes de guardar.
     */
    public void agregarExcursion() {
        try {
            String descripcion = vistaExcursiones.pedirTexto("Introduce descripción:");
            if (descripcion == null || descripcion.trim().isEmpty()) {
                vistaExcursiones.mostrarMensaje("La descripción no puede estar vacía.");
                logger.warn("La descripción proporcionada está vacía.");
                return;
            }

            LocalDate fecha = vistaExcursiones.pedirFecha("Introduce la fecha (dd/MM/yyyy):");
            if (fecha.isBefore(LocalDate.now())) {
                vistaExcursiones.mostrarMensaje("La fecha no puede estar en el pasado.");
                logger.warn("Fecha proporcionada inválida: {}", fecha);
                return;
            }

            int numeroDias = Integer.parseInt(vistaExcursiones.pedirTexto("Introduce el número de días:"));
            if (numeroDias <= 0 || numeroDias > 365) {
                vistaExcursiones.mostrarMensaje("El número de días debe ser mayor que 0 y menor o igual a 365.");
                logger.warn("Número de días proporcionado inválido: {}", numeroDias);
                return;
            }

            float precio = Float.parseFloat(vistaExcursiones.pedirTexto("Introduce el precio:"));
            if (precio < 0 || precio > 10_000) {
                vistaExcursiones.mostrarMensaje("El precio debe ser mayor o igual a 0 y menor o igual a 10,000.");
                logger.warn("Precio proporcionado inválido: {}", precio);
                return;
            }

            Excursion excursion = new Excursion(0, descripcion, fecha, numeroDias, precio);
            excursionDAO.save(excursion); // Guardar en la base de datos
            vistaExcursiones.mostrarMensaje("Excursión agregada correctamente.");
            logger.info("Excursión agregada exitosamente: {}", excursion);

        } catch (NumberFormatException e) {
            vistaExcursiones.mostrarMensaje("Los valores numéricos deben ser válidos.");
            logger.warn("Entrada inválida: {}", e.getMessage());
        } catch (Exception e) {
            vistaExcursiones.mostrarMensaje("Error al agregar excursión: " + e.getMessage());
            logger.error("Error al agregar excursión: {}", e.getMessage());
        }
    }

    /**
     * Filtra excursiones que se encuentran dentro de un rango de fechas especificado por el usuario.
     * Los resultados se obtienen del DAO y se muestran al usuario.
     */
    public void filtrarExcursionesEntreFechas() {
        try {
            LocalDate fechaInicio = vistaExcursiones.pedirFecha("Introduce la fecha de inicio (dd/MM/yyyy):");
            LocalDate fechaFin = vistaExcursiones.pedirFecha("Introduce la fecha de fin (dd/MM/yyyy):");

            if (fechaInicio.isAfter(fechaFin)) {
                vistaExcursiones.mostrarMensaje("La fecha de inicio no puede ser posterior a la fecha de fin.");
                logger.warn("Fechas inconsistentes: Inicio {} Fin {}", fechaInicio, fechaFin);
                return;
            }

            List<Excursion> excursionesFiltradas = excursionDAO.findAll().stream()
                    .filter(exc -> !exc.getFechaExcursion().isBefore(fechaInicio) &&
                            !exc.getFechaExcursion().isAfter(fechaFin))
                    .collect(Collectors.toList());

            if (excursionesFiltradas.isEmpty()) {
                vistaExcursiones.mostrarMensaje("No se encontraron excursiones en el rango especificado.");
                logger.info("No se encontraron excursiones entre las fechas {} y {}", fechaInicio, fechaFin);
            } else {
                excursionesFiltradas.forEach(exc -> vistaExcursiones.mostrarMensaje(exc.toString()));
                logger.info("Se encontraron {} excursiones entre las fechas {} y {}", excursionesFiltradas.size(), fechaInicio, fechaFin);
            }
        } catch (Exception e) {
            vistaExcursiones.mostrarMensaje("Error al filtrar excursiones: " + e.getMessage());
            logger.error("Error al filtrar excursiones: {}", e.getMessage());
        }
    }

    /**
     * Muestra los socios inscritos en una excursión específica.
     * Solicita el ID de la excursión al usuario y muestra los datos correspondientes.
     */
    public void mostrarSociosInscritos() {
        try {
            int idExcursion = Integer.parseInt(vistaExcursiones.pedirTexto("Introduce el ID de la excursión:"));

            Excursion excursion = excursionDAO.findById(idExcursion);
            if (excursion == null) {
                vistaExcursiones.mostrarMensaje("No se encontró ninguna excursión con ese ID.");
                logger.warn("Excursión no encontrada con ID: {}", idExcursion);
                return;
            }

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
            logger.warn("Entrada inválida: {}", e.getMessage());
        } catch (Exception e) {
            vistaExcursiones.mostrarMensaje("Error al mostrar socios inscritos: " + e.getMessage());
            logger.error("Error al mostrar socios inscritos: {}", e.getMessage());
        }
    }
}
