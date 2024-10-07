package TheJavengers.controlador;

import TheJavengers.modelo.*;
import TheJavengers.Excepciones.*;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;

public class ControladorExcursionista {
    private SistemaExcursionista sistema;

    public ControladorExcursionista() {
        this.sistema = new SistemaExcursionista();
    }

    // Registrar socio
    public String registrarSocio(int tipoSocio, String idSocio, String nombre, String apellidos, String nif, TipoSeguro seguro, String codigoFederacion, String idSocioTutor) {
        try {
            Socio socio = null;
            switch (tipoSocio) {
                case 1: // Socio estándar
                    socio = new SocioEstandar(idSocio, nombre, apellidos, nif, seguro);
                    break;
                case 2: // Socio federado
                    try {
                        Federacion federacion = sistema.buscarFederacion(codigoFederacion);
                        socio = new SocioFederado(idSocio, nombre, apellidos, nif, federacion);
                    } catch (FederacionNoEncontradaException e) {
                        return "Error al registrar socio: " + e.getMessage();
                    }
                    break;
                case 3: // Socio infantil
                    // Validar que el socio tutor existe y es SocioEstandar o SocioFederado
                    try {
                        Socio socioTutor = sistema.buscarSocio(idSocioTutor);
                        if (socioTutor instanceof SocioEstandar || socioTutor instanceof SocioFederado) {
                            socio = new SocioInfantil(idSocio, nombre, apellidos, idSocioTutor);
                        } else {
                            return "Error al registrar socio: El socio tutor debe ser estándar o federado.";
                        }
                    } catch (SocioNoEncontradoException e) {
                        return "Error al registrar socio: El socio tutor no existe.";
                    }
                    break;
                default:
                    return "Tipo de socio no válido.";
            }
            sistema.registrarSocio(socio);
            return "Socio registrado con éxito.";
        } catch (SocioYaExisteException e) {
            return "Error al registrar socio: " + e.getMessage();
        }
    }

    // Registrar excursión
    public String registrarExcursion(String idExcursion, String descripcion, LocalDate fecha, int numeroDias, float precio) {
        try {
            Excursion excursion = new Excursion(idExcursion, descripcion, fecha, numeroDias, precio);
            sistema.registrarExcursion(excursion);
            return "Excursión registrada con éxito.";
        } catch (ExcursionYaExisteException e) {
            return "Error al registrar excursión: " + e.getMessage();
        }
    }

    // Inscribir socio en excursión
    public String inscribirSocioEnExcursion(String idSocio, String idExcursion) {
        try {
            sistema.inscribirSocioEnExcursion(idSocio, idExcursion);
            return "Inscripción realizada con éxito.";
        } catch (SocioNoEncontradoException | ExcursionNoEncontradaException e) {
            return "Error al inscribir socio: " + e.getMessage();
        }
    }

    // Modificar seguro de socio estándar
    public String modificarSeguroSocioEstandar(String idSocio, TipoSeguro nuevoSeguro) {
        try {
            sistema.modificarSeguroSocioEstandar(idSocio, nuevoSeguro);
            return "Seguro modificado con éxito.";
        } catch (SocioNoEncontradoException | TipoSocioNoValidoException e) {
            return "Error al modificar seguro: " + e.getMessage();
        }
    }

    // Eliminar socio
    public String eliminarSocio(String idSocio) {
        try {
            sistema.eliminarSocio(idSocio);
            return "Socio eliminado con éxito.";
        } catch (SocioNoEncontradoException | SocioConInscripcionesActivasException | SocioConHijosException e) {
            return "Error al eliminar socio: " + e.getMessage();
        }
    }

    // Mostrar factura mensual
    public String mostrarFacturaMensual(String idSocio) {
        return sistema.mostrarFacturaMensual(idSocio);
    }

    // Mostrar excursiones por fechas
    public List<Excursion> mostrarExcursionesPorFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return sistema.mostrarExcursionesPorFechas(fechaInicio, fechaFin);
    }

    // Eliminar inscripción
    public String eliminarInscripcion(int idInscripcion, LocalDate fechaActual) {
        try {
            sistema.eliminarInscripcion(idInscripcion, fechaActual);
            return "Inscripción eliminada con éxito.";
        } catch (InscripcionNoEncontradaException e) {
            return "Error al eliminar inscripción: " + e.getMessage();
        }
    }

    // Mostrar inscripciones filtradas
    public List<Inscripcion> mostrarInscripcionesFiltradas(String idSocio, LocalDate fechaInicio, LocalDate fechaFin) {
        return sistema.mostrarInscripcionesFiltradas(idSocio, fechaInicio, fechaFin);
    }

    // Listar socios en una excursión
    public List<Socio> listarSociosEnExcursion(String idExcursion) {
        try {
            return sistema.listarSociosEnExcursion(idExcursion);
        } catch (ExcursionNoEncontradaException e) {
            System.out.println("Error al listar socios: " + e.getMessage());
            return null;
        }
    }
    public List<Federacion> obtenerFederaciones() {
        return sistema.obtenerFederaciones();
    }

}

