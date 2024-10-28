package org.thejavengers;

import org.thejavengers.modelo.SistemaExcursionista;
import org.thejavengers.vista.VistaExcursionista;
import org.thejavengers.vista.VistaSocios;
import org.thejavengers.vista.VistaExcursiones;
import org.thejavengers.vista.VistaInscripciones;
import org.thejavengers.controlador.ControladorSocios;
import org.thejavengers.controlador.ControladorExcursiones;
import org.thejavengers.controlador.ControladorInscripciones;

/**
 * Clase principal (Main) del sistema de gestión de excursiones.
 * Se encarga de inicializar el modelo, vistas y controladores, y gestionar
 * la interacción principal con el usuario a través de menús.
 * Implementa un bucle principal para que el usuario pueda navegar entre las
 * diferentes funcionalidades del sistema.
 */
public class Main {

    /**
     * Método principal (punto de entrada) del sistema.
     * Inicializa el modelo, las vistas y los controladores, y ejecuta un bucle
     * que permite al usuario seleccionar opciones del menú principal.
     *
     * @param args Los argumentos de línea de comando (no se utilizan).
     */
    public static void main(String[] args) {
        // Inicialización del modelo (lógica del sistema)
        SistemaExcursionista sistema = new SistemaExcursionista();

        // Inicialización de las vistas (interfaz de usuario)
        VistaExcursionista vistaPrincipal = new VistaExcursionista();
        VistaSocios vistaSocios = new VistaSocios();
        VistaExcursiones vistaExcursiones = new VistaExcursiones();
        VistaInscripciones vistaInscripciones = new VistaInscripciones();

        // Inicialización de los controladores (intermediarios entre el modelo y las vistas)
        ControladorSocios controladorSocios = new ControladorSocios(sistema, vistaSocios);
        ControladorExcursiones controladorExcursiones = new ControladorExcursiones(sistema, vistaExcursiones);
        ControladorInscripciones controladorInscripciones = new ControladorInscripciones(sistema, vistaInscripciones);

        // Bucle principal que mantiene el programa en ejecución hasta que el usuario decida salir
        boolean salir = false;

        // Bucle del menú principal
        while (!salir) {
            // Mostrar el menú principal y obtener la opción seleccionada
            int opcionPrincipal = vistaPrincipal.mostrarMenuPrincipal();

            // Evaluar la opción seleccionada y ejecutar la funcionalidad correspondiente
            switch (opcionPrincipal) {
                case 1:
                    // Gestionar socios
                    gestionarSocios(controladorSocios, vistaSocios);
                    break;
                case 2:
                    // Gestionar excursiones
                    gestionarExcursiones(controladorExcursiones, vistaExcursiones);
                    break;
                case 3:
                    // Gestionar inscripciones
                    gestionarInscripciones(controladorInscripciones, vistaInscripciones);
                    break;
                case 4:
                    // Salir del programa
                    salir = true;
                    break;
                default:
                    // Manejar opción no válida
                    vistaPrincipal.mostrarMensaje("Opción no válida.");
            }
        }
    }

    /**
     * Metodo auxiliar que gestiona la interacción con los socios.
     * Ejecuta un bucle secundario donde el usuario puede agregar, eliminar
     * o modificar socios, o regresar al menú principal.
     *
     * @param controladorSocios El controlador encargado de gestionar los socios.
     * @param vistaSocios La vista que permite la interacción con los datos de los socios.
     */
    private static void gestionarSocios(ControladorSocios controladorSocios, VistaSocios vistaSocios) {
        boolean volver = false;

        // Bucle para el submenú de gestión de socios
        while (!volver) {
            int opcionSocios = vistaSocios.mostrarMenuSocios();

            // Evaluar la opción seleccionada en el menú de socios
            switch (opcionSocios) {
                case 1:
                    controladorSocios.agregarSocio();
                    break;
                case 2:
                    controladorSocios.eliminarSocio();
                    break;
                case 3:
                    controladorSocios.modificarSeguroSocio();
                    break;
                case 4:
                    controladorSocios.mostrarFacturaMensual();
                    break;
                case 5:
                    volver = true;  // Volver al menú principal
                    break;
                default:
                    vistaSocios.mostrarMensaje("Opción no válida.");
            }
        }
    }

    /**
     * Metodo auxiliar que gestiona la interacción con las excursiones.
     * Ejecuta un bucle secundario donde el usuario puede agregar o gestionar excursiones,
     * o regresar al menú principal.
     *
     * @param controladorExcursiones El controlador encargado de gestionar las excursiones.
     * @param vistaExcursiones La vista que permite la interacción con los datos de las excursiones.
     */
    private static void gestionarExcursiones(ControladorExcursiones controladorExcursiones, VistaExcursiones vistaExcursiones) {
        boolean volver = false;

        // Bucle para el submenú de gestión de excursiones
        while (!volver) {
            int opcionExcursiones = vistaExcursiones.mostrarMenuExcursiones();

            // Evaluar la opción seleccionada en el menú de excursiones
            switch (opcionExcursiones) {
                case 1:
                    controladorExcursiones.agregarExcursion();
                    break;
                case 2:
                    controladorExcursiones.filtrarExcursionesEntreFechas();
                    break;
                case 3:
                    controladorExcursiones.mostrarSociosInscritos();
                    break;
                case 4:
                    volver = true;  // Volver al menú principal
                    break;
                default:
                    vistaExcursiones.mostrarMensaje("Opción no válida.");
            }
        }
    }

    /**
     * Metodo auxiliar que gestiona la interacción con las inscripciones.
     * Ejecuta un bucle secundario donde el usuario puede inscribir socios en excursiones,
     * eliminar inscripciones o filtrar inscripciones, o regresar al menú principal.
     *
     * @param controladorInscripciones El controlador encargado de gestionar las inscripciones.
     * @param vistaInscripciones La vista que permite la interacción con los datos de las inscripciones.
     */
    private static void gestionarInscripciones(ControladorInscripciones controladorInscripciones, VistaInscripciones vistaInscripciones) {
        boolean volver = false;

        // Bucle para el submenú de gestión de inscripciones
        while (!volver) {
            int opcionInscripciones = vistaInscripciones.mostrarMenuInscripciones();

            // Evaluar la opción seleccionada en el menú de inscripciones
            switch (opcionInscripciones) {
                case 1:
                    controladorInscripciones.inscribirSocioEnExcursion();
                    break;
                case 2:
                    controladorInscripciones.eliminarInscripcion();
                    break;
                case 3:
                    controladorInscripciones.mostrarInscripcionesConFiltro();
                    break;
                case 4:
                    volver = true;  // Volver al menú principal
                    break;
                default:
                    vistaInscripciones.mostrarMensaje("Opción no válida.");
            }
        }
    }
}
