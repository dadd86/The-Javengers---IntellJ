package TheJavengers;

public class Seguro {
    public enum TipoSeguro {
        BÁSICO, COMPLETO
    }

    private TipoSeguro tipo; // Enum para el tipo de seguro
    private double precio;   // Precio del seguro

    // Constructor
    public Seguro(TipoSeguro tipo) {
        this.tipo = tipo;
        // Asignar el precio según el tipo de seguro
        switch (tipo) {
            case BÁSICO:
                this.precio = 5.0; // Precio del seguro básico
                break;
            case COMPLETO:
                this.precio = 10.0; // Precio del seguro completo
                break;
            default:
                throw new IllegalArgumentException("Tipo de seguro no válido.");
        }
    }

    // Método para obtener el tipo de seguro
    public TipoSeguro getTipo() {
        return tipo;
    }

    // Método para obtener el precio del seguro
    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return "Tipo de seguro: " + this.tipo + "\n" +
                "Precio del seguro: " + this.precio;
    }
}
