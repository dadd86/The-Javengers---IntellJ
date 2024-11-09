package org.thejavengers.DAO;

import org.thejavengers.modelo.*;
import com.thejavengers.jdbc.theJDBC;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.time.LocalDate;

public class InscripcionDAOImpl implements InscripcionDAO {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                theJDBC.url,
                theJDBC.username,
                theJDBC.password
        );
    }

    @Override
    public void save(Inscripcion inscripcion) {
        String sql = "{CALL insertarInscripcion(?, ?, ?)}"; //Procedimiento almacenado
        try (Connection conn = getConnection();
             CallableStatement callableStatement = conn.prepareCall(sql)) {


            conn.setAutoCommit(false); // Inicia la transacción
            callableStatement.setInt(1, inscripcion.getSocio().getIdSocio());
            callableStatement.setInt(2, inscripcion.getExcursion().getIdExcursion());
            callableStatement.setDate(3, Date.valueOf(inscripcion.getFechaInscripcion()));
            //callableStatement.setBoolean(4, true);

            callableStatement.execute();
            conn.commit(); //Confirma la transacción
            System.out.print("Inscripción guardada: " + inscripcion);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.print("Error al guardar la inscripción. Realizando rollback.");
            try (Connection conn = getConnection()) {
                conn.rollback(); //Realizamos rollback si hay un error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public Inscripcion findById(int idInscripcion) {
        String sql = "{CALL obtenerInscripcionId(?)}"; //Procedimiento almacenado
        try (Connection conn = getConnection();
             CallableStatement callableStatement = conn.prepareCall(sql)) {

            callableStatement.setInt(1, idInscripcion);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()) {
                    int idSocio = resultSet.getInt("idSocio");
                    String nombreSocio = resultSet.getString("nombreSocio");
                    String apellidosSocio = resultSet.getString("apellidosSocio");
                    String tipoSocio = resultSet.getString("tipoSocio");

                    Socio socio = null;
                    if ("estandar".equalsIgnoreCase(tipoSocio)) {
                        String nif = resultSet.getString("nif");
                        TipoSeguro seguro = TipoSeguro.valueOf(resultSet.getString("tipoSeguro"));
                        socio = new SocioEstandar(idSocio, nombreSocio, apellidosSocio, nif, seguro);
                    } else if ("federado".equalsIgnoreCase(tipoSocio)) {
                        String nif = resultSet.getString("nif");
                        Federacion federacion = new Federacion(resultSet.getInt("idFederacion"), resultSet.getString("nombreFederacion"));
                        socio = new SocioFederado(idSocio, nombreSocio, apellidosSocio, nif, federacion);
                    }

                    int idExcursion = resultSet.getInt("idExcursion");
                    Excursion excursion = new Excursion(idExcursion, resultSet.getString("descripcionExcursion"),
                            resultSet.getDate("fechaExcursion").toLocalDate(), resultSet.getInt("numeroDias"),
                            resultSet.getFloat("precioExcursion"));

                    LocalDate fechaInscripcion = resultSet.getDate("fechaInscripcion").toLocalDate();

                    return new Inscripcion(idInscripcion, socio, excursion, fechaInscripcion);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Inscripción no encontrada con ID: " + idInscripcion);
        return null;
    }

    @Override
    public List<Inscripcion> findByDateRange(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Inscripcion> inscripciones = new ArrayList<>();
        String sql = "{CALL obtenerInscripcionFechas(?, ?)}"; // Procedimiento almacenado

        try (Connection conn = getConnection();
             CallableStatement callableStatement = conn.prepareCall(sql)) {

            callableStatement.setDate(1, Date.valueOf(fechaInicio));
            callableStatement.setDate(2, Date.valueOf(fechaFin));

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Datos del socio
                    int idSocio = resultSet.getInt("idSocio");
                    String nombreSocio = resultSet.getString("nombreSocio");
                    String apellidosSocio = resultSet.getString("apellidosSocio");
                    String tipoSocio = resultSet.getString("tipoSocio");

                    Socio socio = null;
                    if ("estandar".equalsIgnoreCase(tipoSocio)) {
                        String nif = resultSet.getString("nif");
                        TipoSeguro seguro = TipoSeguro.valueOf(resultSet.getString("tipoSeguro"));
                        socio = new SocioEstandar(idSocio, nombreSocio, apellidosSocio, nif, seguro);
                    } else if ("federado".equalsIgnoreCase(tipoSocio)) {
                        String nif = resultSet.getString("nif");
                        Federacion federacion = new Federacion(
                                resultSet.getInt("idFederacion"),
                                resultSet.getString("nombreFederacion")
                        );
                        socio = new SocioFederado(idSocio, nombreSocio, apellidosSocio, nif, federacion);
                    }

                    // Datos de la excursión
                    int idExcursion = resultSet.getInt("idExcursion");
                    Excursion excursion = new Excursion(
                            idExcursion,
                            resultSet.getString("descripcionExcursion"),
                            resultSet.getDate("fechaExcursion").toLocalDate(),
                            resultSet.getInt("numeroDias"),
                            resultSet.getFloat("precioExcursion")
                    );

                    // Datos de la inscripción
                    int idInscripcion = resultSet.getInt("idInscripcion");
                    LocalDate fechaInscripcion = resultSet.getDate("fechaInscripcion").toLocalDate();

                    // Crear la inscripción y añadir a la lista
                    Inscripcion inscripcion = new Inscripcion(idInscripcion, socio, excursion, fechaInscripcion);
                    inscripciones.add(inscripcion);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inscripciones;
    }

    @Override
    public List<Inscripcion> findAll(Integer idSocio, LocalDate fechaInicio, LocalDate fechaFin) {
        List<Inscripcion> inscripciones = new ArrayList<>();
        String sql = "{CALL obtenerInscripcionesFiltradas(?, ?, ?)}"; // Procedimiento almacenado

        try (Connection conn = getConnection();
             CallableStatement callableStatement = conn.prepareCall(sql)) {

            // Configurar parámetros para el procedimiento
            if (idSocio != null) {
                callableStatement.setInt(1, idSocio);
            } else {
                callableStatement.setNull(1, Types.INTEGER);
            }

            if (fechaInicio != null) {
                callableStatement.setDate(2, Date.valueOf(fechaInicio));
            } else {
                callableStatement.setNull(2, Types.DATE);
            }

            if (fechaFin != null) {
                callableStatement.setDate(3, Date.valueOf(fechaFin));
            } else {
                callableStatement.setNull(3, Types.DATE);
            }

            // Ejecutar el procedimiento almacenado
            try (ResultSet resultSet = callableStatement.executeQuery()) {
                while (resultSet.next()) {
                    int idInscripcion = resultSet.getInt("idInscripcion");
                    int idSocioResult = resultSet.getInt("idSocio");
                    String nombreSocio = resultSet.getString("nombreSocio");
                    String apellidosSocio = resultSet.getString("apellidosSocio");

                    // Crear objeto Socio básico
                    Socio socio = new Socio(idSocioResult, nombreSocio, apellidosSocio) {
                        @Override
                        public float calcularCuotaMensual() {
                            return 0; // Lógica predeterminada
                        }

                        @Override
                        public float calcularPrecioExcursion(Excursion excursion) {
                            return excursion.getPrecio();
                        }
                    };

                    int idExcursion = resultSet.getInt("idExcursion");
                    String descripcionExcursion = resultSet.getString("descripcionExcursion");
                    int numeroDias = resultSet.getInt("numero_dias");
                    LocalDate fechaExcursion = resultSet.getDate("fechaExcursion").toLocalDate();
                    float precioExcursion = resultSet.getFloat("precio");

                    Excursion excursion = new Excursion(idExcursion, descripcionExcursion, fechaExcursion, numeroDias, precioExcursion);

                    LocalDate fechaInscripcion = resultSet.getDate("fechaInscripcion").toLocalDate();

                    inscripciones.add(new Inscripcion(idInscripcion, socio, excursion, fechaInscripcion));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener inscripciones: " + e.getMessage(), e);
        }

        return inscripciones;
    }
    @Override
    public List<Inscripcion> findAll() {
        List<Inscripcion> inscripciones = new ArrayList<>();
        String sql = "{CALL obtenerTodasInscripciones()}";

        try (Connection conn = getConnection();
             CallableStatement callableStatement = conn.prepareCall(sql);
             ResultSet resultSet = callableStatement.executeQuery()) {

            while (resultSet.next()) {
                // Extrae datos de las inscripciones aquí...
                // Mismo código que ya tienes.
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener todas las inscripciones: " + e.getMessage(), e);
        }

        return inscripciones;
    }

    @Override
    public void update(Inscripcion inscripcion) {
        String sql = "{CALL actualizarInscripcion(?, ?, ?, ?, ?, ?, ?)}"; //Procedimiento almacenado
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false); //Inicia transacción

            try (CallableStatement callableStatement = conn.prepareCall(sql)) {

                callableStatement.setInt(1, inscripcion.getIdInscripcion());
                callableStatement.setInt(2, inscripcion.getSocio().getIdSocio());
                callableStatement.setInt(3, inscripcion.getExcursion().getIdExcursion());
                callableStatement.setDate(4, Date.valueOf(inscripcion.getFechaInscripcion()));
                callableStatement.setInt(5, inscripcion.getSocio().getIdSocio());
                callableStatement.setInt(6, inscripcion.getExcursion().getIdExcursion());


                callableStatement.executeUpdate();
                conn.commit();
                System.out.print("Inscripción actualizada: " + inscripcion);
            } catch (SQLException e) {
                conn.rollback(); //Realizamos rollback si hay un error
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int idInscripcion) {
        String sql = "{CALL eliminarInscripcion(?)}"; //Procedimiento almacenado

        try (Connection conn = getConnection();
             CallableStatement callableStatement = conn.prepareCall(sql)) {

            conn.setAutoCommit(false); //Inicia transacción
            callableStatement.setInt(1, idInscripcion);
            callableStatement.execute();

            conn.commit();
            System.out.print("Inscripción eliminada con ID: " + idInscripcion);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.print("Error al eliminar la inscripción. Realizando rollback.");
            try (Connection conn = getConnection()) {
                conn.rollback(); //Realizamos rollback si hay un error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }



//    private final List<Inscripcion> inscripciones = new ArrayList<>();
//
//    public List<Inscripcion> findAll() {
//        List<Inscripcion> inscripciones = new ArrayList<>();
//        return inscripciones;
//    }
//
//    public void save(Inscripcion inscripcion) {
//        inscripciones.add(inscripcion);
//        System.out.print("Inscripción guardada: " + inscripcion);
//    }
//
//    public Inscripcion findById(int idInscripcion) {
//        return inscripciones.stream()
//                .filter(i -> i.getIdInscripcion() == idInscripcion)
//                .findFirst()
//                .orElse(null);
//    }
//
//    public void update(Inscripcion inscripcion) {
//        Inscripcion existingInscripcion = findById(inscripcion.getIdInscripcion());
//        if (existingInscripcion != null) {
//            inscripciones.remove(existingInscripcion);
//            inscripciones.add(inscripcion);
//            System.out.print("Inscripción actualizada: " + inscripcion);
//        } else {
//            System.out.print("Inscripción no encontrada para actualizar.");
//        }
//    }
//
//    public void deleteById(int idInscripcion) {
//        inscripciones.removeIf(i -> i.getIdInscripcion() == idInscripcion);
//        System.out.print("Inscripción eliminada con ID: " + idInscripcion);
//    }

}