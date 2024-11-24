package org.thejavengers.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thejavengers.modelo.Socio;
import org.thejavengers.modelo.Inscripcion;
import org.thejavengers.modelo.Excursion;
import org.thejavengers.modelo.*;
import org.thejavengers.utils.HibernateUtil;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.time.LocalDate;

/**
 * Implementación de la interfaz {@link InscripcionDAO} utilizando Hibernate.
 *
 * <p>Esta clase gestiona las operaciones CRUD para el modelo {@link Inscripcion}
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
public class InscripcionDAOImpl implements InscripcionDAO {

    // Logger para registrar eventos y errores
    private static final Logger logger = LoggerFactory.getLogger(InscripcionDAOImpl.class);

    /**
     * Guarda una nueva inscripción en la base de datos.
     *
     * @param inscripcion El objeto {@link Inscripcion} que se desea guardar.
     */
    @Override
    public void save(Inscripcion inscripcion) {
        Transaction transaction = null; // Hibernate gestiona la transacción
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction(); // Inicia la transacción

            session.save(inscripcion); // Persistencia de inscripción en la base de datos

            transaction.commit(); // Confirma la transacción
            System.out.print("Inscripción guardada: " + inscripcion);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Realiza rollback en caso de error
            }
            e.printStackTrace();
            System.err.print("Error al guardar la inscripción. Realizando rollback.");
        }
    }

    /**
     * Recupera una inscripción de la base de datos por su código.
     *
     * @param idInscripcion El ID de la inscripción que se desea recuperar.
     * @return La inscripción correspondiente al ID, o {@code null} si no se encuentra.
     */
    @Override
    public Inscripcion findById(int idInscripcion) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Inscripcion inscripcion = session.get(Inscripcion.class, idInscripcion);

            if (inscripcion != null) {
                System.out.print("Inscripción encontrada: " + inscripcion);
                return inscripcion;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.print("Inscripción no encontrada con ID: " + idInscripcion);
        return null;

        // !!!!!! EN CASO DE QUE NO FUNCIONE BIEN POR EL FETCH, PROBAD LO SIGUIENTE !!!!!!
//        public Inscripcion findById(int idInscripcion) {
//            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//                // Utilizamos una consulta HQL con FETCH JOIN para cargar las relaciones
//                String hql = "FROM Inscripcion i " +
//                        "JOIN FETCH i.socio " +
//                        "JOIN FETCH i.excursion " +
//                        "WHERE i.idInscripcion = :id";
//                Query<Inscripcion> query = session.createQuery(hql, Inscripcion.class);
//                query.setParameter("id", idInscripcion);
//
//                Inscripcion inscripcion = query.uniqueResult();
//
//                if (inscripcion != null) {
//                    System.out.println("Inscripción encontrada: " + inscripcion);
//                    return inscripcion;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            System.out.println("Inscripción no encontrada con ID: " + idInscripcion);
//            return null;
//        }
    }


    @Override
    public List<Inscripcion> findByDateRange(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Inscripcion> inscripciones = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Consulta HQL para obtener las inscripciones dentro del rango de fechas
            String hql = "FROM Inscripcion i " + "WHERE i.fechaInscripcion BETWEEN :fechaInicio AND :fechaFin";

            // Crear la consulta y establecer los parámetros
            Query<Inscripcion> query = session.createQuery(hql, Inscripcion.class);
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaFin", fechaFin);

            // Ejecutar la consulta y obtener los resultados
            inscripciones = query.list();

            // Si no se encuentran inscripciones
            if (inscripciones.isEmpty()) {
                System.out.print("No se encontraron inscripciones en el rango de fechas.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return inscripciones;

        // !!!!! SI FUERA NECESARIO OPTIMIZAR LA CONSULTA !!!!!
//        String hql = "FROM Inscripcion i " +
//                "JOIN FETCH i.socio " +   // Carga el socio de manera anticipada
//                "JOIN FETCH i.excursion " + // Carga la excursión de manera anticipada
//                "WHERE i.fechaInscripcion BETWEEN :fechaInicio AND :fechaFin";

    }

    /**
     * Recupera todas las inscripciones almacenadas en la base de datos.
     *
     * @return Una lista de objetos {@link Inscripcion}, o una lista vacía si no hay resultados.
     */
    @Override
    public List<Inscripcion> findAll() {
        List<Inscripcion> inscripciones = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Consulta HQL para obtener todas las inscripciones
            String hql = "FROM Inscripcion";

            // Crear la consulta
            Query<Inscripcion> query = session.createQuery(hql, Inscripcion.class);

            // Ejecutar la consulta y obtener los resultados
            inscripciones = query.list();

            // Si no se encuentran inscripciones
            if (inscripciones.isEmpty()) {
                System.out.print("No se encontraron inscripciones.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener todas las inscripciones: " + e.getMessage(), e);
        }

        return inscripciones;
    }


    @Override
    public List<Inscripcion> findAll(Integer idSocio, LocalDate fechaInicio, LocalDate fechaFin) {
        List<Inscripcion> inscripciones = new ArrayList<>();
        // Usamos HQL para filtrar las inscripciones
        StringBuilder hql = new StringBuilder("FROM Inscripcion i WHERE 1=1");

        // Añadir condiciones de filtro dinámicamente
        if (idSocio != null) {
            hql.append(" AND i.socio.idSocio = :idSocio");
        }

        if (fechaInicio != null) {
            hql.append(" AND i.fechaInscripcion >= :fechaInicio");
        }

        if (fechaFin != null) {
            hql.append(" AND i.fechaInscripcion <= :fechaFin");
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Inscripcion> query = session.createQuery(hql.toString(), Inscripcion.class);

            if (idSocio != null) {
                query.setParameter("idSocio", idSocio);
            }
            if (fechaInicio != null) {
                query.setParameter("fechaInicio", fechaInicio);
            }
            if (fechaFin != null) {
                query.setParameter("fechaFin", fechaFin);
            }

            // Ejecutar la consulta y obtener los resultados
            inscripciones = query.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener todas las inscripciones: " + e.getMessage(), e);
        }

        return inscripciones;
    }

    /**
     * Actualiza los datos de una inscripción existente en la base de datos.
     *
     * @param inscripcion El objeto {@link Inscripcion} con los datos actualizados.
     */
    @Override
    public void update(Inscripcion inscripcion) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                // Recuperamos la inscripción existente por su ID
                Inscripcion existingInscripcion = session.get(Inscripcion.class, inscripcion.getIdInscripcion());

                if (existingInscripcion != null) {
                    // Actualizamos los campos de la inscripción con los nuevos valores
                    existingInscripcion.setSocio(inscripcion.getSocio());
                    existingInscripcion.setExcursion(inscripcion.getExcursion());
                    existingInscripcion.setFechaInscripcion(inscripcion.getFechaInscripcion());

                    // Hibernate maneja automáticamente el proceso de actualización de la entidad
                    session.update(existingInscripcion);

                    // Commit de la transacción
                    transaction.commit();
                    System.out.print("Inscripción actualizada: " + existingInscripcion);
                } else {
                    System.out.print("No se encontró la inscripción con ID: " + inscripcion.getIdInscripcion());
                }
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina una inscripción de la base de datos por su código.
     *
     * @param idInscripcion El ID de la inscripción que se desea eliminar.
     */
    @Override
    public void deleteById(int idInscripcion) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                Inscripcion inscripcion = session.get(Inscripcion.class, idInscripcion);

                if (inscripcion != null) {
                    session.delete(inscripcion);

                    transaction.commit();
                    System.out.print("Inscripción eliminada con ID: " + idInscripcion);
                } else {
                    System.out.print("No se encontró la inscripción con ID: " + idInscripcion);
                }
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}