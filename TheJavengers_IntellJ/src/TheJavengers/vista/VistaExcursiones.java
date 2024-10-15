package TheJavengers.vista;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class VistaExcursiones {
    private Scanner scanner;
    private DateTimeFormatter formatter;

    public VistaExcursiones() {
        scanner = new Scanner(System.in);
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    // Mostrar el submenú de gestión de excursiones y devolver la opción elegida
    public int mostrarMenuExcursiones() {
        System.out.println("\nGestión de Excursiones:");
        System.out.println("1. Añadir excursión");
        System.out.println("2. Filtrar excursiones entre fechas");
        System.out.println("3. Mostrar los socios inscritos en una excursión");
        System.out.println("4. Volver al menú principal");

        int opcion = scanner.nextInt();
        scanner.nextLine();
        return opcion;
    }

    // Pedir un texto al usuario
    public String pedirTexto(String mensaje) {
        System.out.println(mensaje);
        return scanner.nextLine();
    }

    // Pedir una fecha al usuario
    public LocalDate pedirFecha(String mensaje) {
        System.out.println(mensaje);
        String fechaStr = scanner.nextLine();
        return LocalDate.parse(fechaStr, formatter);  // Convertir el texto en LocalDate
    }

    // Mostrar un mensaje en consola
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
