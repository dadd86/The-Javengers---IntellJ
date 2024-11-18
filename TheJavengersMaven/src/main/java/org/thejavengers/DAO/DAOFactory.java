package org.thejavengers.DAO;

public class DAOFactory {

    public static SocioDAO getSocioDAO() {
        return new SocioDAOImpl();
    }

    public static ExcursionDAO getExcursionDAO() {
        return new ExcursionDAOImpl();
    }

    public static InscripcionDAO getInscripcionDAO() {
        return new InscripcionDAOImpl();
    }

    public static FederacionDAO getFederacionDAO() {
        return new FederacionDAOImpl();
    }

    public static TipoSeguroDAO getTipoSeguroDAO() {
        return new TipoSeguroDAOImpl();
    }
}