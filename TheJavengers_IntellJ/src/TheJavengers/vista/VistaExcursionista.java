package TheJavengers.vista;

import java.util.Scanner;

public class VistaExcursionista {
    private Scanner scanner;

    public VistaExcursionista() {
        scanner = new Scanner(System.in);
    }

    // Mostrar el menú principal y devolver la opción elegida
    public int mostrarMenuPrincipal() {
        System.out.println("\nSeleccione una opción:");
        System.out.println("1. Gestionar Socios");
        System.out.println("2. Gestionar Excursiones");
        System.out.println("3. Gestionar Inscripciones");
        System.out.println("4. Salir");

        int opcion = scanner.nextInt();
        scanner.nextLine();
        return opcion;
    }

    // Mostrar un mensaje en consola
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
