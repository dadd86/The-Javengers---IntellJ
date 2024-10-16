package TheJavengers.controlador;

import TheJavengers.modelo.SistemaExcursionista;
import TheJavengers.vista.VistaInscripciones;
import TheJavengers.Excepciones.*;

import java.time.LocalDate;

public class ControladorInscripciones {

    private SistemaExcursionista sistema;
    private VistaInscripciones vistaInscripciones;

    public ControladorInscripciones(SistemaExcursionista sistema, VistaInscripciones vistaInscripciones) {
        this.sistema = sistema;
        this.vistaInscripciones = vistaInscripciones;
    }

    public void inscribirSocioEnExcursion() {
        String idSocio = vistaInscripciones.pedirTexto("Introduce el ID del socio:");
        String idExcursion = vistaInscripciones.pedirTexto("Introduce el ID de la excursión:");

        try {
            sistema.inscribirSocioEnExcursion(idSocio, idExcursion);
            vistaInscripciones.mostrarMensaje("Socio inscrito correctamente en la excursión.");
        } catch (SocioNoEncontradoException | ExcursionNoEncontradaException e) {
            vistaInscripciones.mostrarMensaje(e.getMessage());
        }
    }

    public void eliminarInscripcion() {
        int idInscripcion = Integer.parseInt(vistaInscripciones.pedirTexto("Introduce el ID de la inscripción:"));
        try {
            sistema.eliminarInscripcion(idInscripcion, LocalDate.now());
            vistaInscripciones.mostrarMensaje("Inscripción eliminada correctamente.");
        } catch (InscripcionNoEncontradaException e) {
            vistaInscripciones.mostrarMensaje(e.getMessage());
        }
    }

    public void mostrarInscripcionesConFiltro() {
        String idSocio = vistaInscripciones.pedirTexto("Introduce el ID del socio (deja vacío para no filtrar por socio):");
        LocalDate fechaInicio = vistaInscripciones.pedirFecha("Introduce la fecha de inicio (yyyy-MM-dd):");
        LocalDate fechaFin = vistaInscripciones.pedirFecha("Introduce la fecha de fin (yyyy-MM-dd):");

        var inscripcionesFiltradas = sistema.mostrarInscripcionesFiltradas(idSocio, fechaInicio, fechaFin);

        if (inscripcionesFiltradas.isEmpty()) {
            vistaInscripciones.mostrarMensaje("No se encontraron inscripciones con los filtros especificados.");
        } else {
            inscripcionesFiltradas.forEach(inscripcion -> vistaInscripciones.mostrarMensaje(inscripcion.toString()));
        }
    }
}
