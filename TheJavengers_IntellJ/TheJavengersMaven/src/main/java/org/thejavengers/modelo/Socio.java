package org.thejavengers.modelo;

public abstract class Socio {
    private final int idSocio;
    private String nombre;
    private String apellidos;
    public static final float CUOTA_MENSUAL = 10.0f;

    public Socio(int idSocio, String nombre, String apellidos) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
        if (apellidos == null || apellidos.trim().isEmpty()) {
            throw new IllegalArgumentException("Los apellidos no pueden ser nulos o vacíos");
        }

        this.idSocio = idSocio;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public int getIdSocio() {
        return idSocio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        if (apellidos == null || apellidos.trim().isEmpty()) {
            throw new IllegalArgumentException("Los apellidos no pueden ser nulos o vacíos");
        }
        this.apellidos = apellidos;
    }

    public abstract float calcularCuotaMensual();
    public abstract float calcularPrecioExcursion(Excursion excursion);

    @Override
    public String toString() {
        return "Socio{" +
                "idSocio=" + idSocio +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Socio socio = (Socio) obj;
        return idSocio == socio.idSocio;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(idSocio);
    }
}
