package AppTest;

import static TheJavengers.modelo.TipoSeguro.BASICO;
import static org.junit.jupiter.api.Assertions.*;

import TheJavengers.controlador.*;
import TheJavengers.vista.*;
import TheJavengers.modelo.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MiclassTest {

    private ControladorSocios controladorSocios;
    private VistaSocios vistaSocios; // Simulando la vista
    private SistemaExcursionista sistema; // Simulando el sistema

    @BeforeEach
    void setUp() {
        vistaSocios = new VistaSocios(); // Inicializa tu vista
        sistema = new Sistema(); // Inicializa tu sistema
        controladorSocios = new ControladorSocios(sistema,vistaSocios); // Crea una instancia de tu controlador
    }

    @Test
    void testAgregarSocioEstandar() {
        // Simular entrada de datos
        String idSocio = "S001";
        String nombre = "Juan";
        String apellidos = "Pérez";
        String nif = "12345678Z";
        TipoSeguro tipoSeguro = BASICO; // Basico

        // Llamar al metodo que se quiere probar
        controladorSocios.agregarSocioEstandar(idSocio, nombre, apellidos, nif, tipoSeguro);

        // Verificar que el socio fue agregado correctamente
        SocioEstandar socio = controladorSocios.buscarSocioPorId(idSocio);
        assertNotNull(socio, "El socio debería ser agregado correctamente.");
        assertEquals(nombre, socio.getNombre(), "El nombre del socio debería coincidir.");
        assertEquals(apellidos, socio.getApellidos(), "Los apellidos del socio deberían coincidir.");
        assertEquals(nif, socio.getNif(), "El NIF del socio debería coincidir.");
        assertEquals(BASICO, socio.getSeguro(), "El tipo de seguro debería ser BASICO.");
    }

    @Test
    void testAgregarSocioEstandarYaExistente() {
        // Simular entrada de datos
        String idSocio = "S001";
        String nombre = "Juan";
        String apellidos = "Pérez";
        String nif = "12345678Z";
        String tipoSeguro = "B"; // Basico

        // Agregar el socio por primera vez
        controladorSocios.agregarSocioEstandar(idSocio, nombre, apellidos, nif, tipoSeguro);

        // Intentar agregar el mismo socio nuevamente
        Exception exception = assertThrows(SocioYaExisteException.class, () -> {
            controladorSocios.agregarSocioEstandar(idSocio, nombre, apellidos, nif, tipoSeguro);
        });

        String expectedMessage = "El socio ya existe."; // Asegúrate de que este mensaje sea el correcto
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage), "Debería lanzar una excepción porque el socio ya existe.");
    }
}
