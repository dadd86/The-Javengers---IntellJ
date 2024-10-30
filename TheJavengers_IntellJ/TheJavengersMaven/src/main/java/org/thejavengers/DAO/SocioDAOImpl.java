package org.thejavengers.dao;

import org.thejavengers.modelo.Socio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SocioDAOImpl implements SocioDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/genteFit"; // Cambia el nombre de la base de datos según tu configuración
    private final String jdbcusername = "root";
    private final String jdbcpassword = "Admin";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcusername, jdbcpassword);
    }

    public void save(Socio socio) {
        String sql = "INSERT INTO Socios (nombre, nif, tipo_socio, seguro_contratado, numero_padre_o_madre, federacion_codigo, cuota_mensual) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, socio.getNombre());
            statement.setString(2, socio.getNIF());
            statement.setString(3, socio.getTipoSocio().name());
            statement.setString(4, socio.getSeguroContratado() != null ? socio.getSeguroContratado().name() : null);
            statement.setObject(5, socio.getNumeroPadreOMadre() != null ? socio.getNumeroPadreOMadre() : null);
            statement.setString(6, socio.getFederacionCodigo());
            statement.setBigDecimal(7, socio.getCuotaMensual());
            statement.executeUpdate();

            // Obtener el ID generado
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    socio.setNumeroSocio(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Socio findById(int id) {
        String sql = "SELECT * FROM Socios WHERE numero_socio = ?";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Socio(
                        resultSet.getInt("numero_socio"),
                        resultSet.getString("nombre"),
                        resultSet.getString("nif"),
                        Socio.TipoSocio.valueOf(resultSet.getString("tipo_socio")),
                        resultSet.getString("seguro_contratado") != null ? Socio.SeguroContratado.valueOf(resultSet.getString("seguro_contratado")) : null,
                        resultSet.getObject("numero_padre_o_madre", Integer.class),
                        resultSet.getString("federacion_codigo"),
                        resultSet.getBigDecimal("cuota_mensual")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Socio> findAll() {
        List<Socio> socios = new ArrayList<>();
        String sql = "SELECT * FROM Socios";
        try (Connection conn = getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                socios.add(new Socio(
                        resultSet.getInt("numero_socio"),
                        resultSet.getString("nombre"),
                        resultSet.getString("nif"),
                        Socio.TipoSocio.valueOf(resultSet.getString("tipo_socio")),
                        resultSet.getString("seguro_contratado") != null ? Socio.SeguroContratado.valueOf(resultSet.getString("seguro_contratado")) : null,
                        resultSet.getObject("numero_padre_o_madre", Integer.class),
                        resultSet.getString("federacion_codigo"),
                        resultSet.getBigDecimal("cuota_mensual")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return socios;
    }

    public void update(Socio socio) {
        String sql = "UPDATE Socios SET nombre = ?, nif = ?, tipo_socio = ?, seguro_contratado = ?, numero_padre_o_madre = ?, federacion_codigo = ?, cuota_mensual = ? WHERE numero_socio = ?";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, socio.getNombre());
            statement.setString(2, socio.getNIF());
            statement.setString(3, socio.getTipoSocio().name());
            statement.setString(4, socio.getSeguroContratado() != null ? socio.getSeguroContratado().name() : null);
            statement.setObject(5, socio.getNumeroPadreOMadre() != null ? socio.getNumeroPadreOMadre() : null);
            statement.setString(6, socio.getFederacionCodigo());
            statement.setBigDecimal(7, socio.getCuotaMensual());
            statement.setInt(8, socio.getNumeroSocio());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM Socios WHERE numero_socio = ?";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
