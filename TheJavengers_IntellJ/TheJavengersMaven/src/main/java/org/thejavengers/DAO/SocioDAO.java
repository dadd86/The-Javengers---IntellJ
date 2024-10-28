import java.util.List;
import org.thejavengers.dao.DAO;
import org.thejavengers.modelo.Socio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.thejavengers.modelo.Socio;
import org.thejavengers.modelo.SocioEstandar;
import org.thejavengers.modelo.SocioFederado;
import org.thejavengers.modelo.SocioInfantil;


/**
 * Clase SocioDAO que implementa la interfaz DAO para gestionar la persistencia de objetos Socio.
 * Proporciona métodos CRUD específicos para la clase Socio.
 */
public class SocioDAO implements DAO<Socio> {

    /**
     * Inserta un objeto {@link Socio} en la base de datos. Este método adapta los valores
     * a insertar en función del tipo de socio (estándar, federado o infantil),
     * configurando los valores correspondientes para cada tipo en la tabla de "Socios".
     *
     * <p>Los parámetros de la consulta SQL se ajustan según las propiedades específicas
     * del tipo de socio:
     * <ul>
     *     <li><b>SocioEstandar</b>: inserta el nombre y NIF, el tipo de socio como "estandar",
     *         y el seguro contratado; no se establecen valores para número de padre o madre ni para federación.</li>
     *     <li><b>SocioFederado</b>: inserta el nombre y NIF, el tipo de socio como "federado",
     *         y el código de federación; no se establecen valores para el seguro contratado ni para número de padre o madre.</li>
     *     <li><b>SocioInfantil</b>: inserta el nombre y NIF, el tipo de socio como "infantil",
     *         y el número de padre o madre; no se establecen valores para el seguro contratado ni para federación.</li>
     * </ul>
     *
     * @param socio El objeto {@link Socio} a insertar, que contiene los datos a añadir a la base de datos.
     * @return {@code true} si el socio se inserta correctamente en la base de datos, {@code false} en caso de error.
     * @throws IllegalArgumentException si el tipo de socio no está soportado.
     */
    @Override
    public boolean insert(Socio socio) {
        String sql = "INSERT INTO Socios (nombre, nif, tipo_socio, seguro_contratado, numero_padre_o_madre, federacion_codigo, cuota_mensual) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Configurar los parámetros en la consulta
            stmt.setString(1, socio.getNombre());
            stmt.setString(2, socio.getNif());

            // Determinar el tipo de socio y establecer el campo correspondiente
            if (socio instanceof SocioEstandar) {
                stmt.setString(3, "estandar");
                stmt.setString(4, ((SocioEstandar) socio).getSeguroContratado());
                stmt.setNull(5, java.sql.Types.INTEGER);
                stmt.setNull(6, java.sql.Types.VARCHAR);
            } else if (socio instanceof SocioFederado) {
                stmt.setString(3, "federado");
                stmt.setNull(4, java.sql.Types.VARCHAR);
                stmt.setNull(5, java.sql.Types.INTEGER);
                stmt.setString(6, ((SocioFederado) socio).getFederacionCodigo());
            } else if (socio instanceof SocioInfantil) {
                stmt.setString(3, "infantil");
                stmt.setNull(4, java.sql.Types.VARCHAR);
                stmt.setInt(5, ((SocioInfantil) socio).getNumeroPadreOMadre());
                stmt.setNull(6, java.sql.Types.VARCHAR);
            } else {
                throw new IllegalArgumentException("Tipo de socio no soportado.");
            }

            stmt.setFloat(7, socio.calcularCuotaMensual());


            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Busca un socio en la base de datos por su ID.
     *
     * @param id el identificador único del socio a buscar.
     * @return el objeto Socio si se encontró, o null si no existe.
     */
    @Override
    public Socio findById(String id) {
        String sql = "SELECT * FROM Socios WHERE numero_socio = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String nif = rs.getString("nif");
                    String tipoSocio = rs.getString("tipo_socio");
                    float cuotaMensual = rs.getFloat("cuota_mensual");

                    switch (tipoSocio) {
                        case "estandar":
                            String seguroContratado = rs.getString("seguro_contratado");
                            return new SocioEstandar(id, nombre, nif, cuotaMensual, seguroContratado);

                        case "federado":
                            String federacionCodigo = rs.getString("federacion_codigo");
                            return new SocioFederado(id, nombre, nif, cuotaMensual, federacionCodigo);

                        case "infantil":
                            int numeroPadreOMadre = rs.getInt("numero_padre_o_madre");
                            return new SocioInfantil(id, nombre, nif, cuotaMensual, numeroPadreOMadre);

                        default:
                            throw new IllegalArgumentException("Tipo de socio no soportado: " + tipoSocio);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Obtiene todos los socios almacenados en la base de datos.
     *
     * Este método realiza una consulta a la base de datos para recuperar todos los registros
     * de la tabla Socios y devuelve una lista de objetos Socio. Los diferentes tipos de
     * socio se instancian según el valor del campo tipo_socio en la base de datos.
     *
     * @return una lista de todos los objetos Socio. Si no hay socios, se devuelve una lista vacía.
     */

    @Override
    public List<Socio> findAll() {
        List<Socio> socios = new ArrayList<>();
        String sql = "SELECT * FROM Socios";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                String id = rs.getString("numero_socio");
                String nombre = rs.getString("nombre");
                String nif = rs.getString("nif");
                float cuotaMensual = rs.getFloat("cuota_mensual");
                String tipoSocio = rs.getString("tipo_socio");

                switch (tipoSocio) {
                    case "estandar":
                        String seguroContratado = rs.getString("seguro_contratado");
                        socios.add(new SocioEstandar(id, nombre, nif, cuotaMensual, seguroContratado));
                        break;

                    case "federado":
                        String federacionCodigo = rs.getString("federacion_codigo");
                        socios.add(new SocioFederado(id, nombre, nif, cuotaMensual, federacionCodigo));
                        break;

                    case "infantil":
                        int numeroPadreOMadre = rs.getInt("numero_padre_o_madre");
                        socios.add(new SocioInfantil(id, nombre, nif, cuotaMensual, numeroPadreOMadre));
                        break;

                    default:
                        throw new IllegalArgumentException("Tipo de socio no soportado: " + tipoSocio);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return socios;
    }


    /**
     * Actualiza la información de un socio existente en la base de datos.
     *
     * @param socio el objeto Socio con los datos actualizados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    @Override
    public boolean update(Socio socio) {
        String sql = "UPDATE Socios SET nombre = ?, nif = ?, tipo_socio = ?, " +
                "seguro_contratado = ?, numero_padre_o_madre = ?, " +
                "federacion_codigo = ?, cuota_mensual = ? WHERE numero_socio = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, socio.getNombre());
            stmt.setString(2, socio.getNif());

            switch (socio.getTipoSocio()) {
                case ESTANDAR:
                    stmt.setString(3, "estandar");
                    stmt.setString(4, ((SocioEstandar) socio).getSeguroContratado());
                    stmt.setNull(5, java.sql.Types.INTEGER);
                    stmt.setNull(6, java.sql.Types.VARCHAR);
                    break;
                case FEDERADO:
                    stmt.setString(3, "federado");
                    stmt.setNull(4, java.sql.Types.VARCHAR);
                    stmt.setNull(5, java.sql.Types.INTEGER);
                    stmt.setString(6, ((SocioFederado) socio).getFederacionCodigo());
                    break;
                case INFANTIL:
                    stmt.setString(3, "infantil");
                    stmt.setNull(4, java.sql.Types.VARCHAR);
                    stmt.setInt(5, ((SocioInfantil) socio).getNumeroPadreOMadre());
                    stmt.setNull(6, java.sql.Types.VARCHAR);
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de socio no soportado.");
            }

            stmt.setInt(7, socio.getNumeroSocio());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    /**
     * Elimina un socio de la base de datos usando su ID.
     *
     * @param id el identificador único del socio a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM Socios WHERE numero_socio = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, Integer.parseInt(id));

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
