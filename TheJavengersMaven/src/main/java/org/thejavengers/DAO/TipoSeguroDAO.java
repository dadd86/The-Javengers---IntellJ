package org.thejavengers.DAO;

import org.thejavengers.modelo.TipoSeguro;

public interface TipoSeguroDAO {
    TipoSeguro findById(String name); // Devuelve el TipoSeguro correspondiente
}