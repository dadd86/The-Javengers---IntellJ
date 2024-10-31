package org.thejavengers.DAO;

import org.thejavengers.modelo.Inscripcion;
import java.util.*;

public interface InscripcionDAO {
    void save(Inscripcion inscripcion); //Crea nueva inscripción
    Inscripcion findById(int idInscripcion); //Busca una inscripción por ID
    List<Inscripcion> findAll(); //Lista todas las inscripciones
    void update(Inscripcion inscripcion); //Actualizar inscripción existente
    void deleteById(int idInscripcion); //Eliminar inscripción por ID
}