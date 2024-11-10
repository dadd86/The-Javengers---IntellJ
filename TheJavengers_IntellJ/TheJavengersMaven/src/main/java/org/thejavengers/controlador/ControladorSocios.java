package org.thejavengers.controlador;

import org.thejavengers.modelo.SistemaExcursionista;
import org.thejavengers.vista.VistaSocios;
import org.thejavengers.modelo.SocioEstandar;
import org.thejavengers.modelo.TipoSeguro;
import org.thejavengers.modelo.Federacion;
import org.thejavengers.modelo.SocioFederado;
import org.thejavengers.modelo.SocioInfantil;
import org.thejavengers.Excepciones.SocioYaExisteException;
import org.thejavengers.Excepciones.SocioNoEncontradoException;
import org.thejavengers.Excepciones.SocioConInscripcionesActivasException;
import org.thejavengers.Excepciones.SocioConHijosException;
import org.thejavengers.Excepciones.TipoSocioNoValidoException;

import java.util.List;

/**
 * Clase que actúa como el controlador de la gestión de socios en el sistema.
 * Interactúa con la vista (VistaSocios) y el modelo (SistemaExcursionista) para realizar operaciones
 * de agregar, eliminar y modificar información de los socios.
 * Valida las entradas del usuario y maneja las excepciones correspondientes para garantizar la consistencia del sistema.
 */
public class ControladorSocios {

    // Atributos
    private final SistemaExcursionista sistema;  // Instancia del sistema que contiene la lógica de negocio
    private final VistaSocios vistaSocios;       // Instancia de la vista para interactuar con el usuario

    /**
     * Constructor del controlador de socios.
     *
     * @param sistema     El sistema que contiene la lógica de gestión de socios.
     * @param vistaSocios La vista que se encarga de recoger y mostrar la información de los socios.
     * @throws IllegalArgumentException Si alguno de los parámetros es nulo.
     */
    public ControladorSocios(SistemaExcursionista sistema, VistaSocios vistaSocios) {
        if (sistema == null || vistaSocios == null) {
            throw new IllegalArgumentException("El sistema y la vista no pueden ser nulos.");
        }
        this.sistema = sistema;
        this.vistaSocios = vistaSocios;
    }

    /**
     * Agrega un socio al sistema según el tipo seleccionado por el usuario.
     * Solicita el tipo de socio (Estándar, Federado o Infantil) y llama a los métodos específicos para crear el socio.
     */
    public void agregarSocio() {
        String tipoSocio = vistaSocios.pedirTexto("Introduce el tipo de socio (E=Estándar, F=Federado, I=Infantil):");
        switch (tipoSocio.toUpperCase()) {
            case "E":
                agregarSocioEstandar();
                break;
            case "F":
                agregarSocioFederado();
                break;
            case "I":
                agregarSocioInfantil();
                break;
            default:
                vistaSocios.mostrarMensaje("Tipo de socio no válido.");
        }
    }

    /**
     * Agrega un socio de tipo Estándar al sistema.
     * Solicita los datos necesarios del socio, incluyendo el NIF y el tipo de seguro,
     * valida las entradas y maneja la excepción si el socio ya existe.
     */
    private void agregarSocioEstandar() {
        int idSocio = 0;
        String nombre = vistaSocios.pedirTexto("Introduce el nombre del socio:");
        String apellidos = vistaSocios.pedirTexto("Introduce los apellidos del socio:");
        String nif = vistaSocios.pedirTexto("Introduce el NIF del socio:");

        // Validar el tipo de seguro (Básico o Completo)
        String tipoSeguro = vistaSocios.pedirTexto("Introduce el tipo de seguro (B=Básico, C=Completo):");
        TipoSeguro seguro;
        switch (tipoSeguro.toUpperCase()) {
            case "B":
                seguro = TipoSeguro.BASICO;
                break;
            case "C":
                seguro = TipoSeguro.COMPLETO;
                break;
            default:
                vistaSocios.mostrarMensaje("Tipo de seguro no válido. Debe ser 'B' o 'C'.");
                return;
        }

        // Crear el socio estándar
        SocioEstandar socio = new SocioEstandar(idSocio, nombre, apellidos, nif, seguro);
        try {
            sistema.registrarSocio(socio);
            vistaSocios.mostrarMensaje("Socio estándar agregado correctamente.");
        } catch (SocioYaExisteException e) {
            vistaSocios.mostrarMensaje(e.getMessage());
        }
    }

    /**
     * Agrega un socio de tipo Federado al sistema.
     * Solicita los datos del socio y permite al usuario seleccionar una federación de una lista de federaciones disponibles.
     * Maneja la excepción si el socio ya existe.
     */
    private void agregarSocioFederado() {
        int idSocio = 0;
        String nombre = vistaSocios.pedirTexto("Introduce el nombre del socio:");
        String apellidos = vistaSocios.pedirTexto("Introduce los apellidos del socio:");
        String nif = vistaSocios.pedirTexto("Introduce el NIF del socio:");

        // Mostrar y seleccionar federaciones disponibles
        List<Federacion> federaciones = sistema.obtenerFederaciones();
        vistaSocios.mostrarMensaje("Selecciona una federación de la siguiente lista:");
        for (int i = 0; i < federaciones.size(); i++) {
            vistaSocios.mostrarMensaje((i + 1) + ". " + federaciones.get(i).getNombre());
        }

        // Validar la selección del usuario
        int seleccion = vistaSocios.pedirEntero("Introduce el número de la federación:");
        if (seleccion < 1 || seleccion > federaciones.size()) {
            vistaSocios.mostrarMensaje("Selección no válida.");
            return;
        }

        // Crear el socio federado
        Federacion federacionSeleccionada = federaciones.get(seleccion - 1);
        SocioFederado socio = new SocioFederado(idSocio, nombre, apellidos, nif, federacionSeleccionada);
        try {
            sistema.registrarSocio(socio);
            vistaSocios.mostrarMensaje("Socio federado agregado correctamente.");
        } catch (SocioYaExisteException e) {
            vistaSocios.mostrarMensaje(e.getMessage());
        }
    }

    /**
     * Agrega un socio de tipo Infantil al sistema.
     * Solicita los datos del socio infantil y del tutor (ID del socio padre o madre).
     * Maneja la excepción si ya existe un socio con el mismo ID.
     */
    private void agregarSocioInfantil() {
        int idSocio = 0;
        String nombre = vistaSocios.pedirTexto("Introduce el nombre del socio:");
        String apellidos = vistaSocios.pedirTexto("Introduce los apellidos del socio:");
        int idSocioTutor = Integer.parseInt(vistaSocios.pedirTexto("Introduce el ID del socio padre/madre:"));

        // Crear el socio infantil
        SocioInfantil socio = new SocioInfantil(idSocio, nombre, apellidos, idSocioTutor);
        try {
            sistema.registrarSocio(socio);
            vistaSocios.mostrarMensaje("Socio infantil agregado correctamente.");
        } catch (SocioYaExisteException e) {
            vistaSocios.mostrarMensaje(e.getMessage());
        }
    }

    /**
     * Elimina un socio del sistema.
     * Solicita el ID del socio y gestiona la eliminación, mostrando el resultado al usuario.
     *
     * @throws SocioConInscripcionesActivasException Si el socio tiene inscripciones activas.
     * @throws SocioConHijosException Si el socio tiene hijos.
     */
    public void eliminarSocio() throws SocioConInscripcionesActivasException, SocioConHijosException {
        String idSocio = vistaSocios.pedirTexto("Introduce el ID del socio a eliminar:");
        try {
            sistema.eliminarSocio(idSocio);
            vistaSocios.mostrarMensaje("Socio eliminado correctamente.");
        } catch (SocioNoEncontradoException e) {
            vistaSocios.mostrarMensaje("Socio no encontrado.");
        }
    }

    /**
     * Modifica el seguro de un socio de tipo Estándar.
     * Solicita el nuevo tipo de seguro y maneja excepciones de validación y si el socio no es de tipo estándar.
     */
    public void modificarSeguroSocio() {
        String idSocio = vistaSocios.pedirTexto("Introduce el ID del socio:");
        String tipoSeguro = vistaSocios.pedirTexto("Introduce el nuevo tipo de seguro (B=Básico, C=Completo):");

        // Validar el tipo de seguro
        TipoSeguro seguro;
        switch (tipoSeguro.toUpperCase()) {
            case "B":
                seguro = TipoSeguro.BASICO;
                break;
            case "C":
                seguro = TipoSeguro.COMPLETO;
                break;
            default:
                vistaSocios.mostrarMensaje("Tipo de seguro no válido. Debe ser 'B' o 'C'.");
                return;
        }

        try {
            sistema.modificarSeguroSocioEstandar(Integer.parseInt(idSocio), seguro);
            vistaSocios.mostrarMensaje("Seguro del socio actualizado correctamente.");
        } catch (SocioNoEncontradoException | TipoSocioNoValidoException e) {
            vistaSocios.mostrarMensaje("Socio no encontrado.");
        }
    }

    public void mostrarFacturaMensual() {
        String idSocio = vistaSocios.pedirTexto("Introduce el ID del socio para mostrar la factura:");
        String factura = String.valueOf(sistema.mostrarFacturaMensual(idSocio));
        vistaSocios.mostrarMensaje(factura);
    }
}
