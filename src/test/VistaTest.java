package test;

import Vista.Vista;
import excepciones.AccionInvalidaException;
import excepciones.CoordenadaInvalidaException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

class VistaTest {
    private Vista vista;

    @Test
    void testMostrarMensaje() {
        vista = new Vista();
        assertDoesNotThrow(() -> vista.mostrarMensaje("Mensaje de prueba"));
    }

    @Test
    void testObtenerAccion() throws AccionInvalidaException {
        String input = "D\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        vista = new Vista();
        assertEquals("D", vista.obtenerAccion());
    }

    @Test
    void testObtenerCoordenadas() throws CoordenadaInvalidaException {
        String input = "A5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        vista = new Vista();
        int[] coordenadas = vista.obtenerCoordenadas();
        assertArrayEquals(new int[]{0, 4}, coordenadas);
    }
}