package TheJavengers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestionExcursiones {
    private List<Excursion> listaExcursiones;

    public GestionExcursiones() {
        listaExcursiones = new ArrayList<>();
    }

    // Método para añadir excursión
    public void agregarExcursion() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduce el código de la excursión: ");
        String codigo = scanner.nextLine();

        System.out.println("Introduce la descripción de la excursión: ");
        String descripcion = scanner.nextLine();

        System.out.println("Introduce la fecha de la excursión (formato yyyy-MM-dd): ");
        LocalDate fecha = leerFecha(scanner);

        System.out.println("Introduce el número de días de la excursión: ");
        int numeroDias = scanner.nextInt();

        System.out.println("Introduce el precio de inscripción: ");
        double precioInscripcion = scanner.nextDouble();

        Excursion nuevaExcursion = new Excursion(codigo, descripcion, fecha, numeroDias, precioInscripcion);
        listaExcursiones.add(nuevaExcursion);

        System.out.println("Excursión añadida con éxito.");
    }

    // Método para mostrar excursiones entre dos fechas introducidas por el usuario
    public List<Excursion> mostrarExcursiones() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduce la fecha de inicio (formato yyyy-MM-dd): ");
        LocalDate fechaInicio = leerFecha(scanner);

        System.out.println("Introduce la fecha de fin (formato yyyy-MM-dd): ");
        LocalDate fechaFin = leerFecha(scanner);

        List<Excursion> excursionesFiltradas = new ArrayList<>();

        System.out.println("Excursiones entre las fechas " + fechaInicio + " y " + fechaFin + ":");

        for (Excursion excursion : listaExcursiones) {
            if (!excursion.getFecha().isBefore(fechaInicio) && !excursion.getFecha().isAfter(fechaFin)) {
                excursionesFiltradas.add(excursion);
                System.out.println(excursion);
            }
        }

        if (excursionesFiltradas.isEmpty()) {
            System.out.println("No se encontraron excursiones en ese rango de fechas.");
        }

        return excursionesFiltradas;
    }

    // Método para mostrar todas las excursiones
    public void mostrarTodaslasExcursiones() {
        if (listaExcursiones.isEmpty()) {
            System.out.println("No hay excursiones disponibles.");
            return;
        }

        System.out.println("Lista de todas las excursiones:");
        for (Excursion excursion : listaExcursiones) {
            System.out.println(excursion);
        }
    }

    // Método auxiliar para leer la fecha del usuario
    private LocalDate leerFecha(Scanner scanner) {
        LocalDate fecha = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (fecha == null) {
            try {
                String fechaTexto = scanner.nextLine();
                fecha = LocalDate.parse(fechaTexto, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha incorrecto, por favor intenta de nuevo (yyyy-MM-dd):");
            }
        }
        return fecha;
    }
}
