package org.thejavengers.dao;

import org.thejavengers.modelo.Excursion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExcursionDAOImpl implements ExcursionDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/excursion";
    private final String jdbcusername = "root";
    private final String jdbcpassword = "Admin";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcusername, jdbcpassword);
    }

    public void save(Excursion excursion) {
        String sql = "INSERT INTO Excursion (idExcursion, descripcion, fechaExcursion, numeroDias, precio) VALUES (?,?, ?, ?, ?)";
        try (Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, excursion.getIdExcursion());
            statement.setString(2, excursion.getDescripcion());
            statement.getDate(3, Date.valueOf(excursion.getFechaExcursion()));
            statement.setInt(4, excursion.getNumeroDias());
            statement.setFloat(5, excursion.getPrecio());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Excursion findById(String id) {
        String sql = "SELECT * FROM Excursion WHERE idExcursion = ?";
        try (Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Excursion (
                        resultSet.getString("idExcursion"),
                        resultSet.getString("descripcion"),
                        resultSet.getDate("fechaExcursion").toLocalDate(),
                        resultSet.getInt("numeroDias"),
                        resultSet.getFloat("precio")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Excursion> findAll() {
        List<Excursion> excursiones = new ArrayList<>();
        String sql = "SELECT * FROM Excursion";
        try (Connection conn = getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                excursiones.add(new Excursion(
                        resultSet.getString("idExcursion"),
                        resultSet.getString("descripcion"),
                        resultSet.getDate("fechaExcursion").toLocalDate(),
                        resultSet.getInt("numeroDias"),
                        resultSet.getFloat("precio")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return excursiones;
    }

    public void update(Excursion excursion) {
        String sql = "UPDATE Excursion SET descripcion = ?, fechaExcursion = ?, numeroDias = ?, precio = ? WHERE idExcursion = ?";
        try (Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, excursion.getDescripcion());
            statement.setDate(2, Date.valueOf(excursion.getFechaExcursion()));
            statement.setInt(3, excursion.getNumeroDias());
            statement.setFloat(4, excursion.getPrecio());
            statement.setString(5, excursion.getIdExcursion());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM Excursion WHERE idExcursion = ?";
        try (Connection conn = getConnection();
            Prepared Statement statement = conn.prepareStatement(sql)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}