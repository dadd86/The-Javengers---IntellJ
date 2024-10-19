package TheJavengers.controlador;

import TheJavengers.modelo.*;
import TheJavengers.vista.VistaInscripciones;
import TheJavengers.Excepciones.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que actúa como controlador para gestionar las inscripciones en excursiones del sistema.
 * Se encarga de interactuar entre la vista (VistaInscripciones) y el modelo (SistemaExcursionista),
 * gestionando las operaciones relacionadas con inscribir socios en excursiones y eliminar inscripciones.
 */
public class ControladorInscripciones {

    // Instancias del modelo y la vista
    private final SistemaExcursionista sistema;
    private final VistaInscripciones vistaInscripciones;
    private Controlador<Inscripcion> controladorInscripciones;

    /**
     * Constructor de la clase ControladorInscripciones.
     *
     * @param sistema            El sistema que gestiona las excursiones y socios.
     * @param vistaInscripciones La vista encargada de mostrar la interfaz de inscripciones.
     * @throws IllegalArgumentException Si alguno de los parámetros es nulo.
     */
    public ControladorInscripciones(SistemaExcursionista sistema, VistaInscripciones vistaInscripciones) {
        if (sistema == null || vistaInscripciones == null) {
            throw new IllegalArgumentException("El sistema y la vista no pueden ser nulos.");
        }
        this.controladorInscripciones = new Controlador<>();
        this.sistema = sistema;
        this.vistaInscripciones = vistaInscripciones;
    }

    /**
     * Inscribe un socio en una excursión. Solicita al usuario el ID del socio y el ID de la excursión,
     * luego se intenta realizar la inscripción. Si el socio o la excursión no se encuentran,
     * se maneja la excepción y se informa al usuario.
     */
    public void inscribirSocioEnExcursion() {
        // Solicitar los datos de inscripción al usuario
        String idSocio = vistaInscripciones.pedirTexto("Introduce el ID del socio:");
        if (idSocio == null || idSocio.trim().isEmpty()) {
            vistaInscripciones.mostrarMensaje("El ID del socio no puede estar vacío.");
            return;
        }

        String idExcursion = vistaInscripciones.pedirTexto("Introduce el ID de la excursión:");
        if (idExcursion == null || idExcursion.trim().isEmpty()) {
            vistaInscripciones.mostrarMensaje("El ID de la excursión no puede estar vacío.");
            return;
        }

        // Intentar inscribir al socio en la excursión
        try {
            sistema.inscribirSocioEnExcursion(idSocio, idExcursion);
            controladorInscripciones.agregarElemento(
                    new Inscripcion(controladorInscripciones.obtenerElementos().size() + 1,
                            sistema.buscarSocio(idSocio),
                            sistema.buscarExcursion(idExcursion),
                            LocalDate.now())
            );
            vistaInscripciones.mostrarMensaje("Socio inscrito correctamente en la excursion.");
        } catch (SocioNoEncontradoException | ExcursionNoEncontradaException e) {
            // Manejar excepciones específicas para casos de socio o excursión no encontrados
            vistaInscripciones.mostrarMensaje(e.getMessage());
        }
    }

    /**
     * Elimina una inscripción de una excursión. Solicita el ID de la inscripción al usuario,
     * verifica que la inscripción aún pueda eliminarse y, en caso de éxito, la elimina.
     * Si no se encuentra la inscripción o no es posible eliminarla, se informa al usuario.
     */
    public void eliminarInscripcion() {
        try {
            // Pedir el ID de la inscripción y validarlo
            int idInscripcion = Integer.parseInt(vistaInscripciones.pedirTexto("Introduce el ID de la inscripción:"));
            // Intentar eliminar la inscripción
            sistema.eliminarInscripcion(idInscripcion, LocalDate.now());
            controladorInscripciones.eliminarElemento(buscarInscripcionPorId(idInscripcion));
            vistaInscripciones.mostrarMensaje("Inscripción eliminada correctamente.");
        } catch (NumberFormatException e) {
            // Manejar el caso en el que el ID de inscripción no es un número válido
            vistaInscripciones.mostrarMensaje("El ID de inscripción debe ser un número válido.");
        } catch (InscripcionNoEncontradaException e) {
            // Manejar la excepción en caso de que no se encuentre la inscripción
            vistaInscripciones.mostrarMensaje(e.getMessage());
        }
    }

    /**
     * Muestra las inscripciones filtradas por el ID del socio y/o un rango de fechas.
     * Solicita al usuario los parámetros de filtro y muestra las inscripciones que coincidan.
     * Si no se encuentran inscripciones, se informa al usuario.
     */
    public void mostrarInscripcionesConFiltro() {
        // Pedir los parámetros de filtro al usuario
        String idSocio = vistaInscripciones.pedirTexto("Introduce el ID del socio (deja vacío para no filtrar por socio):");
        LocalDate fechaInicio = vistaInscripciones.pedirFecha("Introduce la fecha de inicio (yyyy-MM-dd):");
        LocalDate fechaFin = vistaInscripciones.pedirFecha("Introduce la fecha de fin (yyyy-MM-dd):");

        // Obtener y mostrar las inscripciones que coinciden con los filtros
        List<Inscripcion> inscripciones = sistema.mostrarInscripcionesFiltradas(idSocio, fechaInicio, fechaFin);

        // Mostrar las inscripciones filtradas
        System.out.println("Inscripciones filtradas:");
        for (Inscripcion ins : inscripciones) {
            System.out.println(ins);
        }
    }

    /**
     * Busca una inscripción en la lista actual de inscripciones por su ID.
     *
     * @param id el ID de la inscripción a buscar
     * @return la inscripción encontrada o null si no se encuentra
     */
    private Inscripcion buscarInscripcionPorId(int id) {
        return controladorInscripciones.obtenerElementos()
                .stream()
                .filter(inscripcion -> inscripcion.getIdInscripcion() == id)
                .findFirst()
                .orElse(null);
    }
}
