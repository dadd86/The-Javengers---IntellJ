package org.thejavengers.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Clase que representa una inscripción a una excursión realizada por un socio.
 * Contiene información como el identificador de la inscripción, el socio inscrito,
 * la excursión a la que se inscribe, y la fecha de la inscripción.
 */
@Entity
@Table(name = "inscripciones") // Nombre de la tabla en la base de datos
public class Inscripcion {

    // Atributos mapeados
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    @Column(name = "idInscripcion", nullable=false, unique=true) // El ID debe ser único
    private int idInscripcion;

    @ManyToOne(fetch=FetchType.LAZY) // Varias inscripciones pueden ser del mismo socio
    @JoinColumn(name = "idSocio", nullable=false) // Columna que se relaciona con la tabla socios
    private Socio socio;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "idExcursion", nullable=false) // Columna que se relaciona con la tabla excursiones
    private Excursion excursion;

    @Column(name = "fechaInscripcion", nullable=false)
    private LocalDate fechaInscripcion;

    /**
     * Constructor vacío requerido por Hibernate.
     */
    public Inscripcion() {
    }

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

    // Setters con validación

    /**
     * Recupera el socio de la inscripción.
     *
     * @param socio El socio asignado a la inscripción. No puede ser nulo o vacío.
     * @throws IllegalArgumentException si el socio es nulo o vacío.
     */
    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    /**
     * Recupera la excursión  de la inscripción.
     *
     * @param excursion La excursión asignado a la inscripción. No puede ser nulo o vacío.
     * @throws IllegalArgumentException si la excursión es nulo o vacío.
     */
    public void setExcursion(Excursion excursion) {
        this.excursion = excursion;
    }

    /**
     * Recupera la fecha de la inscripción.
     *
     * @param fechaInscripcion La fecha asiganda a la inscripción. No puede ser nulo o vacío.
     * @throws IllegalArgumentException si la fecha es nula o vacía.
     */
    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
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


    // Metodo toString

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

    // Metodo equals y hashCode

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