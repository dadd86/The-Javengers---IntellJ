package TheJavengers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestionSocios {
    private ArrayList<Socio> listaSocios;
    private ArrayList<Federacion> listaFederaciones;
    private Scanner scanner = new Scanner(System.in);

    public GestionSocios() {
        this.listaSocios = new ArrayList<>();
        this.listaFederaciones = new ArrayList<>();
    }

    // Método para añadir un socio (decidir el tipo)
    public void agregarSocio() {
        System.out.println("¿Qué tipo de socio quieres añadir? (Estándar = E, Federado = F, Infantil = I)");
        String tipo = scanner.nextLine();

        switch (tipo.toLowerCase()) { // Usar switch para mayor claridad
            case "e":
                agregarSocioEstandar();
                break;
            case "f":
                agregarSocioFederado();
                break;
            case "i":
                agregarSocioInfantil();
                break;
            default:
                System.out.println("Tipo de socio no reconocido.");
                break;
        }
    }

    // Método para añadir un socio estándar
    public void agregarSocioEstandar() {
        System.out.println("Introduce el ID del socio:");
        String id = scanner.nextLine();
        System.out.println("Introduce el nombre del socio:");
        String nombre = scanner.nextLine();
        System.out.println("Introduce los apellidos del socio:");
        String apellidos = scanner.nextLine();
        System.out.println("Introduce el NIF del socio:");
        String nif = scanner.nextLine();
        System.out.println("Introduce el tipo de seguro (Básico = B, Completo = C):");
        String tipoSeguro = scanner.nextLine();

        // Verificar que el tipo de seguro es válido
        Seguro.TipoSeguro tipo;
        if (tipoSeguro.equalsIgnoreCase("B")) {
            tipo = Seguro.TipoSeguro.BÁSICO;
        } else if (tipoSeguro.equalsIgnoreCase("C")) {
            tipo = Seguro.TipoSeguro.COMPLETO;
        } else {
            System.out.println("Tipo de seguro no válido. Debe ser 'B' para Básico o 'C' para Completo.");
            return;
        }

        // Crear el objeto Seguro con el tipo de seguro seleccionado
        Seguro seguro = new Seguro(tipo);

        // Crear el socio estándar con el seguro
        socioEstandar socioEstandar = new socioEstandar(id, nombre, apellidos, nif, seguro);

        // Añadir a la lista de socios
        listaSocios.add(socioEstandar);
        System.out.println("Socio estándar añadido correctamente.");
    }

    // Método para modificar el tipo de seguro de un socio estándar
    public void modificarSeguroSocio() {
        System.out.println("Introduce el ID del socio:");
        String id = scanner.nextLine();
        for (Socio socio : listaSocios) {
            if (socio instanceof socioEstandar && socio.getidsocio().equals(id)) {
                System.out.println("Introduce el nuevo tipo de seguro (Básico o Completo):");
                String nuevoSeguro = scanner.nextLine();

                // Verificar que el tipo de seguro es válido
                Seguro.TipoSeguro tipoNuevo;
                if (nuevoSeguro.equalsIgnoreCase("Básico")) {
                    tipoNuevo = Seguro.TipoSeguro.BÁSICO;
                } else if (nuevoSeguro.equalsIgnoreCase("Completo")) {
                    tipoNuevo = Seguro.TipoSeguro.COMPLETO;
                } else {
                    System.out.println("Tipo de seguro no válido. Debe ser 'Básico' o 'Completo'.");
                    return;
                }

                // Modificar el seguro
                ((socioEstandar) socio).setSeguroContratado(new Seguro(tipoNuevo));
                System.out.println("Seguro modificado correctamente.");
                return;
            }
        }
        System.out.println("No se ha encontrado un socio estándar con ese ID.");
    }

    // Método para añadir un socio federado
    public void agregarSocioFederado() {
        System.out.println("Introduce el ID del socio:");
        String id = scanner.nextLine();
        System.out.println("Introduce el nombre del socio:");
        String nombre = scanner.nextLine();
        System.out.println("Introduce los apellidos del socio:");
        String apellidos = scanner.nextLine();
        System.out.println("Introduce el NIF del socio:");
        String nif = scanner.nextLine();
        System.out.println("Introduce el nombre de la federación:");
        String nombreFederacion = scanner.nextLine();

        Federacion federacion = Federacion.buscarFederacion(listaFederaciones, nombreFederacion);
        if (federacion == null) {
            // Si no existe, creamos la federación
            federacion = new Federacion("FED" + id, nombreFederacion);
            listaFederaciones.add(federacion);
        }


        socioFederado socioFederado = new socioFederado(id, nombre, apellidos, nif, federacion);

        listaSocios.add(socioFederado);
        System.out.println("Socio federado añadido correctamente.");
    }

    // Método para añadir un socio infantil
    public void agregarSocioInfantil() {
        System.out.println("Introduce el ID del socio:");
        String id = scanner.nextLine();
        System.out.println("Introduce el nombre del socio:");
        String nombre = scanner.nextLine();
        System.out.println("Introduce los apellidos del socio:");
        String apellidos = scanner.nextLine();
        System.out.println("Introduce el ID del socio padre/madre:");
        String idSocioTutor = scanner.nextLine();

        // Verificar si el socio padre/madre existe
        boolean padreExiste = false;
        for (Socio socio : listaSocios) {
            if (socio.getidsocio().equals(idSocioTutor)) {
                padreExiste = true;
                break;
            }
        }

        if (!padreExiste) {
            System.out.println("No se ha encontrado un socio padre/madre con el ID proporcionado.");
            return;
        }

        socioInfantil socioInfantil = new socioInfantil(id, nombre, apellidos, idSocioTutor);
        listaSocios.add(socioInfantil);
        System.out.println("Socio infantil añadido correctamente.");
    }

    // Método para eliminar un socio
    public void eliminarSocio() {
        System.out.println("Introduce el ID del socio que quieres eliminar:");
        String id = scanner.nextLine();
        for (Socio socio : listaSocios) {
            if (socio.getidsocio().equals(id)) {
                listaSocios.remove(socio);
                System.out.println("Socio eliminado correctamente.");
                return;
            }
        }
        System.out.println("No se ha encontrado un socio con ese ID.");
    }

    // Método para mostrar todos los socios o por tipo
    public List<Socio> mostrarSocios() {
        System.out.println("¿Quieres mostrar todos (= T) los socios o filtrar por tipo (E = estándar, F = federado, I = infantil)?");
        String filtro = scanner.nextLine().toLowerCase();

        boolean haySocios = false;
        for (Socio socio : listaSocios) {
            switch (filtro) {
                case "t":
                    System.out.println(socio);
                    haySocios = true;
                    break;
                case "e": // Para socios estándar
                    if (socio instanceof socioEstandar) {
                        System.out.println(socio);
                        haySocios = true;
                    }
                    break;
                case "f": // Para socios federados
                    if (socio instanceof socioFederado) {
                        System.out.println(socio);
                        haySocios = true;
                    }
                    break;
                case "i": // Para socios infantiles
                    if (socio instanceof socioInfantil) {
                        System.out.println(socio);
                        haySocios = true;
                    }
                    break;
                default:
                    System.out.println("Filtro no reconocido. Por favor, usa 'T', 'E', 'F' o 'I'.");
                    return null;
            }
        }

        // Mensaje si no hay socios que mostrar
        if (!haySocios) {
            System.out.println("No se encontraron socios con el filtro especificado.");
        }
        return null;
    }

    // Método para mostrar la factura mensual de los socios
    public void mostrarFacturaMensual() {
        if (listaSocios.isEmpty()) {
            System.out.println("No hay socios registrados.");
            return; // Salir si no hay socios
        }

        System.out.println("Factura mensual:");
        for (Socio socio : listaSocios) {
            System.out.println(socio);
            System.out.printf("Cuota mensual total: %.2f€\n", socio.getCuotaMensualTotal());
        }
    }

}
