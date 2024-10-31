package org.thejavengers.DAO;

import org.thejavengers.modelo.TipoSeguro;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import com.thejavengers.jdbc.theJDBC;

public class TipoSeguroDAOImpl implements TipoSeguroDAO {

    @Override
    public TipoSeguro findById(String name) {
        try {
            return TipoSeguro.valueOf(name.toUpperCase()); // Usamos valueOf y convertimos a mayúsculas si es necesario
        } catch (IllegalArgumentException e) {
            System.out.println("Tipo de seguro no encontrado: " + name);
            return null; // Retornamos null si e
        }
    }
}