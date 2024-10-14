package TheJavengers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class VistaExcursionista {
    private Scanner scanner;
    private DateTimeFormatter formatter;

    public VistaExcursionista() {
        scanner = new Scanner(System.in);
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    // Mostrar el menú y devolver la opción elegida
    public int mostrarMenu() {
        System.out.println("\nSeleccione una opción:");
        System.out.println("1. Añadir socio");
        System.out.println("2. Mostrar factura mensual por socio");
        System.out.println("3. Añadir excursión");
        System.out.println("4. Filtrar excursiones entre fechas");
        System.out.println("5. Eliminar socio");
        System.out.println("6. Inscribir socio en excursión");
        System.out.println("7. Modificar tipo de seguro de un socio estándar");
        System.out.println("8. Eliminar inscripción");
        System.out.println("9. Mostrar inscripciones con filtro por socio y/o fechas");
        System.out.println("10. Mostrar los socios inscritos en una excursión");
        System.out.println("11. Salir");

        int opcion = scanner.nextInt();
        scanner.nextLine();
        return opcion;
    }

    // Pedir un texto al usuario
    public String pedirTexto(String mensaje) {
        System.out.println(mensaje);
        return scanner.nextLine();
    }

    // Pedir un entero al usuario
    public int pedirEntero(String mensaje) {
        System.out.println(mensaje);
        int numero = scanner.nextInt();
        scanner.nextLine();
        return numero;
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
