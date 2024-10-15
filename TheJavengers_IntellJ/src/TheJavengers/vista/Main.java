package TheJavengers.vista;

import java.util.*;

import TheJavengers.modelo.*;
import TheJavengers.controlador.*;

public class Main {
    public static void main(String[] args) {
        Datos datos = new Datos();

        GestionExcursiones gestionExcursiones = new GestionExcursiones(datos);
        GestionInscripciones gestionInscripciones = new GestionInscripciones(datos);
        GestionSocios gestionSocios = new GestionSocios(datos);

        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("Menu:");
            System.out.println("1. Gestionar Excursiones");
            System.out.println("2. Gestionar Inscripciones");
            System.out.println("3. Gestionar Socios");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir nueva línea

            switch (opcion) {
                case 1:
                    gestionExcursiones.agregarExcursion();
                    gestionExcursiones.listarExcursiones();
                    break;
                case 2:
                    gestionInscripciones.agregarInscripcion();
                    break;
                case 3:
                    gestionSocios.agregarSocio();
                    gestionSocios.listarSocios();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
                }
            } while (opcion != 0);
        }
    }
