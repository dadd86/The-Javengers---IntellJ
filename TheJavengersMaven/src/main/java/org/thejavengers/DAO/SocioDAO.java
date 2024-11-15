package org.thejavengers.DAO;

import org.thejavengers.modelo.Socio;
import org.thejavengers.modelo.TipoSeguro;
import java.util.List;

public interface SocioDAO {
    void save(Socio socio); // Guardar un nuevo socio
    Socio findById(int id); // Buscar socio por ID
    List<Socio> findAll(); // Obtener todos los socios
    void update(Socio socio); // Actualizar datos de un socio
    void delete(int id); // Eliminar socio por ID

    // Métodos adicionales
    float obtenerFacturaMensual(int idSocio); // Obtener la factura mensual de un socio
    void modificarSeguro(int idSocio, TipoSeguro nuevoSeguro); // Modificar el seguro de un socio

    // Buscar socios inscritos en una excursión
    List<Socio> findByExcursionId(int idExcursion);
}
