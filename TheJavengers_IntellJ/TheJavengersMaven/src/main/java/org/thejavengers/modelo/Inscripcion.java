package org.thejavengers.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Clase que representa una inscripción a una excursión realizada por un socio.
 * Contiene información como el identificador de la inscripción, el socio inscrito,
 * la excursión a la que se inscribe, y la fecha de la inscripción.
 */
public class Inscripcion {
    // Atributos
    private final int idInscripcion;
    private final Socio socio;
    private final Excursion excursion;
    private final LocalDate fechaInscripcion;

    // Constructor
    /**
     * Constructor para inicializar una inscripción con todos sus atributos.
     *
     * @param idInscripcion    Identificador único de la inscripción.
     * @param socio            Socio que realiza la inscripción. No puede ser nulo.
     * @param excursion        Excursión a la que se inscribe el socio. No puede ser nula.
     * @param fechaInscripcion Fecha en la que se realiza la inscripción. No puede ser nula y debe ser anterior a la fecha de la excursión.
     * @throws IllegalArgumentException si alguno de los parámetros no cumple con las condiciones requeridas.
     */
    public Inscripcion(int idInscripcion, Socio socio, Excursion excursion, LocalDate fechaInscripcion) {
        if (socio == null) {
            throw new IllegalArgumentException("El socio no puede ser nulo");
        }
        if (excursion == null) {
            throw new IllegalArgumentException("La excursión no puede ser nula");
        }
        if (fechaInscripcion == null) {
            throw new IllegalArgumentException("La fecha de inscripción no puede ser nula");
        }
        if (fechaInscripcion.isAfter(excursion.getFechaExcursion())) {
            throw new IllegalArgumentException("La fecha de inscripción no puede ser posterior a la fecha de la excursión");
        }

        this.idInscripcion = idInscripcion;
        this.socio = socio;
        this.excursion = excursion;
        this.fechaInscripcion = fechaInscripcion;
    }

    // Getters

    /**
     * Obtiene el identificador único de la inscripción.
     *
     * @return El identificador de la inscripción.
     */
    public int getIdInscripcion() {
        return idInscripcion;
    }

    /**
     * Obtiene el socio que realizó la inscripción.
     *
     * @return El socio que realizó la inscripción.
     */
    public Socio getSocio() {
        return socio;
    }

    /**
     * Obtiene la excursión a la que se inscribió el socio.
     *
     * @return La excursión a la que se inscribió el socio.
     */
    public Excursion getExcursion() {
        return excursion;
    }

    /**
     * Obtiene la fecha en la que se realizó la inscripción.
     *
     * @return La fecha de inscripción.
     */
    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    // Métodos adicionales

    /**
     * Calcula el precio total de la inscripción, considerando el precio de la excursión y otros cargos o descuentos aplicables.
     *
     * @return El precio total de la inscripción.
     */
    public float calcularPrecioInscripcion() {
        float precioExcursion = excursion.getPrecio();
        float descuentoSocio = socio.calcularCuotaMensual(); // Ajustar según sea necesario
        // aqui falta la logica para asegurar que se hacer descuento
        return precioExcursion + descuentoSocio;
    }

    /**
     * Cancela la inscripción si la fecha de la excursión es posterior a la fecha actual
     * y faltan más de 24 horas para que comience.
     *
     * @throws IllegalStateException si la inscripción no puede ser cancelada porque la
     *         fecha de la excursión ya ha pasado o faltan menos de 24 horas para el inicio.
     */
    public void cancelarInscripcion() {
        LocalDateTime fechaActual = LocalDateTime.now();
        LocalDateTime fechaExcursion = excursion.getFechaExcursion().atStartOfDay(); // Cambiamos a LocalDateTime

        // Calculamos la diferencia en horas entre la fecha actual y la fecha de la excursión.
        long horasHastaExcursion = ChronoUnit.HOURS.between(fechaActual, fechaExcursion);

        // Si la fecha de la excursión ya ha pasado o si faltan menos de 24 horas, no se puede cancelar.
        if (fechaExcursion.isBefore(fechaActual) || horasHastaExcursion < 24) {
            throw new IllegalStateException("No se puede cancelar la inscripción, faltan menos de 24 horas para la excursión o la fecha ya ha pasado");
        }

        // Aquí implementaríamos la lógica para eliminar la inscripción (p. ej., eliminar de una lista)
    }


    // Método toString

    /**
     * Proporciona una representación en forma de cadena de caracteres del objeto Inscripcion.
     *
     * @return Una cadena de caracteres que contiene los datos de la inscripción.
     */
    @Override
    public String toString() {
        return "Inscripcion{" +
                "idInscripcion=" + idInscripcion +
                ", socio=" + socio +
                ", excursion=" + excursion +
                ", fechaInscripcion=" + fechaInscripcion +
                '}';
    }

    // Método equals y hashCode

    /**
     * Compara este objeto con otro objeto para verificar si son iguales.
     * Dos objetos Inscripcion son iguales si tienen el mismo id de inscripción.
     *
     * @param obj El objeto a comparar.
     * @return true si los objetos son iguales; false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Inscripcion that = (Inscripcion) obj;
        return idInscripcion == that.idInscripcion;
    }

    /**
     * Calcula el código hash para este objeto Inscripcion.
     * Utiliza el identificador de inscripción para generar el código hash.
     *
     * @return El código hash del objeto.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(idInscripcion);
    }
}

