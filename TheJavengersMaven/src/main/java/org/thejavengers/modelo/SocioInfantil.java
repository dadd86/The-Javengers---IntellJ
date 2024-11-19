package org.thejavengers.modelo;

import jakarta.persistence.*;
@Entity
@DiscriminatorValue("infantil") // Valor para distinguir este tipo en la tabla "Socio"
/**
 * Clase que representa a un socio infantil.
 * Hereda de la clase Socio y define un descuento en la cuota mensual para este tipo de socio.
 */
public class SocioInfantil extends Socio {
    @Column(name = "tutor", nullable = false)
    private int idSocioTutor;
    @Transient
    private static final float DESCUENTO_INFANTIL = 0.5f; // 50% de descuento

    /**
     * Constructor para inicializar un objeto de tipo SocioInfantil.
     *
     * @param idSocio      Identificador único del socio.
     * @param nombre       Nombre del socio.
     * @param apellidos    Apellidos del socio.
     * @param idSocioTutor Identificador único del socio estándar tutor del infantil.
     */
    public SocioInfantil(int idSocio, String nombre, String apellidos, int idSocioTutor) {
        super(idSocio, nombre, apellidos);
        this.idSocioTutor = idSocioTutor;
    }
    // Constructor sin argumentos necesario para JPA
    public SocioInfantil() {
    }
    /**
     * Obtiene el identificador único del socio estándar responsable del infantil.
     *
     * @return El identificador del socio estándar.
     */
    public int getIdSocioTutor() {
        return idSocioTutor;
    }

    /**
     * Establece el identificador del socio tutor.
     *
     * @param idSocioTutor El identificador del socio tutor.
     */
    public void setIdSocioTutor(int idSocioTutor) {
        this.idSocioTutor = idSocioTutor;
    }

    /**
     * Calcula la cuota mensual del socio infantil.
     * En este caso, la cuota mensual es la cuota fija con un descuento.
     *
     * @return La cuota mensual del socio infantil.
     */
    @Override
    public float calcularCuotaMensual() {
        return CUOTA_MENSUAL * (1 - DESCUENTO_INFANTIL); // Aplica el descuento del 50%
    }

    @Override
    public float calcularPrecioExcursion(Excursion excursion) {
        return excursion.getPrecio(); // No tiene descuento en excursiones
    }

    @Override
    public String toString() {
        return "Socio Infantil: " + super.toString() +
                ", Tutor ID: " + idSocioTutor;
    }

}
