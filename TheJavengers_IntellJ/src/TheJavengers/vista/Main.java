package TheJavengers.vista;

import TheJavengers.controlador.ControladorExcursionista;
import TheJavengers.modelo.*;

import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.format.DateTimeParseException;

public class Main {
    public static void main(String[] args) {
        ControladorExcursionista controlador = new ControladorExcursionista();
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
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
            scanner.nextLine(); // Consumir la nueva línea

            try {
                switch (opcion) {
                    case 1:
                        // Añadir socio
                        System.out.println("Ingrese el tipo de socio (1. Estándar, 2. Federado, 3. Infantil):");
                        int tipoSocio = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Ingrese el ID del socio:");
                        String idSocio = scanner.nextLine();
                        System.out.println("Ingrese el nombre del socio:");
                        String nombre = scanner.nextLine();
                        System.out.println("Ingrese los apellidos del socio:");
                        String apellidos = scanner.nextLine();

                        String nif = "";
                        String codigoFederacion = "";
                        TipoSeguro seguro = null;
                        String idSocioTutor = "";

                        if (tipoSocio == 1) {
                            System.out.println("Ingrese el NIF:");
                            nif = scanner.nextLine();
                            System.out.println("Ingrese el tipo de seguro (1. Básico, 2. Completo):");
                            int tipoSeguro = scanner.nextInt();
                            scanner.nextLine();
                            seguro = (tipoSeguro == 1) ? TipoSeguro.BASICO : TipoSeguro.COMPLETO;
                        } else if (tipoSocio == 2) {
                            System.out.println("Ingrese el NIF:");
                            nif = scanner.nextLine();

                            // Mostrar federaciones disponibles
                            List<Federacion> federaciones = controlador.obtenerFederaciones();
                            System.out.println("Seleccione una federación de la siguiente lista:");
                            for (Federacion fed : federaciones) {
                                System.out.println("Código: " + fed.getCodigo() + ", Nombre: " + fed.getNombre());
                            }
                            System.out.println("Ingrese el código de la federación elegida:");
                            codigoFederacion = scanner.nextLine();
                        } else if (tipoSocio == 3) {
                            System.out.println("Ingrese el ID del socio tutor:");
                            idSocioTutor = scanner.nextLine();
                        } else {
                            System.out.println("Tipo de socio no válido.");
                            break;
                        }

                        String mensajeRegistro = controlador.registrarSocio(tipoSocio, idSocio, nombre, apellidos, nif, seguro, codigoFederacion, idSocioTutor);
                        System.out.println(mensajeRegistro);
                        break;

                    case 2:
                        // Mostrar factura mensual por socio
                        System.out.println("Ingrese el ID del socio para mostrar la factura:");
                        String idSocioFactura = scanner.nextLine();

                        String factura = controlador.mostrarFacturaMensual(idSocioFactura);
                        System.out.println(factura);
                        break;

                    case 3:
                        // Añadir excursión
                        System.out.println("Ingrese el ID de la excursión:");
                        String idExcursion = scanner.nextLine();
                        System.out.println("Ingrese la descripción de la excursión:");
                        String descripcion = scanner.nextLine();
                        System.out.println("Ingrese la fecha de la excursión (dd/MM/yyyy):");
                        String fechaStr = scanner.nextLine();
                        LocalDate fecha = null;
                        try {
                            fecha = LocalDate.parse(fechaStr, formatter);
                        } catch (DateTimeParseException e) {
                            System.out.println("Formato de fecha incorrecto.");
                            break;
                        }
                        System.out.println("Ingrese el número de días de la excursión:");
                        int numeroDias = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Ingrese el precio de inscripción:");
                        float precio = scanner.nextFloat();
                        scanner.nextLine();

                        String mensajeExcursion = controlador.registrarExcursion(idExcursion, descripcion, fecha, numeroDias, precio);
                        System.out.println(mensajeExcursion);
                        break;

                    case 4:
                        // Filtrar excursiones entre fechas
                        System.out.println("Ingrese la fecha de inicio (dd/MM/yyyy):");
                        String fechaInicioStr = scanner.nextLine();
                        LocalDate fechaInicio = null;
                        if (!fechaInicioStr.isEmpty()) {
                            try {
                                fechaInicio = LocalDate.parse(fechaInicioStr, formatter);
                            } catch (DateTimeParseException e) {
                                System.out.println("Formato de fecha incorrecto en la fecha de inicio.");
                                break;
                            }
                        }
                        System.out.println("Ingrese la fecha de fin (dd/MM/yyyy):");
                        String fechaFinStr = scanner.nextLine();
                        LocalDate fechaFin = null;
                        if (!fechaFinStr.isEmpty()) {
                            try {
                                fechaFin = LocalDate.parse(fechaFinStr, formatter);
                            } catch (DateTimeParseException e) {
                                System.out.println("Formato de fecha incorrecto en la fecha de fin.");
                                break;
                            }
                        }

                        List<Excursion> excursiones = controlador.mostrarExcursionesPorFechas(fechaInicio, fechaFin);
                        System.out.println("Excursiones entre las fechas indicadas:");
                        for (Excursion exc : excursiones) {
                            System.out.println(exc);
                        }
                        break;


                    case 5:
                        // Eliminar socio
                        System.out.println("Ingrese el ID del socio a eliminar:");
                        String idSocioEliminar = scanner.nextLine();

                        String mensajeEliminacion = controlador.eliminarSocio(idSocioEliminar);
                        System.out.println(mensajeEliminacion);
                        break;

                    case 6:
                        // Inscribir socio en excursión
                        System.out.println("Ingrese el ID del socio:");
                        String idSocioInscribir = scanner.nextLine();
                        System.out.println("Ingrese el ID de la excursión:");
                        String idExcursionInscribir = scanner.nextLine();

                        String mensajeInscripcion = controlador.inscribirSocioEnExcursion(idSocioInscribir, idExcursionInscribir);
                        System.out.println(mensajeInscripcion);
                        break;

                    case 7:
                        // Modificar tipo de seguro de un socio estándar
                        System.out.println("Ingrese el ID del socio a modificar:");
                        String idSocioModif = scanner.nextLine();
                        System.out.println("Ingrese el nuevo tipo de seguro (1. Básico, 2. Completo):");
                        int tipoSeguroNuevo = scanner.nextInt();
                        scanner.nextLine();
                        TipoSeguro nuevoSeguro = (tipoSeguroNuevo == 1) ? TipoSeguro.BASICO : TipoSeguro.COMPLETO;

                        String mensajeModificacion = controlador.modificarSeguroSocioEstandar(idSocioModif, nuevoSeguro);
                        System.out.println(mensajeModificacion);
                        break;

                    case 8:
                        // Eliminar inscripción
                        System.out.println("Ingrese el número de inscripción a eliminar:");
                        int idInscripcion = scanner.nextInt();
                        scanner.nextLine();
                        LocalDate fechaActual = LocalDate.now();

                        String mensajeEliminacionInscripcion = controlador.eliminarInscripcion(idInscripcion, fechaActual);
                        System.out.println(mensajeEliminacionInscripcion);
                        break;

                    case 9:
                        // Mostrar inscripciones con filtro por socio y/o fechas
                        System.out.println("Ingrese el ID del socio (o presione Enter para omitir):");
                        String idSocioFiltro = scanner.nextLine();
                        if (idSocioFiltro.isEmpty()) {
                            idSocioFiltro = null;
                        }
                        System.out.println("Ingrese la fecha de inicio (dd/MM/yyyy) o presione Enter para omitir:");
                        String fechaInicioFiltroStr = scanner.nextLine();
                        LocalDate fechaInicioFiltro = null;
                        if (!fechaInicioFiltroStr.isEmpty()) {
                            try {
                                fechaInicioFiltro = LocalDate.parse(fechaInicioFiltroStr, formatter);
                            } catch (DateTimeParseException e) {
                                System.out.println("Formato de fecha incorrecto en la fecha de inicio.");
                                break;
                            }
                        }
                        System.out.println("Ingrese la fecha de fin (dd/MM/yyyy) o presione Enter para omitir:");
                        String fechaFinFiltroStr = scanner.nextLine();
                        LocalDate fechaFinFiltro = null;
                        if (!fechaFinFiltroStr.isEmpty()) {
                            try {
                                fechaFinFiltro = LocalDate.parse(fechaFinFiltroStr, formatter);
                            } catch (DateTimeParseException e) {
                                System.out.println("Formato de fecha incorrecto en la fecha de fin.");
                                break;
                            }
                        }

                        List<Inscripcion> inscripciones = controlador.mostrarInscripcionesFiltradas(idSocioFiltro, fechaInicioFiltro, fechaFinFiltro);
                        System.out.println("Inscripciones filtradas:");
                        for (Inscripcion ins : inscripciones) {
                            System.out.println(ins);
                        }
                        break;


                    case 10:
                        // Mostrar los socios inscritos en una excursión
                        System.out.println("Ingrese el ID de la excursión:");
                        String idExcursionListar = scanner.nextLine();

                        List<Socio> sociosInscritos = controlador.listarSociosEnExcursion(idExcursionListar);
                        System.out.println("Socios inscritos en la excursión:");
                        for (Socio s : sociosInscritos) {
                            System.out.println(s);
                        }
                        break;

                    case 11:
                        // Salir
                        System.out.println("Saliendo...");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Opción no válida.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}

