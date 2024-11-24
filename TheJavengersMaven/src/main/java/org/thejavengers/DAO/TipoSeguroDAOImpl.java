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
            // Buscamos en la tabla 'seguros' usando el valor del enum en minúsculas.
            String hql = "SELECT t.TipoSeguro FROM Seguro t WHERE t.TipoSeguro = :name";

            Query<String> query = session.createQuery(hql, String.class);
            query.setParameter("name", name.toLowerCase()); // El valor de 'name' debe estar en minúsculas
            String result = query.uniqueResult();

            if (result != null) {
                // Si se encuentra, convertimos el valor de la cadena de vuelta al enum.
                tipoSeguro = TipoSeguro.fromString(result.toUpperCase());
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
