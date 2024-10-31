package com.thejavengers.jdbc;

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
    public static final String password = "Admin";
    public static void main(String[] args) {

        try {
            /*
             * el DriverManager.getConnection puede recibir hasta tres variables, escogemos este opcion
             * */
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            /*
            * Prueba de funcionamiento de la comunicacion con mysql
            *
            * */
            ResultSet resultSet = statement.executeQuery("SELECT * FROM excursiones");
            while(resultSet.next()){
                System.out.println(resultSet.getShort("codigo_excursion")+" | "+ resultSet.getShort(2));

            }
            /*
            * Siempre que se termine de ejecutar los comandos de SQL se tiene que cerrar la conexion con SQL
            * */
            connection.close();
            statement.close();
            resultSet.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
