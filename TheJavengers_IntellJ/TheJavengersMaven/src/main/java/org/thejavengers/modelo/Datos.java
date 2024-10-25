package TheJavengers.modelo;

import java.time.LocalDate;
import java.util.*;

/**
 * Clase que gestiona los datos de socios, excursiones, federaciones e inscripciones.
 */
public class Datos {
    private ArrayList<Socio> socios;
    private ArrayList<Excursion> excursiones;
    private ArrayList<Federacion> federaciones;
    private ArrayList<Inscripcion> inscripciones;

    /**
     * Constructor para inicializar las listas de datos.
     */
    public Datos() {
        socios = new ArrayList<>();
        excursiones = new ArrayList<>();
        federaciones = new ArrayList<>();
        inscripciones = new ArrayList<>();
    }

    /**
     * Agrega un nuevo socio a la lista de socios.
     *
     * @param nuevoSocio El socio a agregar.
     * @throws IllegalArgumentException si el socio ya existe.
     */
    public void agregarSocio(Socio nuevoSocio) {
        if (!socios.contains(nuevoSocio)) {
            socios.add(nuevoSocio);
        } else {
            throw new IllegalArgumentException("El socio ya existe.");
        }
    }

    /**
     * Obtiene la lista de todos los socios.
     *
     * @return La lista de socios.
     */
    public ArrayList<Socio> getSocios() {
        return socios;
    }

    // Métodos para agregar y gestionar excursiones
    /**
     * Agrega una nueva excursión a la lista de excursiones.
     *
     * @param excursion La excursión a agregar.
     */
    public void agregarExcursion(Excursion excursion) {
        this.excursiones.add(excursion);
    }

    /**
     * Obtiene la lista de todas las excursiones.
     *
     * @return La lista de excursiones.
     */
    public ArrayList<Excursion> getExcursiones() {
        return excursiones;
    }

    // Métodos para agregar y gestionar federaciones
    /**
     * Agrega una nueva federación a la lista de federaciones.
     *
     * @param federacion La federación a agregar.
     */
    public void agregarFederacion(Federacion federacion) {
        federaciones.add(federacion);
    }

    /**
     * Obtiene la lista de todas las federaciones.
     *
     * @return La lista de federaciones.
     */
    public ArrayList<Federacion> getFederaciones() {
        return federaciones;
    }

    // Métodos para agregar y gestionar inscripciones
    /**
     * Agrega una nueva inscripción a la lista de inscripciones.
     *
     * @param inscripcion La inscripción a agregar.
     */
    public void agregarInscripcion(Inscripcion inscripcion) {
        inscripciones.add(inscripcion);
    }

    // Metodo para inscribir un socio en una excursión
    public void inscribirSocioEnExcursion(Socio socio, Excursion excursion) {
        int idInscripcion = inscripciones.size() + 1; // Asigna un ID único
        LocalDate fechaInscripcion = LocalDate.now(); // Fecha de inscripción actual
        Inscripcion inscripcion = new Inscripcion(idInscripcion, socio, excursion, fechaInscripcion);
        inscripciones.add(inscripcion);
    }

    /**
     * Obtiene la lista de todas las inscripciones.
     *
     * @return La lista de inscripciones.
     */
    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }
}
