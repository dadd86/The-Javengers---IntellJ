package org.thejavengers.DAO;

import org.thejavengers.modelo.Socio;
import org.thejavengers.modelo.TipoSeguro;
import java.util.List;

public interface SocioDAO {
    void save(Socio socio);
    Socio findById(int id);
    List<Socio> findAll();
    void update(Socio socio);
    void delete(int id);

    // Métodos adicionales
    float obtenerFacturaMensual(int idSocio);
    void modificarSeguro(int idSocio, TipoSeguro nuevoSeguro);
}