package org.thejavengers.modelo;

import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

/**
 * Representa una excursión organizada por el sistema de gestión.
 * <p>
 * Contiene información como el identificador de la excursión, descripción,
 * fecha, duración en días y el precio.
 * </p>
 *
 * <p><strong>Buenas prácticas aplicadas:</strong></p>
 * <ul>
 *     <li>Validaciones estrictas para mantener la integridad de los datos.</li>
 *     <li>Uso de `Logger` para registrar eventos importantes.</li>
 *     <li>Seguimiento de estándares de documentación Javadoc.</li>
 * </ul>
 */
@Entity
@Table(name = "excursiones") // Define la tabla correspondiente en la base de datos
public class Excursion {

    // Logger para registrar eventos importantes
    private static final Logger logger = LoggerFactory.getLogger(Excursion.class);

    // Constantes para validaciones
    private static final float PRECIO_MAXIMO = 10_000.0f;
    private static final int DURACION_MAXIMA_DIAS = 365;

    // Atributos mapeados
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idExcursion", nullable = false)
    private int idExcursion;

    @Column(name = "descripcion", nullable = false, length = 255)
    private String descripcion;

    @Column(name = "fechaExcursion", nullable = false)
    private LocalDate fechaExcursion;

    @Column(name = "numero_dias", nullable = false)
    private int numeroDias;

    @Column(name = "precio", nullable = false)
    private float precio;

    /**
     * Constructor vacío requerido por Hibernate.
     */
    public Excursion() {
        // Constructor sin argumentos
    }

    /**
     * Constructor para inicializar una excursión con todos sus atributos.
     *
     * @param idExcursion    Identificador único de la excursión. No puede ser negativo.
     * @param descripcion    Descripción de la excursión. No puede ser nula o vacía.
     * @param fechaExcursion Fecha en la que se llevará a cabo la excursión. No puede ser nula.
     * @param numeroDias     Número de días que dura la excursión. Debe ser mayor a 0 y menor o igual a 365.
     * @param precio         Precio de la excursión. Debe ser mayor o igual a 0 y menor o igual a 10,000.
     * @throws IllegalArgumentException Si alguno de los parámetros no cumple con las condiciones requeridas.
     */
    public Excursion(int idExcursion, String descripcion, LocalDate fechaExcursion, int numeroDias, float precio) {
        logger.debug("Creando una nueva excursión con ID: {}", idExcursion);
        setIdExcursion(idExcursion);
        setDescripcion(descripcion);
        setFechaExcursion(fechaExcursion);
        setNumeroDias(numeroDias);
        setPrecio(precio);
        logger.info("Excursión creada exitosamente: {}", this);
    }

    // Getters y Setters con validaciones

    /**
     * Obtiene el identificador único de la excursión.
     *
     * @return El identificador de la excursión.
     */
    public int getIdExcursion() {
        return idExcursion;
    }

    /**
     * Establece el identificador único de la excursión.
     *
     * @param idExcursion El nuevo identificador. Debe ser mayor o igual a 0.
     * @throws IllegalArgumentException Si el identificador es negativo.
     */
    public void setIdExcursion(int idExcursion) {
        if (idExcursion < 0) {
            logger.error("ID de excursión inválido: {}", idExcursion);
            throw new IllegalArgumentException("El ID de la excursión no puede ser negativo.");
        }
        this.idExcursion = idExcursion;
    }

    /**
     * Obtiene la descripción de la excursión.
     *
     * @return La descripción de la excursión.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece una nueva descripción para la excursión.
     *
     * @param descripcion La nueva descripción. No puede ser nula o vacía.
     * @throws IllegalArgumentException Si la descripción es nula o vacía.
     */
    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            logger.error("Descripción inválida: {}", descripcion);
            throw new IllegalArgumentException("La descripción no puede ser nula o vacía.");
        }
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la fecha de la excursión.
     *
     * @return La fecha de la excursión.
     */
    public LocalDate getFechaExcursion() {
        return fechaExcursion;
    }

    /**
     * Establece una nueva fecha para la excursión.
     *
     * @param fechaExcursion La nueva fecha. No puede ser nula ni en el pasado.
     * @throws IllegalArgumentException Si la fecha es nula o está en el pasado.
     */
    public void setFechaExcursion(LocalDate fechaExcursion) {
        if (fechaExcursion == null || fechaExcursion.isBefore(LocalDate.now())) {
            logger.error("Fecha inválida: {}", fechaExcursion);
            throw new IllegalArgumentException("La fecha no puede ser nula ni estar en el pasado.");
        }
        this.fechaExcursion = fechaExcursion;
    }

    /**
     * Obtiene el número de días que dura la excursión.
     *
     * @return El número de días de la excursión.
     */
    public int getNumeroDias() {
        return numeroDias;
    }

    /**
     * Establece un nuevo número de días para la excursión.
     *
     * @param numeroDias El nuevo número de días. Debe ser mayor a 0 y menor o igual a 365.
     * @throws IllegalArgumentException Si el número de días no es válido.
     */
    public void setNumeroDias(int numeroDias) {
        if (numeroDias <= 0 || numeroDias > DURACION_MAXIMA_DIAS) {
            logger.error("Número de días inválido: {}", numeroDias);
            throw new IllegalArgumentException("El número de días debe ser mayor a 0 y menor o igual a 365.");
        }
        this.numeroDias = numeroDias;
    }

    /**
     * Obtiene el precio de la excursión.
     *
     * @return El precio de la excursión.
     */
    public float getPrecio() {
        return precio;
    }

    /**
     * Establece un nuevo precio para la excursión.
     *
     * @param precio El nuevo precio. Debe ser mayor o igual a 0 y menor o igual a 10,000.
     * @throws IllegalArgumentException Si el precio no es válido.
     */
    public void setPrecio(float precio) {
        if (precio < 0 || precio > PRECIO_MAXIMO) {
            logger.error("Precio inválido: {}", precio);
            throw new IllegalArgumentException("El precio debe ser mayor o igual a 0 y menor o igual a 10,000.");
        }
        this.precio = precio;
    }

    /**
     * Representación en cadena de la excursión.
     *
     * @return Una cadena que contiene los datos de la excursión.
     */
    @Override
    public String toString() {
        return String.format("Excursion{id=%d, descripcion='%s', fecha=%s, dias=%d, precio=%.2f}",
                idExcursion, descripcion, fechaExcursion, numeroDias, precio);
    }
}
