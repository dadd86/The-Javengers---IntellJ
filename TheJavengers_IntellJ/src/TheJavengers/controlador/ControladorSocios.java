package TheJavengers.controlador;

import TheJavengers.modelo.*;
import TheJavengers.vista.*;
import TheJavengers.Excepciones.*;

import java.util.*;

/**
 * ControladorSocios gestiona las interacciones entre la vista de socios y el sistema excursionista.
 */
public class ControladorSocios {

    private SistemaExcursionista sistema;
    private VistaSocios vistaSocios;

    /**
     * Constructor que inicializa el controlador de socios con el sistema y la vista especificada.
     *
     * @param sistema el sistema excursionista que gestiona los datos
     * @param vistaSocios la vista que permite la interacción con el usuario para gestionar socios
     */
    public ControladorSocios(SistemaExcursionista sistema, VistaSocios vistaSocios) {
        this.sistema = sistema;
        this.vistaSocios = vistaSocios;
    }

    /**
     * Solicita el tipo de socio a través de la vista y llama al método correspondiente para agregarlo.
     */
    public void agregarSocio() {
        String tipoSocio = vistaSocios.pedirTexto("Introduce el tipo de socio (E=Estandar, F=Federado, I=Infantil):");

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
                vistaSocios.mostrarMensaje("Tipo de socio no valido.");
        }
    }

    /**
     * Solicita los datos de un nuevo socio estándar y lo registra en el sistema.
     * Muestra un mensaje en caso de éxito o si el socio ya existe.
     */
    public void agregarSocioEstandar() {
        String idSocio = vistaSocios.pedirTexto("Introduce ID del socio:");
        String nombre = vistaSocios.pedirTexto("Introduce el nombre del socio:");
        String apellidos = vistaSocios.pedirTexto("Introduce los apellidos del socio:");
        String nif = vistaSocios.pedirTexto("Introduce el NIF del socio:");
        String tipoSeguro = vistaSocios.pedirTexto("Introduce el tipo de seguro (B=Basico, C=Completo):");

        TipoSeguro seguro = obtenerTipoSeguro(tipoSeguro);
        if (seguro == null) return;

        SocioEstandar socio = new SocioEstandar(idSocio, nombre, apellidos, nif, seguro);

        try {
            sistema.registrarSocio(socio);
            vistaSocios.mostrarMensaje("Socio estandar agregado correctamente.");
        } catch (SocioYaExisteException e) {
            vistaSocios.mostrarMensaje(e.getMessage());
        }
    }

    /**
     * Solicita los datos de un nuevo socio federado, muestra las federaciones disponibles
     * y lo registra en el sistema. Muestra un mensaje en caso de éxito o si el socio ya existe.
     */
    private void agregarSocioFederado() {
        String idSocio = vistaSocios.pedirTexto("Introduce ID del socio:");
        String nombre = vistaSocios.pedirTexto("Introduce el nombre del socio:");
        String apellidos = vistaSocios.pedirTexto("Introduce los apellidos del socio:");
        String nif = vistaSocios.pedirTexto("Introduce el NIF del socio:");

        List<Federacion> federaciones = sistema.obtenerFederaciones();
        vistaSocios.mostrarMensaje("Selecciona una federacion de la siguiente lista:");
        for (int i = 0; i < federaciones.size(); i++) {
            vistaSocios.mostrarMensaje((i + 1) + ". " + federaciones.get(i).getNombre());
        }

        int seleccion = vistaSocios.pedirEntero("Introduce el numero de la federacion:");
        if (seleccion < 1 || seleccion > federaciones.size()) {
            vistaSocios.mostrarMensaje("Seleccion no valida.");
            return;
        }

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
     * Solicita los datos de un nuevo socio infantil y lo registra en el sistema.
     * Muestra un mensaje en caso de éxito o si el socio ya existe.
     */
    private void agregarSocioInfantil() {
        String idSocio = vistaSocios.pedirTexto("Introduce ID del socio:");
        String nombre = vistaSocios.pedirTexto("Introduce el nombre del socio:");
        String apellidos = vistaSocios.pedirTexto("Introduce los apellidos del socio:");
        String idSocioTutor = vistaSocios.pedirTexto("Introduce el ID del socio padre/madre:");

        SocioInfantil socio = new SocioInfantil(idSocio, nombre, apellidos, idSocioTutor, SocioInfantil.CUOTA_MENSUAL);

        try {
            sistema.registrarSocio(socio);
            vistaSocios.mostrarMensaje("Socio infantil agregado correctamente.");
        } catch (SocioYaExisteException e) {
            vistaSocios.mostrarMensaje(e.getMessage());
        }
    }

    /**
     * Solicita el ID de un socio y lo elimina del sistema.
     * Muestra un mensaje en caso de éxito o si ocurre algún error.
     */
    public void eliminarSocio() {
        String idSocio = vistaSocios.pedirTexto("Introduce el ID del socio a eliminar:");
        try {
            sistema.eliminarSocio(idSocio);
            vistaSocios.mostrarMensaje("Socio eliminado correctamente.");
        } catch (Exception e) {
            vistaSocios.mostrarMensaje(e.getMessage());
        }
    }

    /**
     * Modifica el tipo de seguro de un socio existente.
     */
    public void modificarSeguroSocio() {
        String idSocio = vistaSocios.pedirTexto("Introduce el ID del socio:");
        String tipoSeguro = vistaSocios.pedirTexto("Introduce el nuevo tipo de seguro (B=Basico, C=Completo):");

        TipoSeguro seguro = obtenerTipoSeguro(tipoSeguro);
        if (seguro == null) return;

        try {
            sistema.modificarSeguroSocioEstandar(idSocio, seguro);
            vistaSocios.mostrarMensaje("Tipo de seguro actualizado correctamente.");
        } catch (Exception e) {
            vistaSocios.mostrarMensaje(e.getMessage());
        }
    }

    /**
     * Muestra la factura mensual de un socio.
     */
    public void mostrarFacturaMensual() {
        String idSocio = vistaSocios.pedirTexto("Introduce el ID del socio para mostrar la factura:");
        String factura = sistema.mostrarFacturaMensual(idSocio);
        vistaSocios.mostrarMensaje(factura);
    }

    /**
     * Obtiene el tipo de seguro a partir de una entrada de texto.
     *
     * @param tipoSeguro texto introducido por el usuario
     * @return el tipo de seguro correspondiente o null si no es válido
     */
    private TipoSeguro obtenerTipoSeguro(String tipoSeguro) {
        switch (tipoSeguro.toUpperCase()) {
            case "B":
                return TipoSeguro.BASICO;
            case "C":
                return TipoSeguro.COMPLETO;
            default:
                vistaSocios.mostrarMensaje("Tipo de seguro no valido. Debe ser 'B' o 'C'.");
                return null;
        }
    }
}
