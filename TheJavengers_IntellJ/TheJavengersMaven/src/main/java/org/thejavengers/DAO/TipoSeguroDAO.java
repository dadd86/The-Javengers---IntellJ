package org.thejavengers.dao;

import org.thejavengers.modelo.TipoSeguro;
import java.util.*;

public interface TipoSeguroDAO {
    TipoSeguro findById(String name); //Devuelve el tipo de seguro
}