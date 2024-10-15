package TheJavengers.controlador;

import TheJavengers.modelo.SistemaExcursionista;
import TheJavengers.vista.VistaSocios;
import TheJavengers.modelo.*;
import TheJavengers.modelo.TipoSeguro;
import TheJavengers.Excepciones.SocioYaExisteException;

import java.util.List;

public class ControladorSocios {
    private SistemaExcursionista sistema;
    private VistaSocios vistaSocios;

    public ControladorSocios(SistemaExcursionista sistema, VistaSocios vistaSocios) {
        this.sistema = sistema;
        this.vistaSocios = vistaSocios;
    }

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

    private void agregarSocioEstandar() {
        String idSocio = vistaSocios.pedirTexto("Introduce ID del socio:");
        String nombre = vistaSocios.pedirTexto("Introduce el nombre del socio:");
        String apellidos = vistaSocios.pedirTexto("Introduce los apellidos del socio:");
        String nif = vistaSocios.pedirTexto("Introduce el NIF del socio:");

        // Pedir el tipo de seguro (B o C)
        String tipoSeguro = vistaSocios.pedirTexto("Introduce el tipo de seguro (B=Básico, C=Completo):");

        // Convertir la entrada a TipoSeguro
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

        SocioEstandar socio = new SocioEstandar(idSocio, nombre, apellidos, nif, seguro);

        try {
            sistema.registrarSocio(socio);
            vistaSocios.mostrarMensaje("Socio estándar agregado correctamente.");
        } catch (SocioYaExisteException e) {
            vistaSocios.mostrarMensaje(e.getMessage());
        }
    }


    private void agregarSocioFederado() {
        String idSocio = vistaSocios.pedirTexto("Introduce ID del socio:");
        String nombre = vistaSocios.pedirTexto("Introduce el nombre del socio:");
        String apellidos = vistaSocios.pedirTexto("Introduce los apellidos del socio:");
        String nif = vistaSocios.pedirTexto("Introduce el NIF del socio:");

        // Mostrar lista de federaciones disponibles
        List<Federacion> federaciones = sistema.obtenerFederaciones();
        vistaSocios.mostrarMensaje("Selecciona una federación de la siguiente lista:");

        for (int i = 0; i < federaciones.size(); i++) {
            vistaSocios.mostrarMensaje((i + 1) + ". " + federaciones.get(i).getNombre());
        }

        // Pedir al usuario que seleccione una federación por número
        int seleccion = vistaSocios.pedirEntero("Introduce el número de la federación:");

        // Validar la selección del usuario
        if (seleccion < 1 || seleccion > federaciones.size()) {
            vistaSocios.mostrarMensaje("Selección no válida.");
            return;
        }

        // Obtener la federación seleccionada
        Federacion federacionSeleccionada = federaciones.get(seleccion - 1);

        // Crear el socio federado
        SocioFederado socio = new SocioFederado(idSocio, nombre, apellidos, nif, federacionSeleccionada);

        try {
            sistema.registrarSocio(socio);
            vistaSocios.mostrarMensaje("Socio federado agregado correctamente.");
        } catch (SocioYaExisteException e) {
            vistaSocios.mostrarMensaje(e.getMessage());
        }
    }


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


    public void eliminarSocio() {
        String idSocio = vistaSocios.pedirTexto("Introduce el ID del socio a eliminar:");
        try {
            sistema.eliminarSocio(idSocio);
            vistaSocios.mostrarMensaje("Socio eliminado correctamente.");
        } catch (Exception e) {
            vistaSocios.mostrarMensaje(e.getMessage());
        }
    }

    public void modificarSeguroSocio() {
        String idSocio = vistaSocios.pedirTexto("Introduce el ID del socio:");
        String tipoSeguro = vistaSocios.pedirTexto("Introduce el nuevo tipo de seguro (B=Básico, C=Completo):");

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


    }

    public void mostrarFacturaMensual() {
        String idSocio = vistaSocios.pedirTexto("Introduce el ID del socio para mostrar la factura:");
        String factura = sistema.mostrarFacturaMensual(idSocio);
        vistaSocios.mostrarMensaje(factura);
    }

}
