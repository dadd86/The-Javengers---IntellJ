package TheJavengers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import TheJavengers.modelo.*;
import TheJavengers.vista.*;

import java.time.LocalDate;

class GestionSocioTest {
    private Datos datos;
    private Federacion federacion;
    private GestionSocios gestionSocios;

  @BeforeEach
    void setUp() {
      //Inicializa la clase Datos y gestionSocios antes de cada prueba
      datos = new Datos();
      gestionSocios = new GestionSocios(datos);
  }

  @Test
    void agregarSocioEstandar() {
      //Simulación de entrada del usuario para agregar un socio estándar
      String idSocio = "S001";
      String nombre = "Juan";
      String apellidos = "Pérez González";
      String nif = "12345678A";
      TipoSeguro tipoSeguro = TipoSeguro.BASICO;

      //Creamos un socio estándar y lo agregamos a la lista de socios en Datos
      SocioEstandar socio = new SocioEstandar(idSocio, nombre, apellidos, nif, tipoSeguro);
      datos.agregarSocio(socio);

      //Comprobamos que el socio se agregue correctamente
      assertTrue(datos.getSocios().contains(socio), "El socio estándar está en la lista de socios");
  }

  @Test
    void agregarSocioInfantil() {
      //Simulación de la creación de un socio infantil
      String idSocioTutor = "T001";
      SocioEstandar socioTutor = new SocioEstandar(idSocioTutor, "Carlos", "Gómez Blanco", "23456789B", TipoSeguro.BASICO);
      datos.agregarSocio(socioTutor);

      //Creamos un socio infantil asociado al tutor y lo agregamos a la lista en Datos
      String idSocioInfantil = "I001";
      String nombreInfantil = "Pedro";
      String apellidosInfantil = "Gómez García";
      float descuentoCuota = 0.05f; //50% descuento

      SocioInfantil socioInfantil = new SocioInfantil(idSocioInfantil, nombreInfantil, apellidosInfantil, idSocioTutor, descuentoCuota);
      datos.agregarSocio(socioInfantil);

      //Comprobamos que el socio infantil se haya agregado correctamente
      assertTrue(datos.getSocios().contains(socioInfantil), "El socio infantil está en la lista de socios");

      //Comprobamos que el tutor asociado es el correcto
      assertEquals(idSocioTutor, socioInfantil.getIdSocioTutor(), "El ID del tutor coincide con el del socio estándar asociado");

      //Comprobamos que el descuento aplicado sea correcto
      assertEquals(descuentoCuota, socioInfantil.getDescuentoCuota(), "El descuento en la cuota es del 50%");
  }

  @Test
    void agregarSocioFederado() {
      //Simulación de la creación de un socio federado
      String idSocio = "F001";
      String nombre = "Luis";
      String apellidos = "Martínez Delgado";
      String nif = "87654321C";

      // Creamos una federación
      federacion = new Federacion("FED001", "Federación de Montañismo");
      datos.agregarFederacion(federacion);

      //Creamos un socio federado y lo agregamos a la lista de socios en Datos
      SocioFederado socioFederado = new SocioFederado(idSocio, nombre, apellidos, nif, federacion);
      datos.agregarSocio(socioFederado);

      //Comprobamos que el socio federado se ha agregado correctamente
      assertTrue(datos.getSocios().contains(socioFederado), "El socio federado está en la lista de socios");

      //Comprobamos que la federación asociada sea correcta
      assertEquals(federacion, socioFederado.getFederacion(), "La federación asociada es correcta");
  }

  @Test
    void cuotaSocioFederado() {
      //Creamos un socio Federado
      SocioFederado socioFederado = new SocioFederado("F001", "Luis", "Martínez Delgado", "87654321C", federacion);
      datos.agregarSocio(socioFederado);

      // Comprobamos que la cuota mensual calculada para el socio federado es correcta
      // El descuento del 5% debe aplicarse sobre la cuota mensual base
      float cuotaMensualEsperada = Socio.CUOTA_MENSUAL + (1 - 0.05f); //Descuento del 5%
      assertEquals(cuotaMensualEsperada, socioFederado.calcularCuotaMensual(), "La cuota mensual incluye el descuento del 5% para socios federados.");
  }

  @Test
    void precioExcursionFederado() {
      // Creamos una excursión válida
      Excursion excursion = new Excursion("EXC001", "Primera excursión a la montaña de noviembre", LocalDate.of(2024, 11,
              1), 3, 200.0f);
      datos.agregarExcursion(excursion);

      // Creamos de un socio federado
      SocioFederado socioFederado = new SocioFederado("F001", "Luis", "Martínez Delgado", "87654321C", federacion);
      datos.agregarSocio(socioFederado);

      // Calculamos el precio de la excursión con el descuento del 10% aplicado
      float precioExcursionEsperado = excursion.getPrecio() * (1 - 0.1f);

      // Comprobamos que el precio calculado por el metodo es el correcto
      assertEquals(precioExcursionEsperado, socioFederado.calcularPrecioExcursion(excursion), "El precio de la excursión incluye el descuento del " +
              "10% para socios federados.");
    }
}