package org.thejavengers.vista;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase que representa la vista de gestión de inscripciones del sistema.
 * Proporciona métodos para interactuar con el usuario y solicitar datos
 * relacionados con las inscripciones, así como mostrar mensajes y menús.
 */
public class VistaInscripciones {

    // Atributos
    private Scanner scanner;  // Objeto Scanner para capturar las entradas del usuario
    private DateTimeFormatter formatter;  // Formato para las fechas en "yyyy-MM-dd"

    /**
     * Constructor de la clase VistaInscripciones.
     * Inicializa el Scanner y el formateador de fechas.
     */
    public VistaInscripciones() {
        scanner = new Scanner(System.in);
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");  // Formato de fecha
    }

    /**
     * Solicita al usuario una fecha y devuelve un objeto LocalDate.
     * El formato de la fecha debe ser "yyyy-MM-dd". Si el usuario ingresa una fecha incorrecta,
     * se le solicitará nuevamente hasta que introduzca una fecha válida.
     *
     * @param mensaje El mensaje que se mostrará para solicitar la fecha.
     * @return Un objeto LocalDate con la fecha válida ingresada por el usuario.
     */
    public LocalDate pedirFecha(String mensaje) {
        System.out.println(mensaje);
        LocalDate fecha = null;
        boolean fechaValida = false;

        // Bucle que sigue pidiendo la fecha hasta que el usuario introduzca una válida
        while (!fechaValida) {
            try {
                String fechaStr = scanner.nextLine();
                fecha = LocalDate.parse(fechaStr, formatter);  // Convertir el texto en LocalDate
                fechaValida = true;  // La fecha fue correctamente parseada
            } catch (DateTimeParseException e) {
                System.out.println("Error: Fecha no válida. Introduzca una fecha con formato yyyy-MM-dd:");
            }
        }
        return fecha;
    }

    /**
     * Muestra el submenú de gestión de inscripciones y solicita al usuario que elija una opción.
     * Si el usuario ingresa un valor no válido, el sistema seguirá solicitando hasta que introduzca
     * un número válido.
     *
     * @return La opción seleccionada por el usuario como entero.
     */
    public int mostrarMenuInscripciones() {
        System.out.println("\nGestión de Inscripciones:");
        System.out.println("1. Inscribir socio en excursión");
        System.out.println("2. Eliminar inscripción");
        System.out.println("3. Mostrar inscripciones con filtro por socio y/o fechas");
        System.out.println("4. Volver al menú principal");

        int opcion = -1;  // Inicializamos con un valor no válido
        boolean opcionValida = false;

        // Bucle para asegurarse de que el usuario introduce una opción válida
        while (!opcionValida) {
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();  // Limpiar el buffer
                if (opcion >= 1 && opcion <= 4) {
                    opcionValida = true;  // La opción es válida
                } else {
                    System.out.println("Opción no válida. Introduzca un número entre 1 y 4.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe introducir un número. Inténtelo de nuevo.");
                scanner.nextLine();  // Limpiar el buffer para evitar bucle infinito
            }
        }
        return opcion;
    }

    /**
     * Solicita al usuario que ingrese un texto y lo devuelve como cadena.
     * Este método es útil para capturar datos como ID o nombres.
     *
     * @param mensaje El mensaje que se mostrará para solicitar el texto.
     * @return El texto ingresado por el usuario.
     */
    public String pedirTexto(String mensaje) {
        System.out.println(mensaje);
        return scanner.nextLine();
    }

    /**
     * Muestra un mensaje en la consola.
     * Este método es útil para informar al usuario sobre el resultado de una operación o para mostrar mensajes de error.
     *
     * @param mensaje El mensaje que se mostrará en la consola.
     */
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
