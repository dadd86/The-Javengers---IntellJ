import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class SistemaExcursionista {
    private List<Socio> socios;
    private List<Excursion> excursiones;
    private List<Inscripcion> inscripciones;

    public SistemaExcursionista() {
        socios = new ArrayList<>();
        excursiones = new ArrayList<>();
        inscripciones = new ArrayList<>();
    }

    // Métodos para gestionar socios
    public void registrarSocio(Socio socio) {
        socios.add(socio);
    }

    public Socio buscarSocio(int numeroSocio) {
        for (Socio socio : socios) {
            if (socio.getNumeroSocio() == numeroSocio) {
                return socio;
            }
        }
        return null;
    }

    public void listarSocios() {
        for (Socio socio : socios) {
            System.out.println(socio);
        }
    }

    // Métodos para gestionar excursiones
    public void registrarExcursion(Excursion excursion) {
        excursiones.add(excursion);
    }

    public Excursion buscarExcursion(String codigo) {
        for (Excursion excursion : excursiones) {
            if (excursion.getCodigo().equals(codigo)) {
                return excursion;
            }
        }
        return null;
    }

    public void listarExcursiones() {
        for (Excursion excursion : excursiones) {
            System.out.println(excursion);
        }
    }

    // Métodos para gestionar inscripciones
    public void inscribirSocioEnExcursion(int numeroSocio, String codigoExcursion) {
        Socio socio = buscarSocio(numeroSocio);
        Excursion excursion = buscarExcursion(codigoExcursion);
        if (socio != null && excursion != null) {
            Inscripcion inscripcion = new Inscripcion(inscripciones.size() + 1, socio, excursion);
            inscripciones.add(inscripcion);
        }
    }

    public boolean cancelarInscripcion(int numeroInscripcion, Date fechaActual) {
        for (Inscripcion inscripcion : inscripciones) {
            if (inscripcion.getNumeroInscripcion() == numeroInscripcion) {
                return inscripcion.cancelarInscripcion(fechaActual);
            }
        }
        return false;
    }

    public void listarSociosEnExcursion(String codigoExcursion) {
        for (Inscripcion inscripcion : inscripciones) {
            if (inscripcion.getExcursion().getCodigo().equals(codigoExcursion)) {
                System.out.println(inscripcion.getSocio());
            }
        }
    }

    public void listarExcursionesDeSocio(int numeroSocio) {
        for (Inscripcion inscripcion : inscripciones) {
            if (inscripcion.getSocio().getNumeroSocio() == numeroSocio) {
                System.out.println(inscripcion.getExcursion());
            }
        }
    }
}
