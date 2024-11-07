package org.thejavengers.DAO;

import com.thejavengers.jdbc.theJDBC;
import org.thejavengers.modelo.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SocioDAOImpl implements SocioDAO {

    private final String jdbcURL = theJDBC.url;
        private final String jdbcusername = theJDBC.username;
    private final String jdbcpassword = theJDBC.password;

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcusername, jdbcpassword);
    }

    @Override
    public void save(Socio socio) {
        String sql = "{CALL insertarSocio(?, ?, ?, ?, ?, ?, ?)}"; //Procedimiento almacenado
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false); //Inicia la conexión

            statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, socio.getNombre());
            statement.setString(2, socio.getApellidos());

            if (socio instanceof SocioEstandar) {
                statement.setString(3, "estandar");
                statement.setString(4, ((SocioEstandar) socio).getNif());
                statement.setString(5, ((SocioEstandar) socio).getSeguro().name());
                statement.setNull(6, Types.VARCHAR);
                statement.setNull(7, Types.VARCHAR);
            } else if (socio instanceof SocioFederado) {
                statement.setString(3, "federado");
                statement.setString(4, ((SocioFederado) socio).getNif());
                statement.setNull(5, Types.VARCHAR);
                statement.setInt(6, ((SocioFederado) socio).getFederacion().getCodigo());
                statement.setNull(7, Types.VARCHAR);
            } else if (socio instanceof SocioInfantil) {
                statement.setString(3, "infantil");
                statement.setNull(4, Types.VARCHAR);
                statement.setNull(5, Types.VARCHAR);
                statement.setNull(6, Types.VARCHAR);
                statement.setInt(7, ((SocioInfantil) socio).getIdSocioTutor());
            }

            statement.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); //Realizamos rollback si hay un error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); //Restauramos el autocommit después de la transacción
                    conn.close(); //Cierra la conexión
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Socio findById(int id) {
        String sql = "{CALL obtenerSocioId(?)}"; //Procedimiento almacenado
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String tipoSocio = rs.getString("tipo_socio");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");

                switch (tipoSocio) {
                    case "estandar":
                        String nif = rs.getString("nif");
                        TipoSeguro seguro = TipoSeguro.valueOf(rs.getString("seguro"));
                        return new SocioEstandar(id, nombre, apellidos, nif, seguro);
                    case "federado":
                        nif = rs.getString("nif");
                        int federacionCodigo = rs.getInt("federacion");
                        Federacion federacion = new Federacion(federacionCodigo, "");
                        return new SocioFederado(id, nombre, apellidos, nif, federacion);
                    case "infantil":
                        int idSocioTutor = rs.getInt("tutor");
                        return new SocioInfantil(id, nombre, apellidos, idSocioTutor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Socio> findAll() {
        List<Socio> socios = new ArrayList<>();
        String sql = "SELECT * FROM Socios"; //Este sigue siendo un simple SELECT
        try (Connection conn = getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("idSocio");
                String tipoSocio = resultSet.getString("tipo_socio");
                String nombre = resultSet.getString("nombre");
                String apellidos = resultSet.getString("apellidos");

                switch (tipoSocio) {
                    case "estandar":
                        String nif = resultSet.getString("nif");
                        TipoSeguro seguro = TipoSeguro.valueOf(resultSet.getString("seguro"));
                        socios.add(new SocioEstandar(id, nombre, apellidos, nif, seguro));
                        break;
                    case "federado":
                        nif = resultSet.getString("nif");
                        int federacionCodigo = resultSet.getInt("federacion");
                        Federacion federacion = new Federacion(federacionCodigo, "");
                        socios.add(new SocioFederado(id, nombre, apellidos, nif, federacion));
                        break;
                    case "infantil":
                        int idSocioTutor = resultSet.getInt("tutor");
                        socios.add(new SocioInfantil(id, nombre, apellidos, idSocioTutor));
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return socios;
    }

    @Override
    public void update(Socio socio) {
        String sql = "{CALL actualizarSocio(?, ?, ?, ?, ?, ?, ?, ?)]"; //Procedimiento almacenado
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, socio.getNombre());
            statement.setString(2, socio.getApellidos());

            if (socio instanceof SocioEstandar) {
                statement.setString(3, "estandar");
                statement.setString(4, ((SocioEstandar) socio).getNif());
                statement.setString(5, ((SocioEstandar) socio).getSeguro().name());
                statement.setNull(6, Types.VARCHAR);
                statement.setNull(7, Types.VARCHAR);
            } else if (socio instanceof SocioFederado) {
                statement.setString(3, "federado");
                statement.setString(4, ((SocioFederado) socio).getNif());
                statement.setNull(5, Types.VARCHAR);
                statement.setInt(6, ((SocioFederado) socio).getFederacion().getCodigo());
                statement.setNull(7, Types.VARCHAR);
            } else if (socio instanceof SocioInfantil) {
                statement.setString(3, "infantil");
                statement.setNull(4, Types.VARCHAR);
                statement.setNull(5, Types.VARCHAR);
                statement.setNull(6, Types.VARCHAR);
                statement.setInt(7, ((SocioInfantil) socio).getIdSocioTutor());
            }

            statement.setInt(8, socio.getIdSocio());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "{CALL eliminarSocio(?)}"; //Procedimiento almacenado
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public float obtenerFacturaMensual(int idSocio) {
        Socio socio = findById(idSocio);
        if (socio == null) {
            throw new IllegalArgumentException("Socio no encontrado con ID: " + idSocio);
        }

        // Calcula y devuelve solo la cuota mensual
        return socio.calcularCuotaMensual();
    }


    @Override
    public void modificarSeguro(int idSocio, TipoSeguro nuevoSeguro) {
        String sql = "UPDATE Socios SET seguro = ? WHERE numero_socio = ? AND tipo_socio = 'estandar'";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, nuevoSeguro.name());
            statement.setInt(2, idSocio);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
//    @Override
//    public void save(Socio socio) {
//        String sql = "INSERT INTO Socios (nombre, apellidos, tipo_socio, nif, seguro, federacion, tutor) VALUES (?, ?, ?, ?, ?, ?, ?)";
//        try (Connection conn = getConnection();
//             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            statement.setString(1, socio.getNombre());
//            statement.setString(2, socio.getApellidos());
//
//            if (socio instanceof SocioEstandar) {
//                statement.setString(3, "estandar");
//                statement.setString(4, ((SocioEstandar) socio).getNif());
//                statement.setString(5, ((SocioEstandar) socio).getSeguro().name());
//                statement.setNull(6, Types.VARCHAR);
//                statement.setNull(7, Types.VARCHAR);
//            } else if (socio instanceof SocioFederado) {
//                statement.setString(3, "federado");
//                statement.setString(4, ((SocioFederado) socio).getNif());
//                statement.setNull(5, Types.VARCHAR);
//                statement.setInt(6, ((SocioFederado) socio).getFederacion().getCodigo());
//                statement.setNull(7, Types.VARCHAR);
//            } else if (socio instanceof SocioInfantil) {
//                statement.setString(3, "infantil");
//                statement.setNull(4, Types.VARCHAR);
//                statement.setNull(5, Types.VARCHAR);
//                statement.setNull(6, Types.VARCHAR);
//                statement.setInt(7, ((SocioInfantil) socio).getIdSocioTutor());
//            }
//
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public Socio findById(int id) {
//        String sql = "SELECT * FROM Socios WHERE idSocio = ?";
//        try (Connection conn = getConnection();
//             PreparedStatement statement = conn.prepareStatement(sql)) {
//            statement.setInt(1, id);
//            ResultSet rs = statement.executeQuery();
//
//            if (rs.next()) {
//                String tipoSocio = rs.getString("tipo_socio");
//                String nombre = rs.getString("nombre");
//                String apellidos = rs.getString("apellidos");
//
//                switch (tipoSocio) {
//                    case "estandar":
//                        String nif = rs.getString("nif");
//                        TipoSeguro seguro = TipoSeguro.valueOf(rs.getString("seguro"));
//                        return new SocioEstandar(id, nombre, apellidos, nif, seguro);
//                    case "federado":
//                        nif = rs.getString("nif");
//                        int federacionCodigo = Integer.parseInt(rs.getString("federacion"));
//                        Federacion federacion = new Federacion(federacionCodigo, "");
//                        return new SocioFederado(id, nombre, apellidos, nif, federacion);
//                    case "infantil":
//                        int idSocioTutor = Integer.parseInt(rs.getString("tutor"));
//                        return new SocioInfantil(id, nombre, apellidos, idSocioTutor);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public List<Socio> findAll() {
//        List<Socio> socios = new ArrayList<>();
//        String sql = "SELECT * FROM Socios";
//        try (Connection conn = getConnection();
//             Statement statement = conn.createStatement();
//             ResultSet resultSet = statement.executeQuery(sql)) {
//            while (resultSet.next()) {
//                int id = resultSet.getInt("idSocio");
//                String tipoSocio = resultSet.getString("tipo_socio");
//                String nombre = resultSet.getString("nombre");
//                String apellidos = resultSet.getString("apellidos");
//
//                switch (tipoSocio) {
//                    case "estandar":
//                        String nif = resultSet.getString("nif");
//                        TipoSeguro seguro = TipoSeguro.valueOf(resultSet.getString("seguro"));
//                        socios.add(new SocioEstandar(id, nombre, apellidos, nif, seguro));
//                        break;
//                    case "federado":
//                        nif = resultSet.getString("nif");
//                        int federacionCodigo = Integer.parseInt(resultSet.getString("federacion"));
//                        Federacion federacion = new Federacion(federacionCodigo, "");
//                        socios.add(new SocioFederado(id, nombre, apellidos, nif, federacion));
//                        break;
//                    case "infantil":
//                        int idSocioTutor = Integer.parseInt(resultSet.getString("tutor"));
//                        socios.add(new SocioInfantil(id, nombre, apellidos, idSocioTutor));
//                        break;
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return socios;
//    }
//
//    @Override
//    public void delete(int id) {
//        String sql = "DELETE FROM Socios WHERE idSocio = ?";
//        try (Connection conn = getConnection();
//             PreparedStatement statement = conn.prepareStatement(sql)) {
//            statement.setInt(1, id);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void update(Socio socio) {
//        String sql = "UPDATE Socios SET nombre = ?, apellidos = ?, tipo_socio = ?, nif = ?, seguro = ?, federacion = ?, tutor = ? WHERE idSocio = ?";
//        try (Connection conn = getConnection();
//             PreparedStatement statement = conn.prepareStatement(sql)) {
//
//            statement.setString(1, socio.getNombre());
//            statement.setString(2, socio.getApellidos());
//
//            if (socio instanceof SocioEstandar) {
//                statement.setString(3, "estandar");
//                statement.setString(4, ((SocioEstandar) socio).getNif());
//                statement.setString(5, ((SocioEstandar) socio).getSeguro().name());
//                statement.setNull(6, Types.VARCHAR);
//                statement.setNull(7, Types.VARCHAR);
//            } else if (socio instanceof SocioFederado) {
//                statement.setString(3, "federado");
//                statement.setString(4, ((SocioFederado) socio).getNif());
//                statement.setNull(5, Types.VARCHAR);
//                statement.setInt(6, ((SocioFederado) socio).getFederacion().getCodigo());
//                statement.setNull(7, Types.VARCHAR);
//            } else if (socio instanceof SocioInfantil) {
//                statement.setString(3, "infantil");
//                statement.setNull(4, Types.VARCHAR);
//                statement.setNull(5, Types.VARCHAR);
//                statement.setNull(6, Types.VARCHAR);
//                statement.setInt(7, ((SocioInfantil) socio).getIdSocioTutor());
//            }
//
//            statement.setInt(8, socio.getIdSocio());
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public float obtenerFacturaMensual(int idSocio) {
//        Socio socio = findById(idSocio);
//        if (socio == null) {
//            throw new IllegalArgumentException("Socio no encontrado con ID: " + idSocio);
//        }
//
//        // Calcula y devuelve solo la cuota mensual
//        return socio.calcularCuotaMensual();
//    }
//
//
//    @Override
//    public void modificarSeguro(int idSocio, TipoSeguro nuevoSeguro) {
//        String sql = "UPDATE Socios SET seguro = ? WHERE numero_socio = ? AND tipo_socio = 'estandar'";
//        try (Connection conn = getConnection();
//             PreparedStatement statement = conn.prepareStatement(sql)) {
//            statement.setString(1, nuevoSeguro.name());
//            statement.setInt(2, idSocio);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}