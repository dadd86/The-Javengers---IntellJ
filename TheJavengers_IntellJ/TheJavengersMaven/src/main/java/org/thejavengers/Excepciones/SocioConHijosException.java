package org.thejavengers.Excepciones;

/**
 * Excepción lanzada cuando se intenta eliminar un socio que es tutor de un socio infantil.
 */
public class SocioConHijosException extends Exception {
    public SocioConHijosException(String mensaje) {
        super(mensaje);
    }
}