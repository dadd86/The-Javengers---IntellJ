package TheJavengers;

public class Seguro {
    private String tipo; // "Básico" o "Completo"
    private double precio; // Precio del seguro

    // Constructor
    public Seguro(String tipo) {
        this.tipo = tipo;
        // Asignar el precio según el tipo de seguro
        if ("Básico".equalsIgnoreCase(tipo)) {
            this.precio = 5.0; // Precio del seguro básico
        } else if ("Completo".equalsIgnoreCase(tipo)) {
            this.precio = 10.0; // Precio del seguro completo
        } else {
            throw new IllegalArgumentException("Tipo de seguro no válido: debe ser 'Básico' o 'Completo'.");
        }
    }

    // Método para obtener el tipo de seguro
    public String getTipo() {
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
