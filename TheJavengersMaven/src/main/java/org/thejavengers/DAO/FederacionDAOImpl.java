package org.thejavengers.DAO;

import org.thejavengers.modelo.Federacion;
import com.thejavengers.jdbc.theJDBC;
import java.util.*;
import java.sql.*;

public class FederacionDAOImpl implements FederacionDAO {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                theJDBC.url,
                theJDBC.username,
                theJDBC.password
        );
    }

    @Override
    public void save(Federacion federacion) {
        String sql = "{CALL insertarFederacion(?, ?)}"; // Procedimiento almacenado
        try (Connection conn = getConnection();
             CallableStatement callableStatement = conn.prepareCall(sql)) {
            conn.setAutoCommit(false); //Inicia transacción
            callableStatement.setInt(1, federacion.getCodigo());
            callableStatement.setString(2, federacion.getNombre());

            callableStatement.execute();
            conn.commit(); //Confirma la transacción
            System.out.print("Federación guardada: " + federacion);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.print("Error al guardar federacion. Se realizará un rollback.");
            try (Connection conn = getConnection()) {
                conn.rollback(); //Deshace la transacción si ocurre un error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public Federacion findByCodigo(int codigo) {
        String sql = "{CALL obtenerFederacionCodigo(?)}"; //Procedimiento almacenado
        try (Connection conn = getConnection();
             CallableStatement callableStatement = conn.prepareCall(sql)) {
            callableStatement.setInt(1, codigo);
            ResultSet resultSet = callableStatement.executeQuery();

            if (resultSet.next()) {
                return new Federacion(
                        resultSet.getInt("codigo"),
                        resultSet.getString("nombre")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.print("Federación no encontrada con código: " + codigo);
        return null;
    }

    @Override
    public List<Federacion> findAll() {
        List<Federacion> federaciones = new ArrayList<>();
        String sql = "{CALL encontrarTodasFederaciones()}"; //Procedimiento almacenado
        try (Connection conn = getConnection();
             CallableStatement callableStatement = conn.prepareCall(sql);
             ResultSet resultSet = callableStatement.executeQuery()) {

            while (resultSet.next()) {
                federaciones.add(new Federacion(
                        resultSet.getInt("codigo"),
                        resultSet.getString("nombre")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return federaciones;
    }

    @Override
    public void update(Federacion federacion) {
        String sql = "{CALL actualizarFederacion(?, ?)}"; //Procedimiento almacenado
        try (Connection conn = getConnection();
             CallableStatement callableStatement = conn.prepareCall(sql)) {
            conn.setAutoCommit(false); //Inicia transacción
            callableStatement.setInt(1, federacion.getCodigo());
            callableStatement.setString(2, federacion.getNombre());

            callableStatement.execute();
            conn.commit(); // Confirma transacción
            System.out.print("Federación actualizada: " + federacion);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.print("Error al actualizar la federación. Se realiza rollback.");
            try (Connection conn = getConnection()) {
                conn.rollback(); //Deshace transacción si ocurre un error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void delete(int codigo) {
        String sql = "{CALL eliminarFederacion(?)}"; //Procedimiento almacenado
        try (Connection conn = getConnection();
             CallableStatement callableStatement = conn.prepareCall(sql)) {

            conn.setAutoCommit(false); //Inicia la transacción
            callableStatement.setInt(1, codigo);

            callableStatement.execute();
            conn.commit(); //Confirma la transacción
            System.out.print("Federación eliminada con código: " + codigo);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.print("Error al eliminar la federación. Se realiza rollback.");
            try (Connection conn = getConnection()) {
                conn.rollback(); // Deshace transacción si ocurre un error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }



//    private final List<Federacion> federaciones = new ArrayList<>();
//
//    public void save(Federacion federacion) {
//        federaciones.add(federacion);
//        System.out.print("Federación guardada: " + federacion);
//    }
//
//    public Federacion findByCodigo(int codigo) {
//        return federaciones.stream()
//                .filter(f -> f.getCodigo() == codigo)
//                .findFirst()
//                .orElse(null);
//    }
//
//
//    public List<Federacion> findAll() {
//        return new ArrayList<>(federaciones);
//    }
//
//    public void update(Federacion federacion) {
//        Federacion existingFederacion = findByCodigo(federacion.getCodigo());
//        if (existingFederacion != null) {
//            existingFederacion.setNombre(federacion.getNombre());
//            System.out.print("Federación actualizada: " + existingFederacion);
//        } else {
//            System.out.print("Federación no encontrada para actualizar.");
//        }
//    }
//
//    public void delete(int codigo) {
//        federaciones.removeIf(f -> f.getCodigo() == codigo);
//        System.out.print("Federación eliminada con código: " + codigo);
//    }

}