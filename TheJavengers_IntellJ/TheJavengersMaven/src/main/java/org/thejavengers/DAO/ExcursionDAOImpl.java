package org.thejavengers.DAO;

import org.thejavengers.modelo.Excursion;
import com.thejavengers.jdbc.theJDBC;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExcursionDAOImpl implements ExcursionDAO {

    @Override
    public void save(Excursion excursion) {
        String sql = "{CALL insertarExcursion(?, ?, ?, ?, ?)}";
        Connection conn = null;
        try {
            conn = theJDBC.getConnection();
            conn.setAutoCommit(false); //Inicia la transacción

            try (CallableStatement callableStatement = conn.prepareCall(sql)) {
                callableStatement.setInt(1, excursion.getIdExcursion());
                callableStatement.setString(2, excursion.getDescripcion());
                callableStatement.setDate(3, Date.valueOf(excursion.getFechaExcursion()));
                callableStatement.setInt(4, excursion.getNumero_dias());
                callableStatement.setFloat(5, excursion.getPrecio());
                callableStatement.execute();
            }

            theJDBC.commitTransaction(conn); //Confirma la transacción
        } catch (SQLException e) {
            theJDBC.rollbackTransaction(conn); //Deshace la transacción en caso de error
            e.printStackTrace();
        } finally {
            theJDBC.closeConnection(conn); //Cierra la conexión
        }
    }

    @Override
    public Excursion findById(String id) {
        String sql = "{CALL obtenerExcursionId(?)}";
        try (Connection conn = theJDBC.getConnection();
             CallableStatement callableStatement = conn.prepareCall(sql)) {
            callableStatement.setString(1, id);
            try (ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Excursion(
                            resultSet.getInt("idExcursion"),
                            resultSet.getString("descripcion"),
                            resultSet.getDate("fechaExcursion").toLocalDate(),
                            resultSet.getInt("numero_dias"),
                            resultSet.getFloat("precio")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Excursion> findAll() {
        List<Excursion> excursiones = new ArrayList<>();
        String sql = "{CALL encontrarTodasExcursiones()}";

        try (Connection conn = theJDBC.getConnection();
             CallableStatement callableStatement = conn.prepareCall(sql);
             ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()) {
                excursiones.add(new Excursion(
                        resultSet.getInt("idExcursion"),
                        resultSet.getString("descripcion"),
                        resultSet.getDate("fechaExcursion").toLocalDate(),
                        resultSet.getInt("numero_dias"),
                        resultSet.getFloat("precio")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return excursiones;
    }

    @Override
    public void update(Excursion excursion) {
        String sql = "{CALL actualizarExcursion(?, ?, ?, ?, ?)}";
        Connection conn = null;
        try{
            conn = theJDBC.getConnection();
            conn.setAutoCommit(false); //Inicia la transacción
            try (CallableStatement callableStatement = conn.prepareCall(sql)) {
                callableStatement.setInt(1, excursion.getIdExcursion());
                callableStatement.setString(2, excursion.getDescripcion());
                callableStatement.setDate(3, Date.valueOf(excursion.getFechaExcursion()));
                callableStatement.setInt(4, excursion.getNumero_dias());
                callableStatement.setFloat(5, excursion.getPrecio());
                callableStatement.execute();
            }

            theJDBC.commitTransaction(conn); //Confirma la transacción
        } catch (SQLException e) {
            theJDBC.rollbackTransaction(conn); // Deshace la transacción en caso de error
            e.printStackTrace();
        } finally {
            theJDBC.closeConnection(conn); //Cierra la conexión
        }
    }

    @Override
    public void delete(String id) {
        String sql = "{CALL eliminarExcursion(?)}";
        Connection conn = null;
        try {
            conn = theJDBC.getConnection();
            conn.setAutoCommit(false); //Inicia la transacción

            try (CallableStatement callableStatement = conn.prepareCall(sql)) {
                callableStatement.setString(1, id);
                callableStatement.execute();
            }

            theJDBC.commitTransaction(conn); //Confirma la transacción
        } catch (SQLException e) {
            theJDBC.rollbackTransaction(conn); // Deshace la transacción en caso de error
            e.printStackTrace();
        } finally {
            theJDBC.closeConnection(conn); //Cierra la conexión
        }
    }



//    private final String jdbcURL = theJDBC.url;
//    private final String jdbcusername = theJDBC.username;
//    private final String jdbcpassword = theJDBC.password;
//
//    private Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(jdbcURL, jdbcusername, jdbcpassword);
//    }
//
//    public void save(Excursion excursion) {
//        String sql = "INSERT INTO excursiones (idExcursion, descripcion, fechaExcursion, numero_dias, precio) VALUES (?,?, ?, ?, ?)";
//        try (Connection conn = getConnection();
//            PreparedStatement statement = conn.prepareStatement(sql)) {
//            statement.setInt(1, excursion.getIdExcursion());
//            statement.setString(2, excursion.getDescripcion());
//            statement.setDate(3, Date.valueOf(excursion.getFechaExcursion()));
//            statement.setInt(4, excursion.getNumero_dias());
//            statement.setFloat(5, excursion.getPrecio());
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public Excursion findById(String id) {
//        String sql = "SELECT * FROM excursiones WHERE idExcursion = ?";
//        try (Connection conn = getConnection();
//            PreparedStatement statement = conn.prepareStatement(sql)) {
//            statement.setString(1, id);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                return new Excursion (
//                        resultSet.getInt("idExcursion"),
//                        resultSet.getString("descripcion"),
//                        resultSet.getDate("fechaExcursion").toLocalDate(),
//                        resultSet.getInt("numero_dias"),
//                        resultSet.getFloat("precio")
//                );
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public List<Excursion> findAll() {
//        List<Excursion> excursiones = new ArrayList<>();
//        String sql = "SELECT * FROM excursiones";
//        try (Connection conn = getConnection();
//            Statement statement = conn.createStatement();
//            ResultSet resultSet = statement.executeQuery(sql)) {
//            while (resultSet.next()) {
//                excursiones.add(new Excursion(
//                        resultSet.getInt("idExcursion"),
//                        resultSet.getString("descripcion"),
//                        resultSet.getDate("fechaExcursion").toLocalDate(),
//                        resultSet.getInt("numero_dias"),
//                        resultSet.getFloat("precio")
//                ));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return excursiones;
//    }
//
//    public void update(Excursion excursion) {
//        String sql = "UPDATE excursiones SET descripcion = ?, fechaExcursion = ?, numeroDias = ?, precio = ? WHERE idExcursion = ?";
//        try (Connection conn = getConnection();
//            PreparedStatement statement = conn.prepareStatement(sql)) {
//            statement.setString(1, excursion.getDescripcion());
//            statement.setDate(2, Date.valueOf(excursion.getFechaExcursion()));
//            statement.setInt(3, excursion.getNumero_dias());
//            statement.setFloat(4, excursion.getPrecio());
//            statement.setInt(5, excursion.getIdExcursion());
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void delete(String id) {
//        String sql = "DELETE FROM excursiones WHERE idExcursion = ?";
//        try (Connection conn = getConnection();
//            PreparedStatement statement = conn.prepareStatement(sql)) {
//                statement.setString(1, id);
//                statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}