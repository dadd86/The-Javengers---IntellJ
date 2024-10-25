package org.thejavengers.vista;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase que representa la vista de gestión de socios del sistema.
 * Proporciona métodos para interactuar con el usuario, solicitando datos relacionados
 * con la gestión de socios, y mostrando mensajes o menús de forma controlada.
 */
public class VistaSocios {

    // Atributos
    private Scanner scanner;  // Objeto para capturar la entrada del usuario

    /**
     * Constructor de la clase VistaSocios.
     * Inicializa el objeto Scanner que se utilizará para capturar las entradas del usuario.
     */
    public VistaSocios() {
        scanner = new Scanner(System.in);
    }

    /**
     * Muestra el menú de gestión de socios al usuario y le solicita que seleccione una opción.
     * Valida que el usuario ingrese un número entero dentro del rango permitido (1-5).
     * Si se ingresa una opción inválida o no numérica, se vuelve a pedir hasta que sea válida.
     *
     * @return El número de opción seleccionado por el usuario.
     */
    public int mostrarMenuSocios() {
        System.out.println("\nGestión de Socios:");
        System.out.println("1. Añadir socio");
        System.out.println("2. Eliminar socio");
        System.out.println("3. Modificar tipo de seguro de un socio estándar");
        System.out.println("4. Mostrar factura mensual por socio");
        System.out.println("5. Mostrar o filtrar socios inscritos");
        System.out.println("6 Volver al menú principal");

        int opcion = -1;  // Inicializamos con un valor no válido
        boolean opcionValida = false;

        // Bucle para asegurarse de que el usuario introduce una opción válida
        while (!opcionValida) {
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();  // Limpiar el buffer
                if (opcion >= 1 && opcion <= 5) {
                    opcionValida = true;  // Opción válida
                } else {
                    System.out.println("Opción no válida. Introduzca un número entre 1 y 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe introducir un número. Inténtelo de nuevo.");
                scanner.nextLine();  // Limpiar el buffer para evitar bucle infinito
            }
        }
        return opcion;
    }

    /**
     * Solicita al usuario que ingrese un texto y lo devuelve.
     * Este método es útil para capturar datos como nombres, apellidos, IDs, etc.
     * Garantiza que el usuario no pueda ingresar un valor vacío.
     *
     * @param mensaje El mensaje que se mostrará al usuario solicitando la entrada de texto.
     * @return El texto ingresado por el usuario.
     */
    public String pedirTexto(String mensaje) {
        System.out.println(mensaje);
        String texto = scanner.nextLine().trim();

        // Validación para evitar entradas vacías
        while (texto.isEmpty()) {
            System.out.println("Entrada no válida. " + mensaje);
            texto = scanner.nextLine().trim();
        }

        return texto;
    }

    /**
     * Solicita al usuario que ingrese un número entero y lo devuelve.
     * Este método es útil para capturar datos como identificadores numéricos, selección de opciones, etc.
     * Si el usuario ingresa un valor no numérico, se maneja la excepción y se solicita nuevamente.
     *
     * @param mensaje El mensaje que se mostrará al usuario solicitando la entrada de un número entero.
     * @return El número entero ingresado por el usuario.
     */
    public int pedirEntero(String mensaje) {
        System.out.println(mensaje);
        int numero = -1;  // Valor inicial no válido
        boolean numeroValido = false;

        // Bucle para validar la entrada del número entero
        while (!numeroValido) {
            try {
                numero = scanner.nextInt();
                scanner.nextLine();  // Limpiar el buffer
                numeroValido = true;  // El número es válido
            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada no válida. Debe ingresar un número entero.");
                scanner.nextLine();  // Limpiar el buffer para evitar bucle infinito
            }
        }
        return numero;
    }

    /**
     * Muestra un mensaje en la consola.
     * Este método es útil para informar al usuario sobre el resultado de una operación,
     * o para mostrar mensajes de error o confirmación.
     *
     * @param mensaje El mensaje que se mostrará en la consola.
     */
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
