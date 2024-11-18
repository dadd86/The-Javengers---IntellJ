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
        String sql = "{CALL obtenerTipoSeguroNombre(?)}"; //Procedimiento almacenado
        TipoSeguro tipoSeguro = null;

        try (Connection conn = DriverManager.getConnection(theJDBC.url, theJDBC.username, theJDBC.password);
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, name.toUpperCase()); //Convertimos a mayúsculas, por si acaso
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                tipoSeguro = TipoSeguro.valueOf(resultSet.getString("nombre").toUpperCase()); //Aseguramos que sea en mayúsculas
            } else {
                System.out.println("Tipo de seguro no encontrado: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tipoSeguro;
    }

//    @Override
//    public TipoSeguro findById(String name) {
//        try {
//            return TipoSeguro.valueOf(name.toUpperCase()); // Usamos valueOf y convertimos a mayúsculas si es necesario
//        } catch (IllegalArgumentException e) {
//            System.out.println("Tipo de seguro no encontrado: " + name);
//            return null; // Retornamos null si e
//        }
//    }
}