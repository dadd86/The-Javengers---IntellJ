package org.thejavengers; // Ajustado al paquete correcto

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.thejavengers.modelo.*;
import org.thejavengers.Excepciones.*; // Ajustado a minúsculas
import org.thejavengers.modelo.TipoSeguro;

import java.time.LocalDate;
/**
 * Clase de prueba para gestionar las pruebas unitarias relacionadas con la gestión de socios
 * en el sistema de excursiones utilizando JUnit 5. Esta clase cubre la creación de diferentes tipos de socios,
 * así como la verificación de sus atributos y funcionalidades asociadas, como las cuotas y precios.
 */
class GestionSociosTest {

    private SistemaExcursionista sistema;  // Instancia del sistema a ser probado
    private Federacion federacion;  // Instancia de federación utilizada en algunas pruebas

    /**
     * Configura el entorno antes de cada prueba.
     * Inicializa una nueva instancia del sistema de excursiones para garantizar que
     * cada prueba comience desde un estado limpio.
     */
    @BeforeEach
    void setUp() {
        // Inicializa SistemaExcursionista antes de cada prueba
        sistema = new SistemaExcursionista();
    }
    /**
     * Prueba que se pueda agregar correctamente un socio estándar al sistema.
     * Verifica que el socio estándar se agrega a la lista de socios del sistema.
     *
     * @throws SocioYaExisteException Si ya existe un socio con el mismo ID.
     */

    @Test
    void agregarSocioEstandar() throws SocioYaExisteException {
        // Simulación de entrada del usuario para agregar un socio estándar
        String idSocio = "S001";
        String nombre = "Juan";
        String apellidos = "Pérez González";
        String nif = "12345678A";
        TipoSeguro tipoSeguro = TipoSeguro.BASICO;

        // Creamos un socio estándar y lo agregamos al sistema
        SocioEstandar socio = new SocioEstandar(idSocio, nombre, apellidos, nif, tipoSeguro);
        sistema.registrarSocio(socio);// Registrar el socio en el sistema

        // Comprobamos que el socio se agregue correctamente
        assertTrue(sistema.obtenerSocios().contains(socio), "El socio estándar está en la lista de socios");
    }
    /**
     * Prueba que se pueda agregar correctamente un socio infantil al sistema.
     * Se asegura que el socio tutor esté registrado previamente, y luego verifica que
     * el socio infantil se asocia correctamente con el tutor y que el descuento aplicado sea el adecuado.
     *
     * @throws SocioYaExisteException Si ya existe un socio con el mismo ID.
     */

    @Test
    void agregarSocioInfantil() throws SocioYaExisteException {
        // Simulación de la creación de un socio infantil
        String idSocioTutor = "T001";
        SocioEstandar socioTutor = new SocioEstandar(idSocioTutor, "Carlos", "Gómez Blanco", "23456789B", TipoSeguro.BASICO);
        sistema.registrarSocio(socioTutor);// Registrar el socio tutor

        // Creamos un socio infantil asociado al tutor y lo agregamos al sistema
        String idSocioInfantil = "I001";
        String nombreInfantil = "Pedro";
        String apellidosInfantil = "Gómez García";
        float descuentoCuota = 0.05f; // 50% descuento

        SocioInfantil socioInfantil = new SocioInfantil(idSocioInfantil, nombreInfantil, apellidosInfantil, idSocioTutor, descuentoCuota);
        sistema.registrarSocio(socioInfantil);

        // Comprobamos que el socio infantil se haya agregado correctamente
        assertTrue(sistema.obtenerSocios().contains(socioInfantil), "El socio infantil está en la lista de socios");

        // Comprobamos que el tutor asociado es el correcto
        assertEquals(idSocioTutor, socioInfantil.getIdSocioTutor(), "El ID del tutor coincide con el del socio estándar asociado");

        // Comprobamos que el descuento aplicado sea correcto
        assertEquals(descuentoCuota, socioInfantil.getDescuentoCuota(), "El descuento en la cuota es del 50%");
    }
    /**
     * Prueba que se pueda agregar correctamente un socio federado al sistema.
     * Verifica que el socio federado está asociado a una federación válida y
     * que fue registrado correctamente en el sistema.
     *
     * @throws SocioYaExisteException Si ya existe un socio con el mismo ID.
     */

    @Test
    void agregarSocioFederado() throws SocioYaExisteException {
        // Simulación de la creación de un socio federado
        String idSocio = "F001";
        String nombre = "Luis";
        String apellidos = "Martínez Delgado";
        String nif = "87654321C";

        // Obtenemos una federación del sistema
        federacion = sistema.obtenerFederaciones().get(0);

        // Creamos un socio federado y lo agregamos al sistema
        SocioFederado socioFederado = new SocioFederado(idSocio, nombre, apellidos, nif, federacion);
        sistema.registrarSocio(socioFederado);// Registrar el socio federado

        // Comprobamos que el socio federado se ha agregado correctamente
        assertTrue(sistema.obtenerSocios().contains(socioFederado), "El socio federado está en la lista de socios");

        // Comprobamos que la federación asociada sea correcta
        assertEquals(federacion, socioFederado.getFederacion(), "La federación asociada es correcta");
    }

    /**
     * Prueba el cálculo de la cuota mensual para un socio federado.
     * Verifica que se aplique correctamente el descuento del 5% en la cuota mensual.
     *
     * @throws SocioYaExisteException Si ya existe un socio con el mismo ID.
     */

    @Test
    void cuotaSocioFederado() throws SocioYaExisteException {
        // Creamos un socio federado
        SocioFederado socioFederado = new SocioFederado("F001", "Luis", "Martínez Delgado", "87654321C", sistema.obtenerFederaciones().get(0));
        sistema.registrarSocio(socioFederado);

        // Comprobamos que la cuota mensual calculada para el socio federado es correcta
        float cuotaMensualEsperada = Socio.CUOTA_MENSUAL * (1 - 0.05f); // Descuento del 5%
        assertEquals(cuotaMensualEsperada, socioFederado.calcularCuotaMensual(), "La cuota mensual incluye el descuento del 5% para socios federados.");
    }

    /**
     * Prueba el cálculo del precio de una excursión para un socio federado.
     * Verifica que se aplique correctamente el descuento del 10% en el precio de la excursión.
     *
     * @throws SocioYaExisteException Si ya existe un socio con el mismo ID.
     * @throws ExcursionYaExisteException Si ya existe una excursión con el mismo ID.
     */

    @Test
    void precioExcursionFederado() throws SocioYaExisteException, ExcursionYaExisteException {
        // Creamos una excursión válida
        Excursion excursion = new Excursion("EXC001", "Primera excursión a la montaña de noviembre", LocalDate.of(2024, 11, 1), 3, 200.0f);
        sistema.registrarExcursion(excursion);

        // Creamos un socio federado
        SocioFederado socioFederado = new SocioFederado("F001", "Luis", "Martínez Delgado", "87654321C", sistema.obtenerFederaciones().get(0));
        sistema.registrarSocio(socioFederado);// Registrar el socio federado

        // Calculamos el precio de la excursión con el descuento del 10% aplicado
        float precioExcursionEsperado = excursion.getPrecio() * (1 - 0.1f);

        // Comprobamos que el precio calculado por el método es el correcto
        assertEquals(precioExcursionEsperado, socioFederado.calcularPrecioExcursion(excursion), "El precio de la excursión incluye el descuento del 10% para socios federados.");
    }
    /**
     * Test que valida que no se pueda agregar un socio estándar con un ID ya existente.
     *
     * Caso 1: Agregar un socio estándar correctamente.
     * Caso 2: Intentar agregar el mismo socio por segunda vez, lo cual debe fallar con la excepción esperada.
     *
     * Verifica que:
     * - Al agregar el socio por primera vez, no se lance ninguna excepción.
     * - Al intentar agregar el mismo socio nuevamente, se lance una `SocioYaExisteException`.
     * - El mensaje de la excepción sea adecuado y claro para facilitar la identificación del error.
     */

    @Test
    void testAgregarSocioEstandarYaExistente() {
        // Simular entrada de datos
        String idSocio = "S001";
        String nombre = "Juan";
        String apellidos = "Pérez";
        String nif = "12345678Z";
        String tipoSeguro = "B"; // Tipo de seguro básico

        // Caso 1: Agregar el socio por primera vez
        SocioEstandar socio = new SocioEstandar(idSocio, nombre, apellidos, nif, TipoSeguro.BASICO);
        try {
            sistema.registrarSocio(socio);
        } catch (SocioYaExisteException e) {
            // Si entra aquí, el test falla porque el socio no debería existir al agregarlo por primera vez
            fail("El socio no debería existir al agregarlo por primera vez.");
        }

        // Caso 2: Intentar agregar el mismo socio nuevamente, lo cual debería falla
        Exception exception = assertThrows(SocioYaExisteException.class, () -> {
            sistema.registrarSocio(new SocioEstandar(idSocio, nombre, apellidos, nif, TipoSeguro.BASICO));
        });

        // Verificar que el mensaje de la excepción sea correcto
        String expectedMessage = "El ID de socio ya existe.";  // Mensaje de error esperado
        String actualMessage = exception.getMessage();  // Mensaje real que devuelve el sistema
        assertTrue(actualMessage.contains(expectedMessage), "Debería lanzar una excepción porque el socio ya existe.");
    }
}

