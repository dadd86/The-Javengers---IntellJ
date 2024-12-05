package org.thejavengers.controlador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thejavengers.DAO.ExcursionDAO;
import org.thejavengers.DAO.ExcursionDAOImpl;
import org.thejavengers.modelo.Excursion;

import java.time.LocalDate;

/**
 * Controlador encargado de la lógica de negocio para gestionar excursiones.
 */
public class ControladorExcursionesVista {

    private static final Logger logger = LoggerFactory.getLogger(ControladorExcursionesVista.class);

    private final ExcursionDAO excursionDAO;

    /**
     * Constructor que inicializa el DAO para la gestión de excursiones.
     */
    public ControladorExcursionesVista() {
        this.excursionDAO = new ExcursionDAOImpl();
    }

    /**
     * Valida los datos de entrada y guarda una nueva excursión en la base de datos.
     *
     * @param descripcion Descripción de la excursión.
     * @param fecha       Fecha de la excursión.
     * @param diasStr     Número de días como cadena de texto.
     * @param precioStr   Precio como cadena de texto.
     * @throws IllegalArgumentException Si los datos de entrada son inválidos.
     */
    public void validarYGuardarExcursion(String descripcion, LocalDate fecha, String diasStr, String precioStr) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }

        if (fecha == null || fecha.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha no puede ser nula ni estar en el pasado.");
        }

        int numeroDias;
        try {
            numeroDias = Integer.parseInt(diasStr.trim());
            if (numeroDias <= 0 || numeroDias > 365) {
                throw new IllegalArgumentException("El número de días debe estar entre 1 y 365.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El número de días debe ser un valor numérico.");
        }

        float precio;
        try {
            precio = Float.parseFloat(precioStr.trim());
            if (precio < 0 || precio > 10_000) {
                throw new IllegalArgumentException("El precio debe estar entre 0 y 10,000.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El precio debe ser un valor numérico.");
        }

        Excursion nuevaExcursion = new Excursion(0, descripcion, fecha, numeroDias, precio);
        excursionDAO.save(nuevaExcursion);
        logger.info("Excursión agregada exitosamente: {}", nuevaExcursion);
    }
}
