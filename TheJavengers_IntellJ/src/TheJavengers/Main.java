package TheJavengers;

//import TheJavengers.controlador.ControladorExcursionista;
//import TheJavengers.modelo.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
       // ControladorExcursionista controlador = new ControladorExcursionista();
       VistaExcursionista vista = new VistaExcursionista();

        while (true) {
            int opcion = vista.mostrarMenu();

            switch (opcion) {
                case 1:
                    // Añadir socio
                    int tipoSocio = vista.pedirEntero("Ingrese el tipo de socio (1. Estándar, 2. Federado, 3. Infantil):");
                    String idSocio = vista.pedirTexto("Ingrese el ID del socio:");
                    String nombre = vista.pedirTexto("Ingrese el nombre del socio:");
                    String apellidos = vista.pedirTexto("Ingrese los apellidos del socio:");
                    // String mensajeRegistro = controlador.registrarSocio(tipoSocio, idSocio, nombre, apellidos, "", null, "", "");
                    //vista.mostrarMensaje(mensajeRegistro);
                    break;

                case 2:
                    // Mostrar factura mensual por socio
                    String idSocioFactura = vista.pedirTexto("Ingrese el ID del socio para mostrar la factura:");
                    // String factura = controlador.mostrarFacturaMensual(idSocioFactura);
                    // vista.mostrarMensaje(factura);
                    break;

                case 3:
                    // Añadir excursión
                    String idExcursion = vista.pedirTexto("Ingrese el ID de la excursión:");
                    String descripcion = vista.pedirTexto("Ingrese la descripción de la excursión:");
                    LocalDate fecha = vista.pedirFecha("Ingrese la fecha de la excursión (dd/MM/yyyy):");
                    int numeroDias = vista.pedirEntero("Ingrese el número de días de la excursión:");
                    float precio = Float.parseFloat(vista.pedirTexto("Ingrese el precio de inscripción:"));
                    //String mensajeExcursion = controlador.registrarExcursion(idExcursion, descripcion, fecha, numeroDias, precio);
                    //vista.mostrarMensaje(mensajeExcursion);
                    break;

                default:
                    vista.mostrarMensaje("Opción no válida.");
            }
        }
    }
}


