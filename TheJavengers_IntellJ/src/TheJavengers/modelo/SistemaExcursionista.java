package TheJavengers.modelo;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import TheJavengers.Excepciones.*;

/**
 * Clase que representa el sistema de gestión de excursiones del centro excursionista.
 * Gestiona las listas de socios, excursiones, inscripciones y federaciones, y proporciona
 * métodos para realizar operaciones como registrar socios, gestionar inscripciones y excursiones,
 * y generar facturas.
 */
public class SistemaExcursionista {

    // Atributos que almacenan las listas de socios, excursiones, inscripciones y federaciones
    private List<Socio> socios;
    private List<Excursion> excursiones;
    private List<Inscripcion> inscripciones;
    private List<Federacion> federaciones;

    /**
     * Constructor que inicializa el sistema con listas vacías para socios, excursiones, inscripciones y federaciones.
     * Además, precarga algunas federaciones de ejemplo.
     */
    public SistemaExcursionista() {
        socios = new ArrayList<>();
        excursiones = new ArrayList<>();
        inscripciones = new ArrayList<>();
        federaciones = new ArrayList<>();
        precargarFederaciones();
    }

    // Métodos para gestionar federaciones

    /**
     * Precarga algunas federaciones de ejemplo en el sistema.
     */
    private void precargarFederaciones() {
        federaciones.add(new Federacion("123", "Montañeros por el mundo"));
        federaciones.add(new Federacion("234", "Escaladas locas"));
        federaciones.add(new Federacion("567", "La vida en el monte"));
    }

    /**
     * Devuelve una lista de todas las federaciones registradas en el sistema.
     *
     * @return Lista de federaciones.
     */
    public List<Federacion> obtenerFederaciones() {
        return new ArrayList<>(federaciones); // Devolver una copia para proteger la encapsulación.
    }

    /**
     * Busca una federación por su código único.
     *
     * @param codigo El código de la federación a buscar.
     * @return La federación encontrada.
     * @throws FederacionNoEncontradaException Si no se encuentra ninguna federación con el código proporcionado.
     */
    public Federacion buscarFederacion(String codigo) throws FederacionNoEncontradaException {
        for (Federacion federacion : federaciones) {
            if (federacion.getCodigo().equals(codigo)) {
                return federacion;
            }
        }
        throw new FederacionNoEncontradaException("Federación con código " + codigo + " no encontrada.");
    }

    // Métodos para gestionar socios

    /**
     * Registra un nuevo socio en el sistema.
     *
     * @param socio El socio a registrar.
     * @throws SocioYaExisteException Si ya existe un socio con el mismo ID.
     */
    public void registrarSocio(Socio socio) throws SocioYaExisteException {
        for (Socio s : socios) {
            if (s.getIdSocio().equals(socio.getIdSocio())) {
                throw new SocioYaExisteException("El ID de socio ya existe.");
            }
        }
        socios.add(socio);
    }

    /**
     * Busca un socio por su ID único.
     *
     * @param idSocio El ID del socio a buscar.
     * @return El socio encontrado.
     * @throws SocioNoEncontradoException Si no se encuentra ningún socio con el ID proporcionado.
     */
    public Socio buscarSocio(String idSocio) throws SocioNoEncontradoException {
        for (Socio socio : socios) {
            if (socio.getIdSocio().equals(idSocio)) {
                return socio;
            }
        }
        throw new SocioNoEncontradoException("Socio con ID " + idSocio + " no encontrado.");
    }

    // Métodos para gestionar excursiones

    /**
     * Registra una nueva excursión en el sistema.
     *
     * @param excursion La excursión a registrar.
     * @throws ExcursionYaExisteException Si ya existe una excursión con el mismo ID.
     */
    public void registrarExcursion(Excursion excursion) throws ExcursionYaExisteException {
        for (Excursion e : excursiones) {
            if (e.getIdExcursion().equals(excursion.getIdExcursion())) {
                throw new ExcursionYaExisteException("La excursión con ID " + excursion.getIdExcursion() + " ya existe.");
            }
        }
        excursiones.add(excursion);
    }

    /**
     * Busca una excursión por su ID único.
     *
     * @param idExcursion El ID de la excursión a buscar.
     * @return La excursión encontrada.
     * @throws ExcursionNoEncontradaException Si no se encuentra ninguna excursión con el ID proporcionado.
     */
    public Excursion buscarExcursion(String idExcursion) throws ExcursionNoEncontradaException {
        for (Excursion excursion : excursiones) {
            if (excursion.getIdExcursion().equals(idExcursion)) {
                return excursion;
            }
        }
        throw new ExcursionNoEncontradaException("Excursión con ID " + idExcursion + " no encontrada.");
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
    public void inscribirSocioEnExcursion(String idSocio, String idExcursion) throws SocioNoEncontradoException, ExcursionNoEncontradaException {
        Socio socio = buscarSocio(idSocio);
        Excursion excursion = buscarExcursion(idExcursion);
        Inscripcion inscripcion = new Inscripcion(inscripciones.size() + 1, socio, excursion, LocalDate.now());
        inscripciones.add(inscripcion);
    }

    /**
     * Elimina una inscripción si aún no ha comenzado la excursión.
     *
     * @param idInscripcion El ID de la inscripción a eliminar.
     * @param fechaActual   La fecha actual.
     * @throws InscripcionNoEncontradaException Si no se encuentra la inscripción o la excursión ya ha comenzado.
     */
    public void eliminarInscripcion(int idInscripcion, LocalDate fechaActual) throws InscripcionNoEncontradaException {
        Inscripcion inscripcionAEliminar = null;
        for (Inscripcion inscripcion : inscripciones) {
            if (inscripcion.getIdInscripcion() == idInscripcion && fechaActual.isBefore(inscripcion.getExcursion().getFechaExcursion())) {
                inscripcionAEliminar = inscripcion;
                break;
            }
        }
        if (inscripcionAEliminar != null) {
            inscripciones.remove(inscripcionAEliminar);
        } else {
            throw new InscripcionNoEncontradaException("Inscripción no encontrada o no es posible eliminarla.");
        }
    }

    /**
     * Lista los socios inscritos en una excursión específica.
     *
     * @param idExcursion El ID de la excursión.
     * @return Una lista de socios inscritos en la excursión.
     * @throws ExcursionNoEncontradaException Si no se encuentra la excursión.
     */
    public List<Socio> listarSociosEnExcursion(String idExcursion) throws ExcursionNoEncontradaException {
        List<Socio> sociosInscritos = new ArrayList<>();
        Excursion excursion = buscarExcursion(idExcursion);
        for (Inscripcion inscripcion : inscripciones) {
            if (inscripcion.getExcursion().equals(excursion)) {
                sociosInscritos.add(inscripcion.getSocio());
            }
        }
        return sociosInscritos;
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
        List<Excursion> excursionesFiltradas = new ArrayList<>();
        for (Excursion excursion : excursiones) {
            if (!excursion.getFechaExcursion().isBefore(fechaInicio) && !excursion.getFechaExcursion().isAfter(fechaFin)) {
                excursionesFiltradas.add(excursion);
            }
        }
        return excursionesFiltradas;
    }

    /**
     * Elimina un socio del sistema, verificando que no tenga inscripciones activas y no sea tutor de un socio infantil.
     *
     * @param idSocio El ID del socio a eliminar.
     * @throws SocioNoEncontradoException           Si no se encuentra el socio.
     * @throws SocioConInscripcionesActivasException Si el socio tiene inscripciones activas.
     * @throws SocioConHijosException               Si el socio es tutor de un socio infantil.
     */
    public void eliminarSocio(String idSocio) throws SocioNoEncontradoException, SocioConInscripcionesActivasException, SocioConHijosException {
        Socio socioAEliminar = buscarSocio(idSocio);

        // Verificar si el socio tiene inscripciones activas
        for (Inscripcion inscripcion : inscripciones) {
            if (inscripcion.getSocio().equals(socioAEliminar)) {
                throw new SocioConInscripcionesActivasException("El socio tiene inscripciones activas.");
            }
        }

        // Verificar si es tutor de algún socio infantil
        for (Socio socio : socios) {
            if (socio instanceof SocioInfantil) {
                SocioInfantil socioInfantil = (SocioInfantil) socio;
                if (socioInfantil.getIdSocioTutor().equals(idSocio)) {
                    throw new SocioConHijosException("No se puede eliminar el socio porque es tutor de un socio infantil.");
                }
            }
        }

        // Eliminar el socio
        socios.remove(socioAEliminar);
    }

    /**
     * Muestra la factura mensual de un socio, incluyendo el costo de las excursiones y la cuota mensual.
     *
     * @param idSocio El ID del socio.
     * @return Una cadena de texto que representa la factura mensual.
     */
    public String mostrarFacturaMensual(String idSocio) {
        StringBuilder factura = new StringBuilder();
        try {
            Socio socio = buscarSocio(idSocio);
            float totalExcursiones = 0.0f;
            for (Inscripcion inscripcion : inscripciones) {
                if (inscripcion.getSocio().getIdSocio().equals(idSocio)) {
                    totalExcursiones += socio.calcularPrecioExcursion(inscripcion.getExcursion());
                }
            }
            float totalCuota = socio.calcularCuotaMensual();
            float totalPagar = totalCuota + totalExcursiones;
            factura.append("Factura mensual para el socio ").append(socio.getNombre()).append(":\n")
                    .append("Cuota mensual: ").append(totalCuota).append(" euros\n")
                    .append("Total por excursiones: ").append(totalExcursiones).append(" euros\n")
                    .append("Total a pagar: ").append(totalPagar).append(" euros");
        } catch (SocioNoEncontradoException e) {
            return e.getMessage();
        }
        return factura.toString();
    }

    /**
     * Modifica el seguro de un socio de tipo estándar.
     *
     * @param idSocio     El ID del socio a modificar.
     * @param nuevoSeguro El nuevo tipo de seguro a aplicar.
     * @throws SocioNoEncontradoException Si no se encuentra el socio.
     * @throws TipoSocioNoValidoException Si el socio no es de tipo estándar.
     */
    public void modificarSeguroSocioEstandar(String idSocio, TipoSeguro nuevoSeguro) throws SocioNoEncontradoException, TipoSocioNoValidoException {
        Socio socioModificar = buscarSocio(idSocio);
        if (socioModificar instanceof SocioEstandar) {
            ((SocioEstandar) socioModificar).setSeguro(nuevoSeguro);
        } else {
            throw new TipoSocioNoValidoException("El socio no es de tipo estándar.");
        }
    }

    /**
     * Filtra las inscripciones de un socio o por un rango de fechas.
     *
     * @param idSocio     El ID del socio (puede ser nulo).
     * @param fechaInicio La fecha de inicio del filtro (puede ser nula).
     * @param fechaFin    La fecha de fin del filtro (puede ser nula).
     * @return Una lista de inscripciones que coinciden con los filtros.
     */
    public List<Inscripcion> mostrarInscripcionesFiltradas(String idSocio, LocalDate fechaInicio, LocalDate fechaFin) {
        List<Inscripcion> inscripcionesFiltradas = new ArrayList<>();

        for (Inscripcion inscripcion : inscripciones) {
            boolean coincideSocio = (idSocio == null || idSocio.isEmpty() || inscripcion.getSocio().getIdSocio().equals(idSocio));
            boolean coincideFecha = (fechaInicio == null || fechaFin == null) ||
                    (!inscripcion.getExcursion().getFechaExcursion().isBefore(fechaInicio) &&
                            !inscripcion.getExcursion().getFechaExcursion().isAfter(fechaFin));

            // Filtrar por fecha si no se proporciona un ID de socio, o por ambas condiciones si se proporcionan ambos filtros
            if (coincideSocio && coincideFecha) {
                inscripcionesFiltradas.add(inscripcion);
            }
        }

        return inscripcionesFiltradas;
    }

    public List<Socio> obtenerSocios() {
        return socios; // Devuelve la lista de socios
    }
}
