package org.thejavengers.DAO;

import org.thejavengers.modelo.Federacion;
import java.util.*;

public class FederacionDAOImpl implements FederacionDAO {

    private final List<Federacion> federaciones = new ArrayList<>();

    public void save(Federacion federacion) {
        federaciones.add(federacion);
        System.out.print("Federación guardada: " + federacion);
    }

    public Federacion findByCodigo(int codigo) {
        return federaciones.stream()
                .filter(f -> f.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }


    public List<Federacion> findAll() {
        return new ArrayList<>(federaciones);
    }

    public void update(Federacion federacion) {
        Federacion existingFederacion = findByCodigo(federacion.getCodigo());
        if (existingFederacion != null) {
            existingFederacion.setNombre(federacion.getNombre());
            System.out.print("Federación actualizada: " + existingFederacion);
        } else {
            System.out.print("Federación no encontrada para actualizar.");
        }
    }

    public void delete(int codigo) {
        federaciones.removeIf(f -> f.getCodigo() == codigo);
        System.out.print("Federación eliminada con código: " + codigo);
    }

}