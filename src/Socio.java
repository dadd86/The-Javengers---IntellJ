public class Socio {
    private String idSocio;
    private String nombre;
    private String apellidos;
    private float cuotaMensual;

    //Constructores
    public Socio(String idSocio, String nombre, String apellidos, float cuotaMensual) {
        this.idSocio = idSocio;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.cuotaMensual = cuotaMensual;
    }

    //Getters y setters
    public String getIdSocio() {return idSocio;}
    public String getNombre() {return nombre;}
    public String getApellidos() {return apellidos;}
    public float getCuotaMensual() {return cuotaMensual;}

    public void setIdSocio(String idSocio) {this.idSocio = idSocio;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setApellidos(String apellidos) {this.apellidos = apellidos;}
    public void setCuotaMensual(float cuotaMensual) {this.cuotaMensual = cuotaMensual;}

    //Métodos
    //public abstract float calcularCuotaMensual() {}
    //public abstract float calcularPrecioExcursion(Excursion excursion) {}
}
