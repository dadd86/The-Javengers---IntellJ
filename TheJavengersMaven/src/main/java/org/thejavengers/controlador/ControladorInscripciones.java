package org.thejavengers.controlador;

import org.thejavengers.modelo.Excursion;
import org.thejavengers.modelo.SistemaExcursionista;
import org.thejavengers.modelo.Inscripcion;
import org.thejavengers.Excepciones.SocioNoEncontradoException;
import org.thejavengers.Excepciones.ExcursionNoEncontradaException;
import org.thejavengers.Excepciones.InscripcionNoEncontradaException;
import org.thejavengers.modelo.Socio;
import org.thejavengers.vista.VistaInscripciones;

import java.time.LocalDate;
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
        String idSocioStr = vistaInscripciones.pedirTexto("Introduce el ID del socio:");
        if (idSocioStr == null || idSocioStr.trim().isEmpty()) {
            vistaInscripciones.mostrarMensaje("El ID del socio no puede estar vacío.");
            return;
        }

        String idExcursionStr = vistaInscripciones.pedirTexto("Introduce el ID de la excursión:");
        if (idExcursionStr == null || idExcursionStr.trim().isEmpty()) {
            vistaInscripciones.mostrarMensaje("El ID de la excursión no puede estar vacío.");
            return;
        }

        int idSocio;
        int idExcursion;

        try {
            // Intentar convertir los valores ingresados a enteros
            idSocio = Integer.parseInt(idSocioStr.trim());
            idExcursion = Integer.parseInt(idExcursionStr.trim());
        } catch (NumberFormatException e) {
            vistaInscripciones.mostrarMensaje("El ID del socio y el ID de la excursión deben ser números válidos.");
            return;
        }

        // Intentar inscribir al socio en la excursión
        try {
            Socio socio = sistema.buscarSocio(String.valueOf(idSocio)); // Cambiado a int
            Excursion excursion = sistema.buscarExcursion(idExcursion); // Cambiado a int

            if (socio == null) {
                throw new SocioNoEncontradoException("Socio no encontrado con el ID: " + idSocio);
            }
            if (excursion == null) {
                throw new ExcursionNoEncontradaException("Excursión no encontrada con el ID: " + idExcursion);
            }

            sistema.inscribirSocioEnExcursion(String.valueOf(idSocio), idExcursion); // Cambiado a int

            // Agregar la inscripción en el controlador
            controladorInscripciones.agregarElemento(
                    new Inscripcion(
                            controladorInscripciones.obtenerElementos().size() + 1,
                            socio,
                            excursion,
                            LocalDate.now()
                    )
            );

            vistaInscripciones.mostrarMensaje("Socio inscrito correctamente en la excursión.");
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
        String idSocioStr = vistaInscripciones.pedirTexto("Introduce el ID del socio (deja vacío para no filtrar por socio):");
        LocalDate fechaInicio = vistaInscripciones.pedirFecha("Introduce la fecha de inicio (dd/MM/yyyy):");
        LocalDate fechaFin = vistaInscripciones.pedirFecha("Introduce la fecha de fin (dd/MM/yyyy):");

        Integer idSocio = null;
        if (idSocioStr != null && !idSocioStr.trim().isEmpty()) {
            try {
                idSocio = Integer.parseInt(idSocioStr);
            } catch (NumberFormatException e) {
                vistaInscripciones.mostrarMensaje("El ID de socio debe ser un número válido.");
                return;
            }
        }

        List<Inscripcion> inscripciones = sistema.mostrarInscripcionesFiltradas(idSocio, fechaInicio, fechaFin);

        if (inscripciones.isEmpty()) {
            vistaInscripciones.mostrarMensaje("No se encontraron inscripciones que coincidan con los filtros.");
        } else {
            inscripciones.forEach(System.out::println);
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
