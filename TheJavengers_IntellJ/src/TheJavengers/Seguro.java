package TheJavengers;

public class Seguro {
    public enum TipoSeguro {
        BÁSICO, COMPLETO
    }

    private TipoSeguro tipo;
    private double precio;

    // Constructor
    public Seguro(TipoSeguro tipo) {
        this.tipo = tipo;
       
        switch (tipo) {
            case BÁSICO:
                this.precio = 5.0; 
                break;
            case COMPLETO:
                this.precio = 10.0;
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
