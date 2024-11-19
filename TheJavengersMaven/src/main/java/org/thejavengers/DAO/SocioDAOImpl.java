package org.thejavengers.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thejavengers.modelo.*;
import org.thejavengers.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class SocioDAOImpl implements SocioDAO {

    private static final Logger logger = LoggerFactory.getLogger(SocioDAOImpl.class);

    @Override
    public void save(Socio socio) {
        if (socio == null) {
            logger.warn("No se puede guardar un socio nulo");
            throw new IllegalArgumentException("El socio no puede ser nulo.");
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(socio);
            transaction.commit();
            logger.info("Socio guardado correctamente: {}", socio);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Error al guardar el socio: {}", e.getMessage(), e);
        }
    }

    @Override
    public Socio findById(int id) {
        if (id <= 0) {
            logger.warn("ID inválido para buscar socio: {}", id);
            return null;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Socio socio = session.get(Socio.class, id);
            if (socio != null) {
                logger.info("Socio encontrado: {}", socio);
            } else {
                logger.warn("No se encontró socio con ID: {}", id);
            }
            return socio;
        } catch (Exception e) {
            logger.error("Error al buscar el socio con ID {}: {}", id, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<Socio> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Socio> socios = session.createQuery("from Socio", Socio.class).list();
            logger.info("Se recuperaron {} socios", socios.size());
            return socios;
        } catch (Exception e) {
            logger.error("Error al recuperar todos los socios: {}", e.getMessage(), e);
            return List.of(); // Devuelve lista vacía en caso de error
        }
    }

    @Override
    public void update(Socio socio) {
        if (socio == null) {
            logger.warn("No se puede actualizar un socio nulo");
            return;
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(socio); // Utiliza merge para entidades detached
            transaction.commit();
            logger.info("Socio actualizado correctamente: {}", socio);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Error al actualizar el socio con ID {}: {}", socio.getIdSocio(), e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        if (id <= 0) {
            logger.warn("ID inválido para eliminar socio: {}", id);
            return;
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Socio socio = session.get(Socio.class, id);
            if (socio != null) {
                session.delete(socio);
                transaction.commit();
                logger.info("Socio con ID {} eliminado correctamente", id);
            } else {
                logger.warn("No se encontró el socio con ID: {}", id);
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Error al eliminar el socio con ID {}: {}", id, e.getMessage(), e);
        }
    }

    @Override
    public float obtenerFacturaMensual(int idSocio) {
        Socio socio = findById(idSocio);
        if (socio == null) {
            throw new IllegalArgumentException("Socio no encontrado con ID: " + idSocio);
        }
        return socio.calcularCuotaMensual();
    }

    @Override
    public void modificarSeguro(int idSocio, TipoSeguro nuevoSeguro) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Socio socio = session.get(Socio.class, idSocio);

            if (socio instanceof SocioEstandar) {
                ((SocioEstandar) socio).setSeguro(nuevoSeguro);
                session.merge(socio);
                transaction.commit();
                logger.info("Seguro del socio con ID {} actualizado a {}", idSocio, nuevoSeguro);
            } else {
                logger.warn("El socio con ID {} no es del tipo estándar. No se puede modificar el seguro.", idSocio);
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Error al modificar el seguro del socio con ID {}: {}", idSocio, e.getMessage(), e);
        }
    }

    @Override
    public List<Socio> findByExcursionId(int idExcursion) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = """
                select s
                from Socio s
                join s.inscripciones i
                where i.excursion.idExcursion = :idExcursion
            """;

            List<Socio> socios = session.createQuery(hql, Socio.class)
                    .setParameter("idExcursion", idExcursion)
                    .list();
            logger.info("Se encontraron {} socios inscritos en la excursión con ID {}", socios.size(), idExcursion);
            return socios;
        } catch (Exception e) {
            logger.error("Error al obtener socios inscritos en la excursión con ID {}: {}", idExcursion, e.getMessage(), e);
            return List.of();
        }
    }
}
