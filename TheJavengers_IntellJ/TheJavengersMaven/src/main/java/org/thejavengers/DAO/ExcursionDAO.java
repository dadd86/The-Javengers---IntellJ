package org.thejavengers.DAO;

import org.thejavengers.modelo.Excursion;
import java.util.*;

public interface ExcursionDAO {
    void save(Excursion excursion); //Crea nueva excursión
    Excursion findById(String id); //Encuentra una excursión por su ID
    List<Excursion> findAll(); //Actualiza una excursión
    void update(Excursion excursion);
    void delete(String id);
}