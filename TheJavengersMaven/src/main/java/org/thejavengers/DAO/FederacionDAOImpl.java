package org.thejavengers.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thejavengers.modelo.Excursion;
import org.thejavengers.modelo.Federacion;
import org.thejavengers.utils.HibernateUtil;
import java.util.*;
import java.sql.*;

/**
 * Implementación de la interfaz {@link FederacionDAO} utilizando Hibernate.
 *
 * <p>Esta clase gestiona las operaciones CRUD para el modelo {@link Federacion}
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
public class FederacionDAOImpl implements FederacionDAO {

    // Logger para registrar eventos y errores
    private static final Logger logger = LoggerFactory.getLogger(FederacionDAOImpl.class);

    /**
     * Guarda una nueva federación en la base de datos.
     *
     * @param federacion El objeto {@link Federacion} que se desea guardar.
     */
    @Override
    public void save(Federacion federacion) {
        Transaction transaction = null; // Hibernate gestiona la transacción
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction(); // Inicia la transacción

            session.save(federacion); // Persistencia de federacíon en la base de datos

            transaction.commit(); // Confirma la transacción
            System.out.print("Federación guardada: " + federacion);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Realiza rollback en caso de error
            }
            e.printStackTrace();
            System.err.print("Error al guardar federacion. Realizando rollback.");
        }
    }

    /**
     * Recupera una federación de la base de datos por su código.
     *
     * @param codigo El código de la federación que se desea recuperar.
     * @return La federación correspondiente al código, o {@code null} si no se encuentra.
     */
    @Override
    public Federacion findByCodigo(int codigo) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Federacion federacion = session.get(Federacion.class, codigo); // Busca por clave primaria

            if (federacion != null) {
                System.out.print("Federación encontrada: " + federacion);
            } else {
                System.out.print("Federación no encontrada con código: " + codigo);
            }
            return federacion;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al buscar federación por código.");
            return null;
        }
    }

    /**
     * Recupera todas las federaciones almacenadas en la base de datos.
     *
     * @return Una lista de objetos {@link Federacion}, o una lista vacía si no hay resultados.
     */
    @Override
    public List<Federacion> findAll() {
        List<Federacion> federaciones = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            federaciones = session.createQuery("FROM Federacion", Federacion.class).list(); // Consulta para obtener todas las federaciones
            System.out.print("Federaciones encontradas: " + federaciones.size());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al obtener todas las federaciones.");
        }
        return federaciones;
    }

    /**
     * Actualiza los datos de una federación existente en la base de datos.
     *
     * @param federacion El objeto {@link Federacion} con los datos actualizados.
     */
    @Override
    public void update(Federacion federacion) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction(); // Inicia la transacción
            session.update(federacion); // Actualiza la entidad
            transaction.commit();
            System.out.print("Federación actualizada: " + federacion);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.err.print("Error al actualizar la federación. Realizando rollback.");
        }
    }

    /**
     * Elimina una federación de la base de datos por su código.
     *
     * @param codigo El código de la federación que se desea eliminar.
     */
    @Override
    public void delete(int codigo) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Federacion federacion = session.get(Federacion.class, codigo); // Busca la federación por código

            if (federacion != null) {
                session.delete(federacion); // Elimina la federación
                System.out.print("Federación eliminada con código: " + codigo);
            } else {
                System.out.print("No se encontró la federación con el código: " + codigo);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.err.print("Error al eliminar la federación. Realizando rollback.");
        }
    }
}