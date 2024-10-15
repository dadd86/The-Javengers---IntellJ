package TheJavengers.vista;

import java.util.Scanner;

public class VistaSocios {
    private Scanner scanner;

    public VistaSocios() {
        scanner = new Scanner(System.in);
    }

    public int mostrarMenuSocios() {
        System.out.println("\nGestión de Socios:");
        System.out.println("1. Añadir socio");
        System.out.println("2. Eliminar socio");
        System.out.println("3. Modificar tipo de seguro de un socio estándar");
        System.out.println("4. Mostrar factura mensual por socio");
        System.out.println("5. Volver al menú principal");

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

    // Mostrar un mensaje
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }


}
