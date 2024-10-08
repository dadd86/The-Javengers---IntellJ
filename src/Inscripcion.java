import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Inscripcion {

    private int idInscripcion;
    private List<Socio> socio; //Relación de agregación
    private List<Excursion> excursion; //Relación agregación
    private LocalDate fechaInscripcion;

    //Constructores
    public Inscripcion(int idInscripcion, List<Socio> socio, List<Excursion> excursion, LocalDate fechaInscripcion) {
        this.idInscripcion = idInscripcion;
        this.socio = socio;
        this.excursion = excursion;
        this.fechaInscripcion = fechaInscripcion;
    }

    //Getters y setters
    public int getIdInscripcion() {return idInscripcion;}
    public List<Socio> getSocio() {return socio;}
    public List<Excursion> getExcursion() {return excursion;}
    public LocalDate getFechaInscripcion() {return fechaInscripcion;}

    public void setIdInscripcion(int idInscripcion) {this.idInscripcion = idInscripcion;}
    public void setSocio(List<Socio> socio) {this.socio = socio;}
    public void setExcursion(List<Excursion> excursion) {this.excursion = excursion;}
    public void setFechaInscripcion(LocalDate fechaInscripcion) {this.fechaInscripcion = fechaInscripcion;}

    //Métodos
    public float calcularPrecioInscripcion() {
        //Suma el precio de todas las excursiones para todos los socios
        float precioInscripcion = 0;
        for (Excursion excursion : excursion) {
            for (Socio socio : socio) {
                precioInscripcion += socio.calcularPrecioExcursion(excursion);
            }
        }
        return precioInscripcion;
    }

    //REVISAR PARA QUE LO HAGA CON LA FECHA DE EXCURSION COMO LIMITE
    public boolean cancelarInscripcion(Date fechaExcursion, LocalDate fechaLimite) {
        //Cancelar solo si la fecha de excursión es antes de la límite
        return fechaExcursion.before(java.sql.Date.valueOf(fechaLimite));
    }
}
