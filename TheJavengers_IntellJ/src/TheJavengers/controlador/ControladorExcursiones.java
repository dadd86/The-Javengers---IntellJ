package TheJavengers.controlador;

import TheJavengers.modelo.SistemaExcursionista;
import TheJavengers.modelo.Excursion;
import TheJavengers.vista.VistaExcursiones;
import TheJavengers.Excepciones.ExcursionYaExisteException;

import java.time.LocalDate;

/**
 * Clase que actúa como controlador para gestionar las excursiones en el sistema excursionista.
 * Esta clase maneja las interacciones entre el modelo (SistemaExcursionista) y la vista (VistaExcursiones),
 * permitiendo agregar, filtrar y mostrar excursiones, así como listar los socios inscritos en una excursión.
 */
public class ControladorExcursiones {
    // Instancias de la vista y el modelo
    private final SistemaExcursionista sistema;
    private final VistaExcursiones vistaExcursiones;

    /**
     * Constructor de la clase ControladorExcursiones.
     *
     * @param sistema          El sistema de gestión de excursiones que actúa como modelo.
     * @param vistaExcursiones La vista encargada de interactuar con el usuario.
     * @throws IllegalArgumentException Si alguno de los parámetros es nulo.
     */
    public ControladorExcursiones(SistemaExcursionista sistema, VistaExcursiones vistaExcursiones) {
        if (sistema == null || vistaExcursiones == null) {
            throw new IllegalArgumentException("El sistema y la vista no pueden ser nulos.");
        }
        this.sistema = sistema;
        this.vistaExcursiones = vistaExcursiones;
    }

    /**
     * Método para agregar una nueva excursión al sistema.
     * Solicita los datos de la excursión a la vista y los registra en el sistema.
     * Valida los datos de entrada y maneja las excepciones que puedan surgir, como
     * que la excursión ya exista.
     */
    public void agregarExcursion() {
        // Pedir datos de la excursión
        String idExcursion = vistaExcursiones.pedirTexto("Introduce ID de la excursión:");
        if (idExcursion == null || idExcursion.trim().isEmpty()) {
            vistaExcursiones.mostrarMensaje("El ID de la excursión no puede estar vacío.");
            return;
        }

        String descripcion = vistaExcursiones.pedirTexto("Introduce descripción:");
        if (descripcion == null || descripcion.trim().isEmpty()) {
            vistaExcursiones.mostrarMensaje("La descripción no puede estar vacía.");
            return;
        }

        // Pedir la fecha y manejar posibles errores de formato
        LocalDate fecha;
        try {
            fecha = vistaExcursiones.pedirFecha("Introduce la fecha (dd/MM/yyyy):");
        } catch (Exception e) {
            vistaExcursiones.mostrarMensaje("Formato de fecha no válido. Introduzca una fecha válida.");
            return;
        }

        // Validar el número de días y el precio
        int numeroDias;
        try {
            numeroDias = Integer.parseInt(vistaExcursiones.pedirTexto("Introduce el número de días:"));
            if (numeroDias <= 0) {
                vistaExcursiones.mostrarMensaje("El número de días debe ser mayor que 0.");
                return;
            }
        } catch (NumberFormatException e) {
            vistaExcursiones.mostrarMensaje("El número de días debe ser un valor entero válido.");
            return;
        }

        float precio;
        try {
            precio = Float.parseFloat(vistaExcursiones.pedirTexto("Introduce el precio:"));
            if (precio < 0) {
                vistaExcursiones.mostrarMensaje("El precio debe ser un valor positivo.");
                return;
            }
        } catch (NumberFormatException e) {
            vistaExcursiones.mostrarMensaje("El precio debe ser un valor numérico válido.");
            return;
        }

        // Crear la excursión y registrarla en el sistema
        Excursion excursion = new Excursion(idExcursion, descripcion, fecha, numeroDias, precio);
        try {
            sistema.registrarExcursion(excursion);
            vistaExcursiones.mostrarMensaje("Excursión agregada correctamente.");
        } catch (ExcursionYaExisteException e) {
            vistaExcursiones.mostrarMensaje(e.getMessage());
        }
    }

    /**
     * Método para filtrar y mostrar las excursiones que ocurren dentro de un rango de fechas.
     * Solicita al usuario las fechas de inicio y fin, valida las entradas, y muestra las excursiones
     * que ocurren en ese rango de fechas. Si no hay excursiones, muestra un mensaje informativo.
     */
    public void filtrarExcursionesEntreFechas() {
        try {
            // Solicitar fechas al usuario
            LocalDate fechaInicio = vistaExcursiones.pedirFecha("Introduce la fecha de inicio (dd/MM/yyyy):");
            LocalDate fechaFin = vistaExcursiones.pedirFecha("Introduce la fecha de fin (dd/MM/yyyy):");

            var excursionesFiltradas = sistema.mostrarExcursionesPorFechas(fechaInicio, fechaFin);

            if (excursionesFiltradas.isEmpty()) {
                vistaExcursiones.mostrarMensaje("No se encontraron excursiones en el rango de fechas especificado.");
            } else {
                excursionesFiltradas.forEach(excursion -> vistaExcursiones.mostrarMensaje(excursion.toString()));
            }
        } catch (Exception e) {
            vistaExcursiones.mostrarMensaje("Error al filtrar excursiones: " + e.getMessage());
        }
    }

    /**
     * Método para mostrar los socios inscritos en una excursión específica.
     * Solicita el ID de la excursión al usuario y muestra los datos de los socios inscritos.
     * Si no hay socios inscritos o si ocurre un error, se muestra un mensaje apropiado.
     */
    public void mostrarSociosInscritos() {
        try {
            String idExcursion = vistaExcursiones.pedirTexto("Introduce el ID de la excursión:");
            if (idExcursion == null || idExcursion.trim().isEmpty()) {
                vistaExcursiones.mostrarMensaje("El ID de la excursión no puede estar vacío.");
                return;
            }

            var sociosInscritos = sistema.listarSociosEnExcursion(idExcursion);
            if (sociosInscritos.isEmpty()) {
                vistaExcursiones.mostrarMensaje("No hay socios inscritos en la excursión.");
            } else {
                sociosInscritos.forEach(socio -> vistaExcursiones.mostrarMensaje(socio.toString()));
            }
        } catch (Exception e) {
            vistaExcursiones.mostrarMensaje("Error al mostrar los socios inscritos: " + e.getMessage());
        }
    }
}
