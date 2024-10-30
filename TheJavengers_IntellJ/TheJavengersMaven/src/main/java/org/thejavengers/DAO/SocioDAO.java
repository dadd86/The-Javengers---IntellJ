package org.thejavengers.dao;

import org.thejavengers.modelo.Excursion;
import java.util.*;

public interface ExcursionDAO {
    void save(Socio socio);
    Socio findById(String id);
    List<Socio> findAll();
    void update(Socio socio);
    void delete(String id);
}