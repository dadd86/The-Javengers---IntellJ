package org.thejavengers.dao;

import org.thejavengers.modelo.TipoSeguro;

public class TipoSeguroDAOImpl implements TipoSeguroDAO {
    try {
        return TipoSeguro.valueOf(name); //Usa la enumeración para recuperar el valor
    } catch (IllegalArgumentException e) {
        System.out.println("Tipo de seguro no encontrado: " + name);
        return null;
    }
}