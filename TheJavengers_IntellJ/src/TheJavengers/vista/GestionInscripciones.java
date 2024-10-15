package TheJavengers.vista;

import TheJavengers.modelo.*;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona las inscripciones a las excursiones.
 * Permite agregar, eliminar y mostrar inscripciones.
 */
public class GestionInscripciones {
    private Datos datos;
    private Scanner scanner;

    /**
     * Constructor que inicializa la clase con los datos disponibles.
     *
     * @param datos La instancia de Datos que contiene la información de socios, excursiones e inscripciones.
     */
    public GestionInscripciones(Datos datos) {
        this.datos = datos;
        this.scanner = new Scanner(System.in);
    }

    //METODOS

    /**
     * Metodo para agregar una nueva inscripción solicitando los datos al usuario.
     * Si el socio no existe, se crea uno nuevo mediante la clase {@link GestionSocios}.
     */
    public void agregarInscripcion() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduce el ID del socio:");
        String idSocio = scanner.nextLine();
        System.out.print("Ingrese ID de la excursión: ");
        String idExcursion = scanner.nextLine();

        // Buscar si el socio ya existe usando el metodo estático de la clase Socio
        Socio socio = datos.getSocios().stream()
                .filter(s -> s.getIdSocio().equals(idSocio))
                .findFirst()
                .orElse(null);
        Excursion excursion = datos.getExcursiones().stream()
                .filter(e -> e.getIdExcursion().equals(idExcursion))
                .findFirst()
                .orElse(null);

        if (socio != null && excursion != null) {
            // Implementar lógica de inscripción
            datos.inscribirSocioEnExcursion(socio, excursion);
            System.out.println("Socio inscrito exitosamente en la excursión.");
        } else {
            System.out.println("Socio o excursión no encontrados.");
        }

    }

    /**
     * Metodo para buscar una excursión por su código.
     *
     * @param codigoExcursion Código de la excursión a buscar.
     * @return La excursión si es encontrada, o null si no existe.
     */
    private Excursion buscarExcursion(String codigoExcursion) {
        for (Excursion excursion : datos.getExcursiones()) { // Accediendo a las excursiones desde Datos
            if (excursion.getIdExcursion().equals(codigoExcursion)) {
                return excursion;
            }
        }
        return null;
    }

    /**
     * Metodo para eliminar una inscripción según el número de inscripción introducido por el usuario.
     * No se puede eliminar una inscripción si la excursión ya ha pasado.
     */
    public void eliminarInscripcion() {
        System.out.println("Introduce el número de inscripción que deseas eliminar:");
        int numeroInscripcion = Integer.parseInt(scanner.nextLine());

        Inscripcion inscripcion = null;
        for (Inscripcion i : datos.getInscripciones()) { // Obteniendo inscripciones desde Datos
            if (i.getIdInscripcion() == numeroInscripcion) {
                inscripcion = i;
                break;
            }
        }

        if (inscripcion == null) {
            System.out.println("Número de inscripción no encontrado.");
            return;
        }

        // Comprobar si la fecha de la excursión es anterior a la actual
        LocalDate fechaActual = LocalDate.now();
        if (inscripcion.getExcursion().getFechaExcursion().isBefore(fechaActual)) {
            System.out.println("No se puede eliminar una inscripción de una excursión ya pasada.");
            return;
        }

        // Eliminar inscripción
        datos.getInscripciones().remove(inscripcion); // Eliminando la inscripción desde Datos
        System.out.println("Inscripción eliminada correctamente.");
    }

    /**
     * Metodo para mostrar inscripciones filtradas por socio o por fechas.
     * El usuario elige el filtro a aplicar.
     */
    public void mostrarInscripciones() {
        System.out.println("¿Quieres filtrar por socio (S) o por fechas (F)?");
        String filtro = scanner.nextLine();

        for (Inscripcion inscripcion : datos.getInscripciones()) { // Obteniendo inscripciones desde Datos
            boolean mostrar = true;

            if (filtro.equalsIgnoreCase("S")) {
                System.out.println("Introduce el ID del socio:");
                String idSocio = scanner.nextLine();
                // Verificar si el ID del socio coincide
                mostrar = inscripcion.getSocio().getIdSocio().equals(idSocio);
            } else if (filtro.equalsIgnoreCase("F")) {
                System.out.println("Introduce la fecha de la excursión (formato: YYYY-MM-DD):");
                String fechaStr = scanner.nextLine();
                LocalDate fechaFiltrada = LocalDate.parse(fechaStr); // Convertir string a LocalDate
                // Verificar si la fecha de la excursión coincide
                mostrar = inscripcion.getExcursion().getFechaExcursion().equals(fechaFiltrada);
            }


            if (mostrar) {
                datos.getInscripciones();
            }
        }
    }
}
