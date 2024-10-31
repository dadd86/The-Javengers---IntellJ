package org.thejavengers.DAO;

import org.thejavengers.modelo.Federacion;
import java.util.*;

public interface FederacionDAO {
    void save(Federacion federacion); //Crea una federación
    Federacion findByCodigo(String codigo); //Busca una federación por código
    List<Federacion> findAll(); //Lista las federaciones
    void update(Federacion federacion); //Actualiza una federación
    void delete(String codigo); //Elimina una federación por su código
}