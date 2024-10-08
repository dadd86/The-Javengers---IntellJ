import java.util.Date;

public class Excursion {
    private String idExcursion;
    private String descripcion;
    private Date fechaExcursion;
    private int numeroDias;
    private float precio;

    //Constructores
    public Excursion(String idExcursion, String descripcion, Date fechaExcursion, int numeroDias, float precio) {
        this.idExcursion = idExcursion;
        this.descripcion = descripcion;
        this.fechaExcursion = fechaExcursion;
        this.numeroDias = numeroDias;
        this.precio = precio;
    }

    //Getters y setters
    public String getIdExcursion() {return idExcursion;}
    public String getDescripcion() {return descripcion;}
    public Date getFechaExcursion() {return fechaExcursion;}
    public int getNumeroDias() {return numeroDias;}
    public float getPrecio() {return precio;}

    public void setIdExcursion(String idExcursion) {this.idExcursion = idExcursion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    public void setFechaExcursion(Date fechaExcursion) {this.fechaExcursion = fechaExcursion;}
    public void setNumeroDias(int numeroDias) {this.numeroDias = numeroDias;}
    public void setPrecio(float precio) {this.precio = precio;}
}
