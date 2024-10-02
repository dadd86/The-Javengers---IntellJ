import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SistemaExcursionista sistema = new SistemaExcursionista();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Registrar socio");
            System.out.println("2. Registrar excursión");
            System.out.println("3. Inscribir socio a excursión");
            System.out.println("4. Cancelar inscripción");
            System.out.println("5. Listar socios");
            System.out.println("6. Listar excursiones");
            System.out.println("7. Listar socios en una excursión");
            System.out.println("8. Listar excursiones de un socio");
            System.out.println("9. Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese el tipo de socio (1. Estándar, 2. Federado, 3. Infantil):");
                    int tipoSocioInput = scanner.nextInt();
                    scanner.nextLine(); // Consumir la nueva línea

                    System.out.println("Ingrese el número de socio:");
                    int numeroSocio = scanner.nextInt();
                    scanner.nextLine(); // Consumir la nueva línea

                    System.out.println("Ingrese el nombre del socio:");
                    String nombre = scanner.nextLine();

                    if (tipoSocioInput == 1) {
                        System.out.println("Ingrese el NIF:");
                        String nifEstandar = scanner.nextLine();

                        System.out.println("Ingrese el tipo de seguro (1. Básico, 2. Completo):");
                        int tipoSeguroInput = scanner.nextInt();
                        scanner.nextLine(); // Consumir la nueva línea

                        TipoSeguro tipoSeguro = tipoSeguroInput == 1 ? TipoSeguro.BASICO : TipoSeguro.COMPLETO;

                        System.out.println("Ingrese el precio del seguro:");
                        double precioSeguro = scanner.nextDouble();
                        scanner.nextLine(); // Consumir la nueva línea

                        Seguro seguro = new Seguro(tipoSeguro, precioSeguro);
                        Socio socioEstandar = new SocioEstandar(numeroSocio, nombre, nifEstandar, seguro);
                        sistema.registrarSocio(socioEstandar);

                    } else if (tipoSocioInput == 2) {
                        System.out.println("Ingrese el NIF:");
                        String nifFederado = scanner.nextLine();

                        System.out.println("Ingrese el código de la federación:");
                        String codigoFederacion = scanner.nextLine();

                        System.out.println("Ingrese el nombre de la federación:");
                        String nombreFederacion = scanner.nextLine();

                        Federacion federacion = new Federacion(codigoFederacion, nombreFederacion);
                        Socio socioFederado = new SocioFederado(numeroSocio, nombre, nifFederado, federacion);
                        sistema.registrarSocio(socioFederado);

                    } else if (tipoSocioInput == 3) {
                        System.out.println("Ingrese el número de socio del padre:");
                        int numeroSocioPadre = scanner.nextInt();
                        scanner.nextLine(); // Consumir la nueva línea

                        Socio socioInfantil = new SocioInfantil(numeroSocio, nombre, numeroSocioPadre);
                        sistema.registrarSocio(socioInfantil);
                    }
                    break;

                case 2:
                    System.out.println("Ingrese el código de la excursión:");
                    String codigoExcursion = scanner.nextLine();

                    System.out.println("Ingrese la descripción de la excursión:");
                    String descripcion = scanner.nextLine();

                    System.out.println("Ingrese la fecha de la excursión (yyyy-mm-dd):");
                    String fechaStr = scanner.nextLine();
                    Date fecha = java.sql.Date.valueOf(fechaStr);

                    System.out.println("Ingrese el número de días:");
                    int numeroDias = scanner.nextInt();
                    scanner.nextLine(); // Consumir la nueva línea

                    System.out.println("Ingrese el precio de inscripción:");
                    double precioInscripcion = scanner.nextDouble();
                    scanner.nextLine(); // Consumir la nueva línea

                    Excursion excursion = new Excursion(codigoExcursion, descripcion, fecha, numeroDias, precioInscripcion);
                    sistema.registrarExcursion(excursion);
                    break;

                case 3:
                    System.out.println("Ingrese el número de socio:");
                    int numeroSocioInscripcion = scanner.nextInt();
                    scanner.nextLine(); // Consumir la nueva línea

                    System.out.println("Ingrese el código de la excursión:");
                    String codigoExcursionInscripcion = scanner.nextLine();

                    sistema.inscribirSocioEnExcursion(numeroSocioInscripcion, codigoExcursionInscripcion);
                    break;

                case 4:
                    System.out.println("Ingrese el número de inscripción:");
                    int numeroInscripcion = scanner.nextInt();
                    scanner.nextLine(); // Consumir la nueva línea

                    System.out.println("Ingrese la fecha actual (yyyy-mm-dd):");
                    String fechaActualStr = scanner.nextLine();
                    Date fechaActual = java.sql.Date.valueOf(fechaActualStr);

                    boolean cancelada = sistema.cancelarInscripcion(numeroInscripcion, fechaActual);
                    System.out.println("Inscripción cancelada: " + cancelada);
                    break;

                case 5:
                    sistema.listarSocios();
                    break;

                case 6:
                    sistema.listarExcursiones();
                    break;

                case 7:
                    System.out.println("Ingrese el código de la excursión:");
                    String codigoExcursionListado = scanner.nextLine();
                    sistema.listarSociosEnExcursion(codigoExcursionListado);
                    break;

                case 8:
                    System.out.println("Ingrese el número de socio:");
                    int numeroSocioListado = scanner.nextInt();
                    scanner.nextLine(); // Consumir la nueva línea
                    sistema.listarExcursionesDeSocio(numeroSocioListado);
                    break;

                case 9:
                    System.out.println("Saliendo...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }
}