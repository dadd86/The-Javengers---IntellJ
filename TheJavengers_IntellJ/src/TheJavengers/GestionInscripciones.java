package TheJavengers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestionInscripciones {
    private List<Inscripcion> listaInscripciones;
    private List<Socio> listaSocios;
    private List<Excursion> listaExcursiones;
    private Scanner scanner;

    public GestionInscripciones(List<Socio> listaSocios, List<Excursion> listaExcursiones) {
        this.listaInscripciones = new ArrayList<>();
        this.listaSocios = listaSocios;
        this.listaExcursiones = listaExcursiones;
        this.scanner = new Scanner(System.in);
    }

    // Método para añadir una inscripción
    public void agregarInscripcion() {
        System.out.println("Introduce el ID del socio:");
        String idSocio = scanner.nextLine();

        // Buscar si el socio ya existe usando el método estático de la clase Socio
        Socio socio = Socio.buscarSocio(listaSocios, idSocio);
        if (socio == null) {
            // Instanciar GestionSocios y agregar un nuevo socio
            GestionSocios gestionSocios = new GestionSocios();
            gestionSocios.agregarSocio(); // Esto llamará al método que permite al usuario crear un nuevo socio
            // Asumir que después de esto, deberás volver a buscar el socio recién creado
            socio = Socio.buscarSocio(listaSocios, idSocio); // Volver a buscar el socio
        }

        // Pedir detalles de la excursión
        System.out.println("Introduce el código de la excursión:");
        String codigoExcursion = scanner.nextLine();
        Excursion excursion = buscarExcursion(codigoExcursion); // Método para buscar la excursión
        if (excursion == null) {
            System.out.println("Excursión no encontrada.");
            return; // Salir del método si la excursión no se encuentra
        }

        // Crear inscripción con el número de inscripción
        int numeroInscripcion = listaInscripciones.size() + 1; // Generar un número de inscripción secuencial
        Inscripcion inscripcion = new Inscripcion(numeroInscripcion, socio, excursion, LocalDate.now());
        listaInscripciones.add(inscripcion);
        System.out.println("Inscripción añadida correctamente.");
    }

    // Método para buscar una excursión por código
    private Excursion buscarExcursion(String codigoExcursion) {
        for (Excursion excursion : listaExcursiones) {
            if (excursion.getIdExcursion().equals(codigoExcursion)) {
                return excursion;
            }
        }
        return null;
    }

    // Método para eliminar inscripción
    public void eliminarInscripcion() {
        System.out.println("Introduce el número de inscripción que deseas eliminar:");
        int numeroInscripcion = Integer.parseInt(scanner.nextLine());

        Inscripcion inscripcion = null;
        for (Inscripcion i : listaInscripciones) {
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
        if (inscripcion.getExcursion().getFecha().isBefore(fechaActual)) {
            System.out.println("No se puede eliminar una inscripción de una excursión ya pasada.");
            return;
        }

        // Eliminar inscripción
        listaInscripciones.remove(inscripcion);
        System.out.println("Inscripción eliminada correctamente.");
    }

    // Método para mostrar inscripciones
    public void mostrarInscripciones() {
        System.out.println("¿Quieres filtrar por socio (S) o por fechas (F)?");
        String filtro = scanner.nextLine();

        for (Inscripcion inscripcion : listaInscripciones) {
            boolean mostrar = true; // Variable para determinar si mostrar o no la inscripción

            if (filtro.equalsIgnoreCase("S")) {
                System.out.println("Introduce el ID del socio:");
                String idSocio = scanner.nextLine();
                // Verificar si el ID del socio coincide
                mostrar = inscripcion.getSocio().getidsocio().equals(idSocio);
            } else if (filtro.equalsIgnoreCase("F")) {
                System.out.println("Introduce la fecha de la excursión (formato: YYYY-MM-DD):");
                String fechaStr = scanner.nextLine();
                LocalDate fechaFiltrada = LocalDate.parse(fechaStr); // Convertir string a LocalDate
                // Verificar si la fecha de la excursión coincide
                mostrar = inscripcion.getExcursion().getFecha().equals(fechaFiltrada);
            }

            // Si no hay filtros, mostrar todas las inscripciones
            if (mostrar) {
                inscripcion.mostrarInscripcion(); // Llama al método mostrarInscripcion de la clase Inscripcion
            }
        }
    }
}
