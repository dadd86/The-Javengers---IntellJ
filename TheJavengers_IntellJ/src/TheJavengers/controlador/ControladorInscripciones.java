package TheJavengers.controlador;

import TheJavengers.modelo.SistemaExcursionista;
import TheJavengers.modelo.Inscripcion;
import TheJavengers.vista.VistaInscripciones;
import TheJavengers.Excepciones.*;

import java.time.LocalDate;
import java.util.List;

/**
 * ControladorInscripciones gestiona las interacciones entre la vista de inscripciones y el sistema excursionista.
 */
public class ControladorInscripciones {

    private Controlador<Inscripcion> controladorInscripciones;
    private SistemaExcursionista sistema;
    private VistaInscripciones vistaInscripciones;

    /**
     * Constructor que inicializa el controlador de inscripciones con el sistema y la vista especificada.
     *
     * @param sistema el sistema excursionista que gestiona los datos
     * @param vistaInscripciones la vista que permite la interacción con el usuario para gestionar inscripciones
     */
    public ControladorInscripciones(SistemaExcursionista sistema, VistaInscripciones vistaInscripciones) {
        this.controladorInscripciones = new Controlador<>();
        this.sistema = sistema;
        this.vistaInscripciones = vistaInscripciones;
    }

    /**
     * Solicita los datos de una nueva inscripción a través de la vista y la registra en el sistema.
     * Muestra un mensaje en caso de éxito o si el socio o la excursión no existen.
     */
    public void inscribirSocioEnExcursion() {
        String idSocio = vistaInscripciones.pedirTexto("Introduce el ID del socio:");
        String idExcursion = vistaInscripciones.pedirTexto("Introduce el ID de la excursion:");

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
            vistaInscripciones.mostrarMensaje(e.getMessage());
        }
    }

    /**
     * Solicita el ID de una inscripción y la elimina del sistema si cumple las condiciones.
     * Muestra un mensaje en caso de éxito o si la inscripción no es válida para eliminar.
     */
    public void eliminarInscripcion() {
        int idInscripcion = Integer.parseInt(vistaInscripciones.pedirTexto("Introduce el ID de la inscripcion:"));
        try {
            sistema.eliminarInscripcion(idInscripcion, LocalDate.now());
            controladorInscripciones.eliminarElemento(buscarInscripcionPorId(idInscripcion));
            vistaInscripciones.mostrarMensaje("Inscripcion eliminada correctamente.");
        } catch (InscripcionNoEncontradaException e) {
            vistaInscripciones.mostrarMensaje(e.getMessage());
        }
    }

    /**
     * Muestra las inscripciones filtradas según el ID de socio y un rango de fechas.
     * Muestra un mensaje si no se encuentran inscripciones.
     */
    public void mostrarInscripcionesConFiltro() {
        String idSocio = vistaInscripciones.pedirTexto("Introduce el ID del socio (deja vacio para no filtrar por socio):");
        LocalDate fechaInicio = vistaInscripciones.pedirFecha("Introduce la fecha de inicio (yyyy-MM-dd):");
        LocalDate fechaFin = vistaInscripciones.pedirFecha("Introduce la fecha de fin (yyyy-MM-dd):");

        List<Inscripcion> inscripcionesFiltradas = sistema.mostrarInscripcionesFiltradas(idSocio, fechaInicio, fechaFin);

        if (inscripcionesFiltradas.isEmpty()) {
            vistaInscripciones.mostrarMensaje("No se encontraron inscripciones con los filtros especificados.");
        } else {
            inscripcionesFiltradas.forEach(inscripcion -> vistaInscripciones.mostrarMensaje(inscripcion.toString()));
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
