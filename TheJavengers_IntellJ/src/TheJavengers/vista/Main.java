package TheJavengers;

import TheJavengers.vista.*;
import TheJavengers.controlador.*;
import TheJavengers.modelo.SistemaExcursionista;

public class Main {

    public static void main(String[] args) {
        SistemaExcursionista sistema = new SistemaExcursionista();

        // Vistas
        VistaExcursionista vistaPrincipal = new VistaExcursionista();
        VistaSocios vistaSocios = new VistaSocios();
        VistaExcursiones vistaExcursiones = new VistaExcursiones();
        VistaInscripciones vistaInscripciones = new VistaInscripciones();

        // Controladores
        ControladorSocios controladorSocios = new ControladorSocios(sistema, vistaSocios);
        ControladorExcursiones controladorExcursiones = new ControladorExcursiones(sistema, vistaExcursiones);
        ControladorInscripciones controladorInscripciones = new ControladorInscripciones(sistema, vistaInscripciones);

        boolean salir = false;

        while (!salir) {
            int opcionPrincipal = vistaPrincipal.mostrarMenuPrincipal();

            switch (opcionPrincipal) {
                case 1:
                    gestionarSocios(controladorSocios, vistaSocios);
                    break;
                case 2:
                    gestionarExcursiones(controladorExcursiones, vistaExcursiones);
                    break;
                case 3:
                    gestionarInscripciones(controladorInscripciones, vistaInscripciones);
                    break;
                case 4:
                    salir = true;
                    break;
                default:
                    vistaPrincipal.mostrarMensaje("Opción no válida.");
            }
        }
    }

    private static void gestionarSocios(ControladorSocios controladorSocios, VistaSocios vistaSocios) {
        boolean volver = false;

        while (!volver) {
            int opcionSocios = vistaSocios.mostrarMenuSocios();

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
                    controladorSocios.buscarSocios();
                case 6:
                    volver = true;
                    break;
                default:
                    vistaSocios.mostrarMensaje("Opción no válida.");
            }
        }
    }

    private static void gestionarExcursiones(ControladorExcursiones controladorExcursiones, VistaExcursiones vistaExcursiones) {
        boolean volver = false;

        while (!volver) {
            int opcionExcursiones = vistaExcursiones.mostrarMenuExcursiones();

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
                    volver = true;
                    break;
                default:
                    vistaExcursiones.mostrarMensaje("Opción no válida.");
            }
        }
    }

    private static void gestionarInscripciones(ControladorInscripciones controladorInscripciones, VistaInscripciones vistaInscripciones) {
        boolean volver = false;

        while (!volver) {
            int opcionInscripciones = vistaInscripciones.mostrarMenuInscripciones();

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
                    volver = true;
                    break;
                default:
                    vistaInscripciones.mostrarMensaje("Opción no válida.");
            }
        }
    }
}
