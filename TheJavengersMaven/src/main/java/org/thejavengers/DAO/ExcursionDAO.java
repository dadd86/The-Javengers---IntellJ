package org.thejavengers.DAO;

import org.thejavengers.modelo.Excursion;
import java.util.List;

/**
 * Interfaz DAO para gestionar las operaciones CRUD (Create, Read, Update, Delete)
 * de la entidad {@link Excursion}.
 *
 * <p>Esta interfaz define los métodos que se deben implementar para interactuar
 * con la base de datos de manera uniforme y desacoplada del resto de la aplicación.</p>
 *
 * <p><strong>Mejoras en Seguridad y Robustez:</strong></p>
 * <ul>
 *     <li>La documentación detalla claramente los parámetros y retornos de cada método.</li>
 *     <li>Uso de JavaDoc para facilitar la comprensión y el mantenimiento del código.</li>
 *     <li>Definición de contratos claros para la gestión de excepciones en las implementaciones.</li>
 * </ul>
 */
public interface ExcursionDAO {

    /**
     * Guarda una nueva excursión en la base de datos.
     *
     * <p>Es responsabilidad de la implementación garantizar que no se violen
     * restricciones de integridad, como IDs duplicados o campos obligatorios no proporcionados.</p>
     *
     * @param excursion La excursión a guardar. No debe ser {@code null}.
     * @throws IllegalArgumentException Si {@code excursion} es {@code null}.
     * @throws RuntimeException Si ocurre un error al guardar la excursión en la base de datos.
     */
    void save(Excursion excursion);

    /**
     * Encuentra una excursión en la base de datos por su ID.
     *
     * <p>Este método debe devolver una excursión existente o {@code null} si no se encuentra.</p>
     *
     * @param id El ID único de la excursión que se busca. Debe ser un número positivo.
     * @return La excursión encontrada o {@code null} si no existe.
     * @throws IllegalArgumentException Si {@code id} es menor o igual a cero.
     * @throws RuntimeException Si ocurre un error al buscar la excursión.
     */
    Excursion findById(int id);

    /**
     * Recupera todas las excursiones almacenadas en la base de datos.
     *
     * <p>Este método devuelve una lista de excursiones. Si no hay excursiones, se devuelve una lista vacía.</p>
     *
     * @return Una lista de todas las excursiones existentes. Nunca será {@code null}.
     * @throws RuntimeException Si ocurre un error al recuperar las excursiones.
     */
    List<Excursion> findAll();

    /**
     * Actualiza una excursión existente en la base de datos.
     *
     * <p>Este método reemplaza los datos de una excursión existente con los datos proporcionados.</p>
     *
     * @param excursion La excursión con los datos actualizados. Debe contener un ID válido.
     * @throws IllegalArgumentException Si {@code excursion} es {@code null} o su ID no es válido.
     * @throws RuntimeException Si ocurre un error al actualizar la excursión.
     */
    void update(Excursion excursion);

    /**
     * Elimina una excursión de la base de datos por su ID.
     *
     * <p>Si la excursión no existe, este método no realizará ninguna operación.</p>
     *
     * @param id El ID de la excursión a eliminar. Debe ser un número positivo.
     * @throws IllegalArgumentException Si {@code id} es menor o igual a cero.
     * @throws RuntimeException Si ocurre un error al eliminar la excursión.
     */
    void delete(int id);
}
