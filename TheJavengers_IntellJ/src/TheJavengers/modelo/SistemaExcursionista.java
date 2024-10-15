package TheJavengers.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import TheJavengers.Excepciones.*;

public class SistemaExcursionista {

    private List<Socio> socios;
    private List<Excursion> excursiones;
    private List<Inscripcion> inscripciones;
    private List<Federacion> federaciones;

    public SistemaExcursionista() {
        socios = new ArrayList<>();
        excursiones = new ArrayList<>();
        inscripciones = new ArrayList<>();
        federaciones = new ArrayList<>();

        precargarFederaciones();

    }

    // Métodos para gestionar socios
    private void precargarFederaciones() {
        federaciones.add(new Federacion("123", "Montañaeros por el mundo"));
        federaciones.add(new Federacion("234", "Escaladas locas"));
        federaciones.add(new Federacion("567", "La vida en el monte"));
    }
    public List<Federacion> obtenerFederaciones() {
        return federaciones;
    }
    public Federacion buscarFederacion(String codigo) throws FederacionNoEncontradaException {
        for (Federacion federacion : federaciones) {
            if (federacion.getCodigo().equals(codigo)) {
                return federacion;
            }
        }
        throw new FederacionNoEncontradaException("Federación con código " + codigo + " no encontrada.");
    }

    public void registrarSocio(Socio socio) throws SocioYaExisteException {
        for (Socio s : socios) {
            if (s.getIdSocio().equals(socio.getIdSocio())) {
                throw new SocioYaExisteException("El ID de socio ya existe.");
            }
        }
        socios.add(socio);
    }

    public Socio buscarSocio(String idSocio) throws SocioNoEncontradoException {
        for (Socio socio : socios) {
            if (socio.getIdSocio().equals(idSocio)) {
                return socio;
            }
        }
        throw new SocioNoEncontradoException("Socio con ID " + idSocio + " no encontrado.");
    }

    // Métodos para gestionar excursiones

    public void registrarExcursion(Excursion excursion) throws ExcursionYaExisteException {
        for (Excursion e : excursiones) {
            if (e.getIdExcursion().equals(excursion.getIdExcursion())) {
                throw new ExcursionYaExisteException("La excursión con ID " + excursion.getIdExcursion() + " ya existe.");
            }
        }
        excursiones.add(excursion);
    }

    public Excursion buscarExcursion(String idExcursion) throws ExcursionNoEncontradaException {
        for (Excursion excursion : excursiones) {
            if (excursion.getIdExcursion().equals(idExcursion)) {
                return excursion;
            }
        }
        throw new ExcursionNoEncontradaException("Excursión con ID " + idExcursion + " no encontrada.");
    }

    // Métodos para gestionar inscripciones

    public void inscribirSocioEnExcursion(String idSocio, String idExcursion) throws SocioNoEncontradoException, ExcursionNoEncontradaException {
        Socio socio = buscarSocio(idSocio);
        Excursion excursion = buscarExcursion(idExcursion);
        Inscripcion inscripcion = new Inscripcion(inscripciones.size() + 1, socio, excursion, LocalDate.now());
        inscripciones.add(inscripcion);
    }

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

    public List<Excursion> mostrarExcursionesPorFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Excursion> excursionesFiltradas = new ArrayList<>();
        for (Excursion excursion : excursiones) {
            if (!excursion.getFechaExcursion().isBefore(fechaInicio) && !excursion.getFechaExcursion().isAfter(fechaFin)) {
                excursionesFiltradas.add(excursion);
            }
        }
        return excursionesFiltradas;
    }

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

    public String mostrarFacturaMensual(String idSocio) {
        StringBuilder factura = new StringBuilder();
        try {
            Socio socio = buscarSocio(idSocio);
            float totalExcursiones = 0.0f;
            for (Inscripcion inscripcion : inscripciones) {
                if (inscripcion.getSocio().getIdSocio().equals(idSocio)) {
                    float precioExcursion = inscripcion.getExcursion().getPrecio();
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

    public void modificarSeguroSocioEstandar(String idSocio, TipoSeguro nuevoSeguro) throws SocioNoEncontradoException, TipoSocioNoValidoException {
        Socio socioModificar = buscarSocio(idSocio);
        if (socioModificar instanceof SocioEstandar) {
            ((SocioEstandar) socioModificar).setSeguro(nuevoSeguro);
        } else {
            throw new TipoSocioNoValidoException("El socio no es de tipo estándar.");
        }
    }

    public List<Inscripcion> mostrarInscripcionesFiltradas(String idSocio, LocalDate fechaInicio, LocalDate fechaFin) {
        List<Inscripcion> inscripcionesFiltradas = new ArrayList<>();
        for (Inscripcion inscripcion : inscripciones) {
            boolean coincideSocio = (idSocio == null || inscripcion.getSocio().getIdSocio().equals(idSocio));
            boolean coincideFecha = (fechaInicio == null || fechaFin == null) ||
                    (!inscripcion.getExcursion().getFechaExcursion().isBefore(fechaInicio) &&
                            !inscripcion.getExcursion().getFechaExcursion().isAfter(fechaFin));
            if (coincideSocio && coincideFecha) {
                inscripcionesFiltradas.add(inscripcion);
            }
        }
        return inscripcionesFiltradas;
    }
}