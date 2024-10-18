package AppTest;

import static TheJavengers.modelo.TipoSeguro.BASICO;
import static org.junit.jupiter.api.Assertions.*;

import TheJavengers.controlador.*;
import TheJavengers.vista.*;
import TheJavengers.modelo.*;
import TheJavengers.Excepciones.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MiclassTest {

    private ControladorSocios controladorSocios;
    private VistaSocios vistaSocios; // Simulando la vista
    private SistemaExcursionista sistema; // Simulando el sistema

    @BeforeEach
    void setUp() {
        vistaSocios = new VistaSocios(); // Inicializa tu vista
        sistema = new SistemaExcursionista(); // Inicializa tu sistema
        controladorSocios = new ControladorSocios(sistema, vistaSocios); // Crea una instancia de tu controlador
    }

    @Test
    void testAgregarSocioEstandar() throws SocioNoEncontradoException {
        // Simular entrada de datos
        String idSocio = "S001";
        String nombre = "Juan";
        String apellidos = "Pérez";
        String nif = "12345678Z";
        String tipoSeguro = "B"; // Básico

        // Llamar al método del sistema directamente
        SocioEstandar socio = new SocioEstandar(idSocio, nombre, apellidos, nif, BASICO);
        try {
            sistema.registrarSocio(socio);
        } catch (SocioYaExisteException e) {
            fail("El socio no debería existir al agregarlo por primera vez.");
        }

        // Verificar que el socio fue agregado correctamente
        SocioEstandar socioRegistrado = (SocioEstandar) sistema.buscarSocio(idSocio);
        assertNotNull(socioRegistrado, "El socio debería ser agregado correctamente.");
        assertEquals(nombre, socioRegistrado.getNombre(), "El nombre del socio debería coincidir.");
        assertEquals(apellidos, socioRegistrado.getApellidos(), "Los apellidos del socio deberían coincidir.");
        assertEquals(nif, socioRegistrado.getNif(), "El NIF del socio debería coincidir.");
        assertEquals(BASICO, socioRegistrado.getSeguro(), "El tipo de seguro debería ser BASICO.");
    }

    @Test
    void testAgregarSocioEstandarYaExistente() {
        // Simular entrada de datos
        String idSocio = "S001";
        String nombre = "Juan";
        String apellidos = "Pérez";
        String nif = "12345678Z";
        String tipoSeguro = "B"; // Básico

        // Agregar el socio por primera vez
        SocioEstandar socio = new SocioEstandar(idSocio, nombre, apellidos, nif, BASICO);
        try {
            sistema.registrarSocio(socio);
        } catch (SocioYaExisteException e) {
            fail("El socio no debería existir al agregarlo por primera vez.");
        }

        // Intentar agregar el mismo socio nuevamente
        Exception exception = assertThrows(SocioYaExisteException.class, () -> {
            sistema.registrarSocio(new SocioEstandar(idSocio, nombre, apellidos, nif, BASICO));
        });

        String expectedMessage = "El ID de socio ya existe."; // Asegúrate de que este mensaje sea el correcto
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage), "Debería lanzar una excepción porque el socio ya existe.");
    }
}


