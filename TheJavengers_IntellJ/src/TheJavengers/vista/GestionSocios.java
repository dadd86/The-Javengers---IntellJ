package TheJavengers.vista;

import TheJavengers.modelo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase que gestiona la creación, modificación y eliminación de socios,
 * así como la gestión de federaciones asociadas a los socios federados.
 */
public class GestionSocios {
    private Datos datos;
    private List<Socio> listaSocios;
    Scanner scanner = new Scanner(System.in);

    /**
     * Constructor que inicializa la lista de socios.
     */
    public GestionSocios(Datos datos) {
        this.datos = datos;
        listaSocios = new ArrayList<>();
    }

    //METODOS

    /**
     * Metodo para agregar un nuevo socio.
     */
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

    // Metodo para añadir un socio estándar
    public void agregarSocioEstandar() {
        System.out.println("Introduce el ID del socio:");
        String idSocio = scanner.nextLine();
        System.out.println("Introduce el nombre del socio:");
        String nombre = scanner.nextLine();
        System.out.println("Introduce los apellidos del socio:");
        String apellidos = scanner.nextLine();
        System.out.println("Introduce el NIF del socio:");
        String nif = scanner.nextLine();
        // Solicitar al usuario el tipo de seguro
        System.out.println("Introduce el tipo de seguro (Básico = B, Completo = C):");
        String tipoSeguroInput = scanner.nextLine();

        TipoSeguro tipoSeguro; // Cambiar a TipoSeguro directamente
        if (tipoSeguroInput.equalsIgnoreCase("B")) {
            tipoSeguro = TipoSeguro.BASICO;
        } else if (tipoSeguroInput.equalsIgnoreCase("C")) {
            tipoSeguro = TipoSeguro.COMPLETO;
        } else {
            System.out.println("Tipo de seguro no válido. Debe ser 'B' para Básico o 'C' para Completo.");
            return;
        }

        // Crear el socio estándar con el tipo de seguro
        SocioEstandar socioEstandar = new SocioEstandar(idSocio, nombre, apellidos, nif, tipoSeguro);


        // Añadir a la lista de socios
        listaSocios.add(socioEstandar);
        System.out.println("Socio estándar añadido correctamente.");
    }

    // Metodo para modificar el tipo de seguro de un socio estándar
    public void modificarSeguroSocio() {
        System.out.println("Introduce el ID del socio:");
        String id = scanner.nextLine();

        for (Socio socio : listaSocios) {
            if (socio instanceof SocioEstandar && socio.getIdSocio().equals(id)) {
                System.out.println("Introduce el nuevo tipo de seguro (B=Básico o C=Completo):");
                String nuevoSeguro = scanner.nextLine();

                // Verificar que el tipo de seguro es válido
                TipoSeguro tipoNuevo;
                if (nuevoSeguro.equalsIgnoreCase("b")) {
                    tipoNuevo = TipoSeguro.BASICO;
                } else if (nuevoSeguro.equalsIgnoreCase("c")) {
                    tipoNuevo = TipoSeguro.COMPLETO;
                } else {
                    System.out.println("Tipo de seguro no válido. Debe ser 'B' para el seguro 'Básico' o 'C' para el seguro 'Completo'.");
                    return;
                }

                // Modificar el seguro
                ((SocioEstandar) socio).setSeguro(tipoNuevo);
                System.out.println("Seguro modificado correctamente.");
                return;
            }
        }
        System.out.println("No se ha encontrado un socio estándar con ese ID.");
    }


    // Metodo para añadir un socio federado
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

        Federacion federacion = Federacion.buscarFederacion(datos.getFederaciones(), nombreFederacion);

        if (federacion != null) {
            System.out.println("Federación encontrada: " + federacion.getNombre());
        } else {
            System.out.println("Federación no encontrada. ¿Quieres agregar una nueva federación? S/N");
            String respuesta = scanner.nextLine();

            if (respuesta.equalsIgnoreCase("S")) {
                System.out.println("Introduce el nombre de la nueva federación:");
                nombreFederacion = scanner.nextLine();

                System.out.println("Introduce el ID de la nueva federación:");
                String idFederacion = scanner.nextLine();

                federacion = new Federacion(idFederacion, nombreFederacion);
                datos.agregarFederacion(federacion);

                System.out.println("Nueva federación añadida correctamente.");
            } else {
                System.out.println("No se puede agregar el socio federado sin una federación válida.");
                return;
            }
        }

        // Crear el socio federado con la federación válida
        SocioFederado socioFederado = new SocioFederado(id, nombre, apellidos, nif, federacion);
        listaSocios.add(socioFederado);
        System.out.println("Socio federado añadido correctamente.");
    }


    // Metodo para añadir un socio infantil
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
        for (Socio socio : datos.getSocios()) {
            if (socio.getIdSocio().equals(idSocioTutor)) {
                padreExiste = true;
                break;
            }
        }

        if (!padreExiste) {
            System.out.println("No se ha encontrado un socio padre/madre con el ID proporcionado.");
            return;
        }


        SocioInfantil socioInfantil = new SocioInfantil(id, nombre, apellidos, idSocioTutor, SocioInfantil.CUOTA_MENSUAL);
        listaSocios.add(socioInfantil);
        System.out.println("Socio infantil añadido correctamente.");
    }


    public void listarSocios() {
        System.out.println("Lista de socios:");
        for (Socio socio : datos.getSocios()) {
            System.out.println(socio);
        }
    }
}