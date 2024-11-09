package org.thejavengers.DAO;

import org.thejavengers.modelo.Inscripcion;

import java.time.LocalDate;
import java.util.*;

public interface InscripcionDAO {
    void save(Inscripcion inscripcion); //Crea nueva inscripción
    Inscripcion findById(int idInscripcion); //Busca una inscripción por ID
    List<Inscripcion> findAll(); //Lista todas las inscripciones
    List<Inscripcion> findByDateRange(LocalDate fechaInicio, LocalDate fechaFin); // Busca inscripciones por rango de fechas
    void update(Inscripcion inscripcion); //Actualizar inscripción existente
    void deleteById(int idInscripcion); //Eliminar inscripción por ID
}