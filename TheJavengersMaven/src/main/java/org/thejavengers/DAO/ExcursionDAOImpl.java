package org.thejavengers.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thejavengers.modelo.Excursion;
import org.thejavengers.utils.HibernateUtil;

import java.util.List;

/**
 * Implementación de la interfaz {@link ExcursionDAO} utilizando Hibernate.
 *
 * <p>Gestiona las operaciones CRUD para la entidad {@link Excursion} garantizando:
 * </p>
 * <ul>
 *     <li>Manejo adecuado de transacciones.</li>
 *     <li>Validación previa de parámetros.</li>
 *     <li>Gestión de excepciones con mensajes detallados en los logs.</li>
 * </ul>
 *
 * <p>Se emplea el patrón DAO para desacoplar la lógica de acceso a datos del resto
 * de la aplicación.</p>
 */
public class ExcursionDAOImpl implements ExcursionDAO {

    // Logger para registrar eventos y errores
    private static final Logger logger = LoggerFactory.getLogger(ExcursionDAOImpl.class);

    /**
     * Guarda una nueva excursión en la base de datos.
     *
     * @param excursion La excursión a guardar. No debe ser {@code null}.
     */
    @Override
    public void save(Excursion excursion) {
        if (excursion == null) {
            logger.warn("Intento de guardar una excursión nula.");
            throw new IllegalArgumentException("La excursión no puede ser nula.");
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            logger.debug("Iniciando la transacción para guardar la excursión: {}", excursion);
            transaction = session.beginTransaction();
            session.persist(excursion);
            transaction.commit();
            logger.info("Excursión guardada exitosamente: {}", excursion);
        } catch (IllegalArgumentException e) {
            logger.error("Error en los datos de la excursión al intentar guardarla: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Error inesperado al guardar la excursión: {}", e.getMessage(), e);
        }
    }

    /**
     * Recupera una excursión de la base de datos por su ID.
     *
     * @param id El ID de la excursión que se desea recuperar.
     * @return La excursión correspondiente al ID o {@code null} si no se encuentra.
     */
    @Override
    public Excursion findById(int id) {
        if (id <= 0) {
            logger.warn("ID inválido para buscar excursión: {}", id);
            throw new IllegalArgumentException("El ID debe ser mayor a cero.");
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            logger.debug("Buscando excursión con ID: {}", id);
            Excursion excursion = session.get(Excursion.class, id);
            if (excursion != null) {
                logger.info("Excursión encontrada: {}", excursion);
            } else {
                logger.warn("No se encontró ninguna excursión con el ID: {}", id);
            }
            return excursion;
        } catch (Exception e) {
            logger.error("Error inesperado al buscar la excursión con ID {}: {}", id, e.getMessage(), e);
            return null;
        }
    }

    /**
     * Recupera todas las excursiones almacenadas en la base de datos.
     *
     * @return Una lista de excursiones o una lista vacía si no hay resultados.
     */
    public List<Excursion> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            logger.debug("Recuperando todas las excursiones.");
            List<Excursion> excursiones = session.createQuery("from Excursion", Excursion.class).list();
            logger.info("Se recuperaron {} excursiones.", excursiones.size());
            return excursiones;
        } catch (Exception e) {
            logger.error("Error inesperado al recuperar todas las excursiones: {}", e.getMessage(), e);
            return List.of(); // Devuelve una lista vacía en caso de error
        }
    }

    /**
     * Actualiza los datos de una excursión existente en la base de datos.
     *
     * @param excursion La excursión con los datos actualizados.
     */
    @Override
    public void update(Excursion excursion) {
        if (excursion == null) {
            logger.warn("Intento de actualizar una excursión nula.");
            throw new IllegalArgumentException("La excursión no puede ser nula.");
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            logger.debug("Iniciando la transacción para actualizar la excursión: {}", excursion);
            transaction = session.beginTransaction();
            session.merge(excursion);
            transaction.commit();
            logger.info("Excursión actualizada exitosamente: {}", excursion);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Error inesperado al actualizar la excursión con ID {}: {}", excursion.getIdExcursion(), e);
        }
    }

    /**
     * Elimina una excursión de la base de datos por su ID.
     *
     * @param id El ID de la excursión a eliminar.
     */
    @Override
    public void delete(int id) {
        if (id <= 0) {
            logger.warn("ID inválido para eliminar excursión: {}", id);
            throw new IllegalArgumentException("El ID debe ser mayor a cero.");
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            logger.debug("Iniciando la transacción para eliminar la excursión con ID: {}", id);
            transaction = session.beginTransaction();

            Excursion excursion = session.get(Excursion.class, id);
            if (excursion != null) {
                session.delete(excursion);
                transaction.commit();
                logger.info("Excursión eliminada exitosamente con ID: {}", id);
            } else {
                logger.warn("No se encontró la excursión con ID: {}", id);
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Error inesperado al eliminar la excursión con ID {}: {}", id, e);
        }
    }
}
