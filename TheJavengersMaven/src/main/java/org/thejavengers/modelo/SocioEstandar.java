package org.thejavengers.modelo;
import jakarta.persistence.*;
@Entity
@DiscriminatorValue("estandar") // Valor discriminante para identificar este tipo de socio

public class SocioEstandar extends Socio {
    @Column(name = "nif", nullable = false, unique = true)
    private String nif;
    // Relación muchos-a-uno: muchos socios estándar pueden estar asociados con un tipo de seguro.
// La estrategia de carga EAGER asegura que los datos del tipo de seguro se carguen automáticamente junto con el socio.
    @Enumerated(EnumType.STRING) // Usa el nombre del enum como valor en la base de datos
    @Column(name = "seguro", nullable = false)
    private TipoSeguro seguro;

    public SocioEstandar(int idSocio, String nombre, String apellidos, String nif, TipoSeguro seguro) {
        super(idSocio, nombre, apellidos);
        this.nif = nif;
        this.seguro = seguro;
    }
    // Constructor sin argumentos necesario para JPA
    public SocioEstandar() {
    }
    public String getNif() {
        return nif;
    }

    public TipoSeguro getSeguro() {
        return seguro;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public void setSeguro(TipoSeguro seguro) {
        this.seguro = seguro;
    }

    @Override
    public float calcularCuotaMensual() {
        return CUOTA_MENSUAL + seguro.getCostoSeguro();
    }

    @Override
    public float calcularPrecioExcursion(Excursion excursion) {
        return excursion.getPrecio() + seguro.getCostoSeguro();
    }

    @Override
    public String toString() {
        return "Socio Estandar: " + super.toString() +
                ", Tipo de Seguro: " + seguro +
                ", Costo del Seguro: " + seguro.getCostoSeguro();
    }
}
