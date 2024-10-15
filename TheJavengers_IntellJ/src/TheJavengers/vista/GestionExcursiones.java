package TheJavengers.vista;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import TheJavengers.modelo.*;

/**
 * Clase que gestiona una lista de excursiones, permitiendo agregar y mostrar excursiones
 * en función de fechas o mostrar todas las disponibles.
 */
public class GestionExcursiones {
    private Datos datos;
    private List<Excursion> listaExcursiones;

    /**
     * Constructor que inicializa la lista de excursiones.
     */
    public GestionExcursiones(Datos datos) {
        this.datos = datos;
        listaExcursiones = new ArrayList<>();
    }

    //METODOS

    /**
     * Metodo para añadir una nueva excursión solicitando los datos al usuario.
     * Los datos incluyen código, descripción, fecha, número de días y precio de inscripción.
     */
    public void agregarExcursion() {


        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce ID de la excursión: ");
        String idExcursion = scanner.nextLine();
        System.out.print("Introduce descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Introduce fecha (YYYY-MM-DD): ");
        LocalDate fecha = LocalDate.parse(scanner.nextLine());
        System.out.print("Introduce número de días: ");
        int numeroDias = Integer.parseInt(scanner.nextLine());
        System.out.print("Introduce precio: ");
        float precio = Float.parseFloat(scanner.nextLine());

        try {
            Excursion nuevaExcursion = new Excursion(idExcursion, descripcion, fecha, numeroDias, precio);
            datos.agregarExcursion(nuevaExcursion);
            System.out.println("Excursión agregada exitosamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error al agregar la excursión: " + e.getMessage());
        }
    }

    /**
     * Metodo para mostrar excursiones que se encuentran dentro de un rango de fechas
     * introducido por el usuario.
     *
     * @return Una lista de excursiones que están dentro del rango de fechas.
     */

    public ArrayList<Excursion> filtrarExcursiones() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce la fecha de inicio (formato yyyy-MM-dd): ");
        LocalDate fechaInicio = leerFecha(scanner);

        System.out.println("Introduce la fecha de fin (formato yyyy-MM-dd): ");
        LocalDate fechaFin = leerFecha(scanner);

        ArrayList<Excursion> excursionesFiltradas = new ArrayList<>();

        System.out.println("Excursiones entre las fechas " + fechaInicio + " y " + fechaFin + ":");

        for (Excursion excursion : listaExcursiones) {
            if (!excursion.getFechaExcursion().isBefore(fechaInicio) && !excursion.getFechaExcursion().isAfter(fechaFin)) {
                excursionesFiltradas.add(excursion);
                System.out.println(excursion);
            }
        }

        if (excursionesFiltradas.isEmpty()) {
            System.out.println("No se encontraron excursiones en ese rango de fechas.");
        }

        return excursionesFiltradas;
    }

    /**
     * Metodo para mostrar todas las excursiones disponibles.
     * Si no hay excursiones en la lista, muestra un mensaje indicándolo.
     */
     public void listarExcursiones() {
        System.out.println("Lista de excursiones:");
        for (Excursion excursion : datos.getExcursiones()) {
            System.out.println(excursion);
        }
    }

    /**
     * Metodo auxiliar que busca una excursión por su código.
     *
     * @param idExcursion Código de la excursión a buscar.
     * @return La excursión encontrada, o null si no existe.
     */

    private Excursion buscarExcursionPorCodigo (String idExcursion){
        for (Excursion excursion : listaExcursiones) {
            if (excursion.getIdExcursion().equals(idExcursion)) {
                return excursion;
            }
        }
        return null;
    }

    /**
     * Metodo auxiliar que solicita al usuario una fecha y la convierte en un objeto
     * {@link LocalDate}, validando el formato.
     *
     * @param scanner Objeto {@link Scanner} para leer la entrada del usuario.
     * @return Un objeto {@link LocalDate} que representa la fecha introducida por el usuario.
     */
    private LocalDate leerFecha (Scanner scanner){
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
