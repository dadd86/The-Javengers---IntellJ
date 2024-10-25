package TheJavengers.controlador;

import TheJavengers.modelo.*;
import TheJavengers.vista.*;
import TheJavengers.Excepciones.*;

import java.time.LocalDate;

/**
 * ControladorExcursiones gestiona las interacciones entre la vista de excursiones y el sistema excursionista.
 */
public class ControladorExcursiones {
    private SistemaExcursionista sistema;
    private VistaExcursiones vistaExcursiones;

    /**
     * Constructor que inicializa el controlador con el sistema y la vista correspondiente.
     *
     * @param sistema El sistema excursionista que gestiona los datos.
     * @param vistaExcursiones La vista para interaccionar con el usuario en la gestion de excursiones.
     */
    public ControladorExcursiones(SistemaExcursionista sistema, VistaExcursiones vistaExcursiones) {
        this.sistema = sistema;
        this.vistaExcursiones = vistaExcursiones;
    }

    /**
     * Solicita los datos de una nueva excursion a traves de la vista y la registra en el sistema.
     * Muestra un mensaje en caso de exito o si la excursion ya existe.
     */
    public void agregarExcursion() {
        String idExcursion = vistaExcursiones.pedirTexto("Introduce ID de la excursion:");
        String descripcion = vistaExcursiones.pedirTexto("Introduce descripcion:");
        LocalDate fecha = vistaExcursiones.pedirFecha("Introduce la fecha (dd/MM/yyyy):");
        int numeroDias = Integer.parseInt(vistaExcursiones.pedirTexto("Introduce el numero de dias:"));
        float precio = Float.parseFloat(vistaExcursiones.pedirTexto("Introduce el precio:"));

        Excursion excursion = new Excursion(idExcursion, descripcion, fecha, numeroDias, precio);

        try {
            sistema.registrarExcursion(excursion);
            vistaExcursiones.mostrarMensaje("Excursion agregada correctamente.");
        } catch (ExcursionYaExisteException e) {
            vistaExcursiones.mostrarMensaje(e.getMessage());
        }
    }

    /**
     * Filtra las excursiones en el sistema entre dos fechas proporcionadas por el usuario y muestra los resultados.
     * Muestra un mensaje si no se encuentran excursiones.
     */
    public void filtrarExcursionesEntreFechas() {
        LocalDate fechaInicio = vistaExcursiones.pedirFecha("Introduce la fecha de inicio (dd/MM/yyyy):");
        LocalDate fechaFin = vistaExcursiones.pedirFecha("Introduce la fecha de fin (dd/MM/yyyy):");

        var excursionesFiltradas = sistema.mostrarExcursionesPorFechas(fechaInicio, fechaFin);

        if (excursionesFiltradas.isEmpty()) {
            vistaExcursiones.mostrarMensaje("No se encontraron excursiones en el rango de fechas especificado.");
        } else {
            excursionesFiltradas.forEach(excursion -> vistaExcursiones.mostrarMensaje(excursion.toString()));
        }
    }

    /**
     * Muestra la lista de socios inscritos en una excursion especificada por el usuario a traves de su ID.
     * Si no hay socios inscritos, muestra un mensaje informativo.
     */
    public void mostrarSociosInscritos() {
        String idExcursion = vistaExcursiones.pedirTexto("Introduce el ID de la excursion:");

        try {
            var sociosInscritos = sistema.listarSociosEnExcursion(idExcursion);
            if (sociosInscritos.isEmpty()) {
                vistaExcursiones.mostrarMensaje("No hay socios inscritos en la excursion.");
            } else {
                sociosInscritos.forEach(socio -> vistaExcursiones.mostrarMensaje(socio.toString()));
            }
        } catch (Exception e) {
            vistaExcursiones.mostrarMensaje(e.getMessage());
        }
    }
}
