package TheJavengers.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Controlador<T> {
    private List<T> elementos;

    public Controlador() {
        this.elementos = new ArrayList<>();
    }

    // Metodo para agregar un elemento
    public void agregarElemento(T elemento) {
        elementos.add(elemento);
    }

    // Metodo para eliminar un elemento
    public void eliminarElemento(T elemento) {
        elementos.remove(elemento);
    }

    // Método para obtener todos los elementos
    public List<T> obtenerElementos() {
        return elementos;
    }

    // Metodo para buscar un elemento
    public T buscarElemento(T elemento) {
        int index = elementos.indexOf(elemento);
        return (index != -1) ? elementos.get(index) : null;
    }

    /**
     * Metodo para filtrar elementos en base a un criterio dado.
     *
     * @param criterio el predicado que define el filtro
     * @return una lista de elementos que cumplen con el criterio
     */
    public List<T> filtrarElementos(Predicate<T> criterio) {
        List<T> filtrados = new ArrayList<>();
        for (T elemento : elementos) {
            if (criterio.test(elemento)) {
                filtrados.add(elemento);
            }
        }
        return filtrados;
    }
}
