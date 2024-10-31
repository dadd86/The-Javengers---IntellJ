package org.thejavengers.DAO;

import org.thejavengers.modelo.Excursion;
import org.thejavengers.modelo.Inscripcion;
import java.util.*;

public class InscripcionDAOImpl implements InscripcionDAO {
    private final List<Inscripcion> inscripciones = new ArrayList<>();

    public List<Inscripcion> findAll() {
        List<Inscripcion> inscripciones = new ArrayList<>();
        return inscripciones;
    }

    public void save(Inscripcion inscripcion) {
        inscripciones.add(inscripcion);
        System.out.print("Inscripción guardada: " + inscripcion);
    }

    public Inscripcion findById(int idInscripcion) {
        return inscripciones.stream()
                .filter(i -> i.getIdInscripcion() == idInscripcion)
                .findFirst()
                .orElse(null);
    }

    public void update(Inscripcion inscripcion) {
        Inscripcion existingInscripcion = findById(inscripcion.getIdInscripcion());
        if (existingInscripcion != null) {
            inscripciones.remove(existingInscripcion);
            inscripciones.add(inscripcion);
            System.out.print("Inscripción actualizada: " + inscripcion);
        } else {
            System.out.print("Inscripción no encontrada para actualizar.");
        }
    }

    public void deleteById(int idInscripcion) {
        inscripciones.removeIf(i -> i.getIdInscripcion() == idInscripcion);
        System.out.print("Inscripción eliminada con ID: " + idInscripcion);
    }

}