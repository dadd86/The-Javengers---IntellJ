package org.thejavengers.DAO;

import org.thejavengers.modelo.Socio;
import org.thejavengers.modelo.TipoSeguro;
import java.util.List;

public interface SocioDAO {
    void save(Socio socio);
    Socio findById(String id);
    List<Socio> findAll();
    void update(Socio socio);
    void delete(String id);

    // Métodos adicionales
    float obtenerFacturaMensual(String idSocio);
    void modificarSeguro(String idSocio, TipoSeguro nuevoSeguro);
}