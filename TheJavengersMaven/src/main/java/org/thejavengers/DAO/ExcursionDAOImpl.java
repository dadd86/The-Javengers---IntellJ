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
 * <p>Esta clase gestiona las operaciones CRUD para el modelo {@link Excursion}
 * mediante el uso del ORM Hibernate, asegurando un manejo adecuado de transacciones
 * y sesiones.</p>
 *
 * <p><strong>Mejoras en Seguridad y Robustez:</strong></p>
 * <ul>
 *     <li>Logger para registrar errores y eventos importantes.</li>
 *     <li>Manejo adecuado de transacciones para garantizar atomicidad en las operaciones.</li>
 *     <li>Validación previa de datos para evitar operaciones no deseadas.</li>
 *     <li>Gestión de excepciones con mensajes detallados para facilitar la depuración.</li>
 * </ul>
 */
public class ExcursionDAOImpl implements ExcursionDAO {

    // Logger para registrar eventos y errores
    private static final Logger logger = LoggerFactory.getLogger(ExcursionDAOImpl.class);

    /**
     * Guarda una nueva excursión en la base de datos.
     *
     * @param excursion El objeto {@link Excursion} que se desea guardar.
     */
    @Override
    public void save(Excursion excursion) {
        if (excursion == null) {
            logger.warn("No se puede guardar una excursión nula");
            throw new IllegalArgumentException("La excursión no puede ser nula.");
        }
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(excursion);
            transaction.commit();
            logger.info("Excursión guardada correctamente: {}", excursion);
        } catch (IllegalArgumentException e) {
            logger.error("Error en los datos de la excursión: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Error al guardar la excursión: {}", e.getMessage());
        }
    }


    /**
     * Recupera una excursión de la base de datos por su ID.
     *
     * @param id El ID de la excursión que se desea recuperar.
     * @return La excursión correspondiente al ID, o {@code null} si no se encuentra.
     */
    @Override
    public Excursion findById(int id) {
        if (id <= 0) {
            logger.warn("ID inválido para buscar excursión: {}", id);
            return null;
        }
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Excursion excursion = session.get(Excursion.class, id);
            if (excursion != null) {
                logger.info("Excursión encontrada: {}", excursion);
            } else {
                logger.warn("No se encontró excursión con ID: {}", id);
            }
            return excursion;
        } catch (Exception e) {
            logger.error("Error al buscar la excursión con ID {}: {}", id, e.getMessage());
            return null;
        }
    }

    /**
     * Recupera todas las excursiones almacenadas en la base de datos.
     *
     * @return Una lista de objetos {@link Excursion}, o una lista vacía si no hay resultados.
     */
    @Override
    public List<Excursion> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Excursion> excursiones = session.createQuery("from Excursion", Excursion.class).list();
            logger.info("Se recuperaron {} excursiones", excursiones.size());
            return excursiones;
        } catch (Exception e) {
            logger.error("Error al recuperar todas las excursiones: {}", e.getMessage());
            return List.of(); // Devuelve una lista vacía en caso de error
        }
    }


    /**
     * Actualiza los datos de una excursión existente en la base de datos.
     *
     * @param excursion El objeto {@link Excursion} con los datos actualizados.
     */
    @Override
    public void update(Excursion excursion) {
        if (excursion == null) {
            logger.warn("No se puede actualizar una excursión nula");
            return;
        }
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(excursion); // Cambiado de update() a merge()
            transaction.commit();
            logger.info("Excursión actualizada correctamente: {}", excursion);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Error al actualizar la excursión con ID {}: {}", excursion.getIdExcursion(), e.getMessage());
        }
    }

    /**
     * Elimina una excursión de la base de datos por su ID.
     *
     * @param id El ID de la excursión que se desea eliminar.
     */
    @Override
    public void delete(int id) {
        if (id <= 0) {
            logger.warn("ID inválido para eliminar excursión: {}", id);
            return;
        }
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Excursion excursion = session.get(Excursion.class, id);
            if (excursion != null) {
                session.delete(excursion); // Cambiado de delete() a remove()
                transaction.commit();
                logger.info("Excursión con ID {} eliminada correctamente", id);
            } else {
                logger.warn("No se encontró la excursión con ID: {}", id);
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Error al eliminar la excursión con ID {}: {}", id, e.getMessage());
        }
    }
}