package TheJavengers;

import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        GestionExcursiones gestionExcursiones = new GestionExcursiones();
        GestionSocios gestionSocios = new GestionSocios();

        // Inicializar la lista de socios y excursiones
        List<Socio> listaSocios = gestionSocios.mostrarSocios(); // Asegúrate de que tienes este método
        List<Excursion> listaExcursiones = gestionExcursiones.mostrarExcursiones();// Asegúrate de que tienes este método

        // Crear la gestión de inscripciones
        GestionInscripciones gestionInscripciones = new GestionInscripciones(listaSocios, listaExcursiones);

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            // Mostrar menú
            System.out.println("----- Menú Principal -----");
            System.out.println("1. Gestionar Excursiones");
            System.out.println("2. Gestionar Socios");
            System.out.println("3. Gestionar Inscripciones");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    // Menú de excursiones
                    gestionarExcursiones(gestionExcursiones, scanner);
                    break;
                case 2:
                    // Menú de socios
                    gestionarSocios(gestionSocios, scanner);
                    break;
                case 3:
                    // Menú de inscripciones
                    gestionarInscripciones(gestionInscripciones, scanner);
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, selecciona una opción del 0 al 3.");
            }
        } while (opcion != 0);

        scanner.close(); // Cerrar el escáner
    }

    private static void gestionarExcursiones(GestionExcursiones gestion, Scanner scanner) {
        int opcionExcursion;
        do {
            System.out.println("----- Menú de Excursiones -----");
            System.out.println("1. Añadir Excursión");
            System.out.println("2. Mostrar Excursiones");
            System.out.println("3. Mostrar Todas las Excursiones"); // Nueva opción
            System.out.println("0. Volver al menú principal");
            System.out.print("Selecciona una opción: ");
            opcionExcursion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcionExcursion) {
                case 1:
                    gestion.agregarExcursion();
                    break;
                case 2:
                    gestion.mostrarExcursiones(); // Este método debe implementar la lógica de filtrado
                    break;
                case 3:
                    gestion.mostrarTodaslasExcursiones(); // Llamar al nuevo método
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, selecciona una opción del 0 al 3.");
            }
        } while (opcionExcursion != 0);
    }


    private static void gestionarSocios(GestionSocios gestionSocios, Scanner scanner) {
        int opcionSocio;
        do {
            System.out.println("----- Menú de Socios -----");
            System.out.println("1. Añadir Socio");
            System.out.println("2. Mostrar Socios");
            System.out.println("3. Mostrar Factura Mensual");
            System.out.println("0. Volver al menú principal");
            System.out.print("Selecciona una opción: ");
            opcionSocio = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcionSocio) {
                case 1:
                    gestionSocios.agregarSocio();
                    break;
                case 2:
                    gestionSocios.mostrarSocios();
                    break;
                case 3:
                    gestionSocios.mostrarFacturaMensual();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, selecciona una opción del 0 al 3.");
            }
        } while (opcionSocio != 0);
    }

    private static void gestionarInscripciones(GestionInscripciones gestionInscripciones, Scanner scanner) {
        int opcionInscripcion;
        do {
            System.out.println("----- Menú de Inscripciones -----");
            System.out.println("1. Añadir Inscripción");
            System.out.println("2. Eliminar Inscripción");
            System.out.println("3. Mostrar Inscripciones");
            System.out.println("0. Volver al menú principal");
            System.out.print("Selecciona una opción: ");
            opcionInscripcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcionInscripcion) {
                case 1:
                    gestionInscripciones.agregarInscripcion();
                    break;
                case 2:
                    gestionInscripciones.eliminarInscripcion();
                    break;
                case 3:
                    gestionInscripciones.mostrarInscripciones();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, selecciona una opción del 0 al 3.");
            }
        } while (opcionInscripcion != 0);
    }
}
