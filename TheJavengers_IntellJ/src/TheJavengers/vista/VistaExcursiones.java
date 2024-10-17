package TheJavengers.vista;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Clase que representa la vista para gestionar las excursiones en el sistema.
 * Permite la interacción con el usuario para agregar excursiones, filtrar por fechas
 * y mostrar los socios inscritos. Proporciona métodos para capturar entradas y mostrar
 * información en la consola.
 */
public class VistaExcursiones {

    // Atributos
    private Scanner scanner;  // Objeto Scanner para capturar la entrada del usuario.
    private DateTimeFormatter formatter;  // Formato utilizado para las fechas.

    /**
     * Constructor de la clase VistaExcursiones.
     * Inicializa el scanner y define el formato de fecha a utilizar en el sistema (dd/MM/yyyy).
     */
    public VistaExcursiones() {
        scanner = new Scanner(System.in);
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    /**
     * Muestra el menú de gestión de excursiones y devuelve la opción seleccionada por el usuario.
     *
     * @return La opción seleccionada por el usuario.
     */
    public int mostrarMenuExcursiones() {
        System.out.println("\nGestión de Excursiones:");
        System.out.println("1. Añadir excursión");
        System.out.println("2. Filtrar excursiones entre fechas");
        System.out.println("3. Mostrar los socios inscritos en una excursión");
        System.out.println("4. Volver al menú principal");

        int opcion = -1;
        try {
            opcion = scanner.nextInt();  // Captura la opción ingresada por el usuario.
            scanner.nextLine();  // Consumir la nueva línea.
        } catch (Exception e) {
            System.out.println("Opción no válida. Por favor, introduce un número.");
            scanner.nextLine();  // Limpiar la entrada incorrecta.
        }
        return opcion;
    }

    /**
     * Solicita un texto al usuario mostrando un mensaje y captura la entrada.
     *
     * @param mensaje El mensaje que se mostrará al usuario.
     * @return El texto ingresado por el usuario.
     */
    public String pedirTexto(String mensaje) {
        System.out.println(mensaje);
        return scanner.nextLine();
    }

    /**
     * Solicita una fecha al usuario y la convierte en un objeto LocalDate.
     * Verifica que la fecha esté en el formato correcto, manejando posibles excepciones.
     *
     * @param mensaje El mensaje que se mostrará al usuario.
     * @return La fecha ingresada por el usuario como LocalDate.
     */
    public LocalDate pedirFecha(String mensaje) {
        System.out.println(mensaje);
        LocalDate fecha = null;
        boolean fechaValida = false;

        while (!fechaValida) {
            String fechaStr = scanner.nextLine();  // Captura la fecha como una cadena de texto.
            try {
                fecha = LocalDate.parse(fechaStr, formatter);  // Intenta convertir la cadena en LocalDate.
                fechaValida = true;  // Si la conversión es exitosa, la fecha es válida.
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha no válido. Por favor, ingresa una fecha con el formato dd/MM/yyyy.");
            }
        }
        return fecha;
    }

    /**
     * Muestra un mensaje al usuario en la consola.
     *
     * @param mensaje El mensaje que se mostrará.
     */
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
