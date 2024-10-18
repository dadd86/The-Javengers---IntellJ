package TheJavengers.vista;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class VistaInscripciones {
    private Scanner scanner;
    private DateTimeFormatter formatter;

    public VistaInscripciones() {
        scanner = new Scanner(System.in);
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");  // Formato de fecha
    }

    // Método para pedir una fecha al usuario
    public LocalDate pedirFecha(String mensaje) {
        System.out.println(mensaje);
        LocalDate fecha = null;
        boolean fechaValida = false;

        while (!fechaValida) {
            try {
                String fechaStr = scanner.nextLine();
                fecha = LocalDate.parse(fechaStr, formatter);  // Convertir el texto en LocalDate
                fechaValida = true;  // Fecha correctamente parseada
            } catch (DateTimeParseException e) {
                System.out.println("Fecha no válida. Introduzca una fecha con formato yyyy-MM-dd:");
            }
        }
        return fecha;
    }
    // Mostrar el submenú de gestión de inscripciones y devolver la opción elegida
    public int mostrarMenuInscripciones() {
        System.out.println("\nGestión de Inscripciones:");
        System.out.println("1. Inscribir socio en excursión");
        System.out.println("2. Eliminar inscripción");
        System.out.println("3. Mostrar inscripciones con filtro por socio y/o fechas");
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

    // Mostrar un mensaje en consola
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
