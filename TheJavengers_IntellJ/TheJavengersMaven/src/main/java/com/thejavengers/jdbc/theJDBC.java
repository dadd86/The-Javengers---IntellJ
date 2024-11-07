package com.thejavengers.jdbc;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class theJDBC {
    /*
     * Aqui cada uno debe de poner su localhost si ha cambiado el puerto y si cambia el nombre de la SQL
     * para algunos casos hay que poner la zona horaria.
     * */
    public static final String url="jdbc:mysql://localhost:3306/thejavengers?serverTimezone=UTC";
    public static final String username="root";

    /*
     * Aqui cada uno tiene que poner el password que tiene su root o el usuario
     * */
    public static final String password = "UOC.edu1";
    public static void main(String[] args) {

        try {
            /*
             * el DriverManager.getConnection puede recibir hasta tres variables, escogemos este opcion
             * */
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error al cargar el driver JDBC", e);
        }
    }

    /**
     * Obtiene una conexión a la base de datos.
     *
     * @return una conexión activa
     * @throws SQLException si ocurre un error al conectar
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * Maneja el commit de una transacción.
     *
     * @param connection la conexión activa
     */
    public static void commitTransaction(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.commit();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al realizar commit", e);
        }
    }

    /**
     * Maneja el rollback de una transacción.
     *
     * @param connection la conexión activa
     */
    public static void rollbackTransaction(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al realizar rollback", e);
        }
    }

    /**
     * Cierra una conexión.
     *
     * @param connection la conexión activa
     */
    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al cerrar la conexión", e);
        }
    }
}
