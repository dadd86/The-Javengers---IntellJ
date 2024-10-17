package TheJavengers.vista;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase que representa la vista principal del sistema excursionista.
 * Permite la interacción del usuario con las opciones principales del sistema
 * para gestionar socios, excursiones e inscripciones.
 */
public class VistaExcursionista {

    // Atributo
    private Scanner scanner;  // Objeto Scanner para capturar las entradas del usuario

    /**
     * Constructor de la clase VistaExcursionista.
     * Inicializa el objeto Scanner para capturar la entrada desde la consola.
     */
    public VistaExcursionista() {
        scanner = new Scanner(System.in);
    }

    /**
     * Muestra el menú principal del sistema y devuelve la opción seleccionada por el usuario.
     * Si el usuario ingresa un valor no válido (como una cadena en lugar de un número),
     * se maneja el error y se le solicita que ingrese nuevamente una opción válida.
     *
     * @return La opción seleccionada por el usuario como entero.
     */
    public int mostrarMenuPrincipal() {
        System.out.println("\nSeleccione una opción:");
        System.out.println("1. Gestionar Socios");
        System.out.println("2. Gestionar Excursiones");
        System.out.println("3. Gestionar Inscripciones");
        System.out.println("4. Salir");

        int opcion = -1;  // Inicializamos la opción con un valor no válido
        boolean opcionValida = false;

        // Bucle para asegurarnos de que el usuario introduzca una opción válida
        while (!opcionValida) {
            try {
                opcion = scanner.nextInt();  // Intentamos capturar la opción del usuario
                scanner.nextLine();  // Consumir la nueva línea
                if (opcion < 1 || opcion > 4) {
                    System.out.println("Opción no válida. Introduzca un número entre 1 y 4.");
                } else {
                    opcionValida = true;  // Si la opción es válida, salimos del bucle
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe introducir un número. Inténtelo de nuevo.");
                scanner.nextLine();  // Limpiar la entrada incorrecta
            }
        }
        return opcion;
    }

    /**
     * Muestra un mensaje en la consola para informar o interactuar con el usuario.
     *
     * @param mensaje El mensaje que se mostrará en la consola.
     */
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
