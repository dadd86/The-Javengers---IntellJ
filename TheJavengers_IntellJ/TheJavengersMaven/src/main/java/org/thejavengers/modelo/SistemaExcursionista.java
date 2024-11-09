package org.thejavengers.modelo;

import org.thejavengers.DAO.*;

import java.util.List;
import java.time.LocalDate;
import org.thejavengers.Excepciones.*;
import java.util.stream.Collectors;

/**
 * Clase que representa el sistema de gestión de excursiones del centro excursionista.
 * Gestiona las listas de socios, excursiones, inscripciones y federaciones, y proporciona
 * métodos para realizar operaciones como registrar socios, gestionar inscripciones y excursiones,
 * y generar facturas.
 */
public class SistemaExcursionista {

    // Atributos que almacenan las listas de socios, excursiones, inscripciones y federaciones
    private final SocioDAO socioDAO;
    private final ExcursionDAO excursionDAO;
    private final InscripcionDAO inscripcionDAO;
    private final FederacionDAO federacionDAO;

    /**
     * Constructor que inicializa el sistema con listas vacías para socios, excursiones, inscripciones y federaciones.
     * Además, precarga algunas federaciones de ejemplo.
     */
    public SistemaExcursionista() {
        this.socioDAO = DAOFactory.getSocioDAO();
        this.excursionDAO = DAOFactory.getExcursionDAO();
        this.inscripcionDAO = DAOFactory.getInscripcionDAO();
        this.federacionDAO = DAOFactory.getFederacionDAO();
        //precargarFederaciones();
    }

    // Métodos para gestionar federaciones

    /**
     * Precarga algunas federaciones de ejemplo en el sistema.
     */
   // private void precargarFederaciones() {
   //     federaciones.add(new Federacion("123", "Montañeros por el mundo"));
   //     federaciones.add(new Federacion("234", "Escaladas locas"));
   //     federaciones.add(new Federacion("567", "La vida en el monte"));
   // }

    /**
     * Devuelve una lista de todas las federaciones registradas en el sistema.
     *
     * @return Lista de federaciones.
     */
    // Métodos para gestionar federaciones
    public List<Federacion> obtenerFederaciones() {
        return federacionDAO.findAll();
    }

    /**
     * Busca una federación por su código único.
     *
     * @param codigo El código de la federación a buscar.
     * @return La federación encontrada.
     * @throws FederacionNoEncontradaException Si no se encuentra ninguna federación con el código proporcionado.
     */
    public Federacion buscarFederacion(int codigo) throws FederacionNoEncontradaException {
        Federacion federacion = federacionDAO.findByCodigo(codigo);
        if (federacion == null) throw new FederacionNoEncontradaException("Federación no encontrada");
        return federacion;
    }

    // Métodos para gestionar socios

    /**
     * Registra un nuevo socio en el sistema.
     *
     * @param socio El socio a registrar.
     * @throws SocioYaExisteException Si ya existe un socio con el mismo ID.
     */
    public void registrarSocio(Socio socio) throws SocioYaExisteException {
        socioDAO.save(socio);
    }
    /**
     * Busca un socio por su ID único.
     *
     * @param idSocio El ID del socio a buscar.
     * @return El socio encontrado.
     * @throws SocioNoEncontradoException Si no se encuentra ningún socio con el ID proporcionado.
     */
    public Socio buscarSocio(String idSocio) throws SocioNoEncontradoException {
        Socio socio = socioDAO.findById(Integer.parseInt(idSocio));
        if (socio == null) throw new SocioNoEncontradoException("Socio no encontrado");
        return socio;
    }

    // Métodos para gestionar excursiones

    /**
     * Registra una nueva excursión en el sistema.
     *
     * @param excursion La excursión a registrar.
     * @throws ExcursionYaExisteException Si ya existe una excursión con el mismo ID.
     */
    public void registrarExcursion(Excursion excursion) throws ExcursionYaExisteException {
        excursionDAO.save(excursion);
    }

    /**
     * Busca una excursión por su ID único.
     *
     * @param idExcursion El ID de la excursión a buscar.
     * @return La excursión encontrada.
     * @throws ExcursionNoEncontradaException Si no se encuentra ninguna excursión con el ID proporcionado.
     */
    public Excursion buscarExcursion(int idExcursion) throws ExcursionNoEncontradaException {
        Excursion excursion = excursionDAO.findById(idExcursion);
        if (excursion == null) throw new ExcursionNoEncontradaException("Excursión no encontrada");
        return excursion;
    }

    // Métodos para gestionar inscripciones

    /**
     * Inscribe a un socio en una excursión.
     *
     * @param idSocio     El ID del socio que se quiere inscribir.
     * @param idExcursion El ID de la excursión a la que se inscribe.
     * @throws SocioNoEncontradoException      Si no se encuentra el socio.
     * @throws ExcursionNoEncontradaException  Si no se encuentra la excursión.
     */
    public void inscribirSocioEnExcursion(String idSocio, int idExcursion) throws SocioNoEncontradoException, ExcursionNoEncontradaException {
        Socio socio = buscarSocio(idSocio);
        Excursion excursion = buscarExcursion(idExcursion);
        Inscripcion inscripcion = new Inscripcion(0, socio, excursion, LocalDate.now());
        inscripcionDAO.save(inscripcion);
    }

    /**
     * Elimina una inscripción si aún no ha comenzado la excursión.
     *
     * @param idInscripcion El ID de la inscripción a eliminar.
     * @param fechaActual   La fecha actual.
     * @throws InscripcionNoEncontradaException Si no se encuentra la inscripción o la excursión ya ha comenzado.
     */
    public void eliminarInscripcion(int idInscripcion, LocalDate fechaActual) throws InscripcionNoEncontradaException {
        inscripcionDAO.deleteById(idInscripcion);
    }
    /**
     * Lista los socios inscritos en una excursión específica.
     *
     * @param idExcursion El ID de la excursión.
     * @return Una lista de socios inscritos en la excursión.
     * @throws ExcursionNoEncontradaException Si no se encuentra la excursión.
     */
    public List<Socio> listarSociosEnExcursion(int idExcursion) throws ExcursionNoEncontradaException {
        // Comprueba si la excursión existe antes de buscar los socios
        Excursion excursion = excursionDAO.findById(idExcursion);
        if (excursion == null) {
            throw new ExcursionNoEncontradaException("Excursión no encontrada con ID: " + idExcursion);
        }

        // Usa el DAO para obtener la lista de socios
        return socioDAO.findByExcursionId(idExcursion);
    }




    // Métodos adicionales

    /**
     * Muestra las excursiones que ocurren dentro de un rango de fechas.
     *
     * @param fechaInicio La fecha de inicio del rango.
     * @param fechaFin    La fecha de fin del rango.
     * @return Una lista de excursiones que ocurren entre las fechas especificadas.
     */
    public List<Excursion> mostrarExcursionesPorFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return excursionDAO.findAll().stream()
                .filter(exc -> !exc.getFechaExcursion().isBefore(fechaInicio) && !exc.getFechaExcursion().isAfter(fechaFin))
                .collect(Collectors.toList());
    }

    /**
     * Elimina un socio del sistema, verificando que no tenga inscripciones activas y no sea tutor de un socio infantil.
     *
     * @param idSocio El ID del socio a eliminar.
     * @throws SocioNoEncontradoException           Si no se encuentra el socio.
     * @throws SocioConInscripcionesActivasException Si el socio tiene inscripciones activas.
     * @throws SocioConHijosException               Si el socio es tutor de un socio infantil.
     */
    public void eliminarSocio(String idSocio) throws SocioNoEncontradoException {
        socioDAO.delete(Integer.parseInt(idSocio));
    }

    /**
     * Muestra la factura mensual de un socio, incluyendo el costo de las excursiones y la cuota mensual.
     *
     * @param idSocio El ID del socio.
     * @return Una cadena de texto que representa la factura mensual.
     */
    // Métodos adicionales
    public float mostrarFacturaMensual(String idSocio) {
        return socioDAO.obtenerFacturaMensual(Integer.parseInt(idSocio));
    }

    /**
     * Modifica el seguro de un socio de tipo estándar.
     *
     * @param idSocio     El ID del socio a modificar.
     * @param nuevoSeguro El nuevo tipo de seguro a aplicar.
     * @throws SocioNoEncontradoException Si no se encuentra el socio.
     * @throws TipoSocioNoValidoException Si el socio no es de tipo estándar.
     */
    public void modificarSeguroSocioEstandar(int idSocio, TipoSeguro nuevoSeguro) throws SocioNoEncontradoException, TipoSocioNoValidoException {
        socioDAO.modificarSeguro(idSocio, nuevoSeguro);
    }

    /**
     * Filtra las inscripciones de un socio o por un rango de fechas.
     *
     * @param idSocio     El ID del socio (puede ser nulo).
     * @param fechaInicio La fecha de inicio del filtro (puede ser nula).
     * @param fechaFin    La fecha de fin del filtro (puede ser nula).
     * @return Una lista de inscripciones que coinciden con los filtros.
     */
    public List<Inscripcion> mostrarInscripcionesFiltradas(Integer idSocio, LocalDate fechaInicio, LocalDate fechaFin) {
        return inscripcionDAO.findAll(idSocio, fechaInicio, fechaFin);
    }



    public List<Socio> obtenerSocios() {
        return socioDAO.findAll();
    }


}
