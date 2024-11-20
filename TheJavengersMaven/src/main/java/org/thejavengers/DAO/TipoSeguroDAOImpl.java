package org.thejavengers.DAO;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thejavengers.modelo.TipoSeguro;
import org.thejavengers.utils.HibernateUtil;

public class TipoSeguroDAOImpl implements TipoSeguroDAO {

    private static final Logger logger = LoggerFactory.getLogger(TipoSeguroDAOImpl.class);

    /**
     * Busca un TipoSeguro en la base de datos basado en su nombre.
     *
     * @param name El nombre del tipo de seguro (por ejemplo, "BASICO" o "COMPLETO").
     * @return El TipoSeguro correspondiente, o null si no se encuentra.
     */
    @Override
    public TipoSeguro findById(String name) {
        TipoSeguro tipoSeguro = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from TipoSeguro where name = :name"; // Consulta HQL para buscar por nombre
            Query<TipoSeguro> query = session.createQuery(hql, TipoSeguro.class);
            query.setParameter("name", name.toUpperCase());
            tipoSeguro = query.uniqueResult();

            if (tipoSeguro != null) {
                logger.info("TipoSeguro encontrado: {}", tipoSeguro);
            } else {
                logger.warn("Tipo de seguro no encontrado: {}", name);
            }
        } catch (Exception e) {
            logger.error("Error al buscar el TipoSeguro con nombre {}: {}", name, e.getMessage(), e);
        }

        return tipoSeguro;
    }
}
