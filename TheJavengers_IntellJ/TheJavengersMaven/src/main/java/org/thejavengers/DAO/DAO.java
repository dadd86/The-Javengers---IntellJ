package org.thejavengers.dao;

import java.util.List;

/**
 * Interfaz genérica para el patrón DAO, que define las operaciones CRUD.
 *
 * @param <T> El tipo de entidad que se gestionará.
 */
public interface DAO<T> {

    /**
     * Inserta un nuevo objeto en la base de datos.
     *
     * @param t el objeto a insertar.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
    boolean insert(T t);

    /**
     * Busca un objeto por su ID en la base de datos.
     *
     * @param id el identificador del objeto a buscar.
     * @return el objeto encontrado, o null si no se encontró.
     */
    T findById(String id);

    /**
     * Obtiene todos los objetos de la base de datos.
     *
     * @return una lista de todos los objetos.
     */
    List<T> findAll();

    /**
     * Actualiza un objeto en la base de datos.
     *
     * @param t el objeto a actualizar.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    boolean update(T t);

    /**
     * Elimina un objeto de la base de datos por su ID.
     *
     * @param id el identificador del objeto a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    boolean delete(String id);
}
