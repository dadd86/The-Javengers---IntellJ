package TheJavengers.controlador;

import TheJavengers.modelo.SistemaExcursionista;
import TheJavengers.modelo.Excursion;
import TheJavengers.vista.VistaExcursiones;
import TheJavengers.Excepciones.ExcursionYaExisteException;

import java.time.LocalDate;

public class ControladorExcursiones {
    private SistemaExcursionista sistema;
    private VistaExcursiones vistaExcursiones;

    public ControladorExcursiones(SistemaExcursionista sistema, VistaExcursiones vistaExcursiones) {
        this.sistema = sistema;
        this.vistaExcursiones = vistaExcursiones;
    }

    public void agregarExcursion() {
        String idExcursion = vistaExcursiones.pedirTexto("Introduce ID de la excursión:");
        String descripcion = vistaExcursiones.pedirTexto("Introduce descripción:");
        LocalDate fecha = vistaExcursiones.pedirFecha("Introduce la fecha (dd/MM/yyyy):");
        int numeroDias = Integer.parseInt(vistaExcursiones.pedirTexto("Introduce el número de días:"));
        float precio = Float.parseFloat(vistaExcursiones.pedirTexto("Introduce el precio:"));

        Excursion excursion = new Excursion(idExcursion, descripcion, fecha, numeroDias, precio);

        try {
            sistema.registrarExcursion(excursion);
            vistaExcursiones.mostrarMensaje("Excursión agregada correctamente.");
        } catch (ExcursionYaExisteException e) {
            vistaExcursiones.mostrarMensaje(e.getMessage());
        }
    }

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

    public void mostrarSociosInscritos() {
        String idExcursion = vistaExcursiones.pedirTexto("Introduce el ID de la excursión:");

        try {
            var sociosInscritos = sistema.listarSociosEnExcursion(idExcursion);
            if (sociosInscritos.isEmpty()) {
                vistaExcursiones.mostrarMensaje("No hay socios inscritos en la excursión.");
            } else {
                sociosInscritos.forEach(socio -> vistaExcursiones.mostrarMensaje(socio.toString()));
            }
        } catch (Exception e) {
            vistaExcursiones.mostrarMensaje(e.getMessage());
        }
    }
}
