import java.time.LocalDate;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

public class SenderosYMontanas {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //SIMULACION CREACION DE SOCIOS (se cambiará por versión con menú que pregunte los datos, no que los establezca en el código)
        // Crear Socios
        Socio socio1 = new Socio("S001", "Juan", "Pérez", 50.0f);
        Socio socio2 = new Socio("S002", "Ana", "García", 60.0f);

        // Crear Excursiones
        Excursion excursion1 = new Excursion("E001", "Excursión a la Montaña", new Date(2024, 10, 15), 3, 200.0f);
        Excursion excursion2 = new Excursion("E002", "Excursión a la Playa", new Date(2024, 11, 10), 2, 150.0f);

        // Crear Inscripción
        Inscripcion inscripcion = new Inscripcion(
                1,
                Arrays.asList(socio1, socio2),
                Arrays.asList(excursion1, excursion2),
                LocalDate.now()
        );

        //Muestra información y calcula precio de inscripción
        System.out.println("Precio total de la inscripción: " + inscripcion.calcularPrecioInscripcion());

        // Cancelar la inscripción para la primera excursión
        LocalDate fechaLimite = LocalDate.of(2024, 10, 12); // Fecha límite de cancelación
        boolean cancelada = inscripcion.cancelarInscripcion(excursion1.getFechaExcursion(), fechaLimite);

        if (cancelada) {
            System.out.println("La inscripción fue cancelada con éxito.");
        } else {
            System.out.println("No se pudo cancelar la inscripción (fecha fuera de límite).");
        }
    }
}
