package test;

import Modelo.Tablero;
import excepciones.CeldaMarcadaException;
import excepciones.CeldaYaDescubiertaException;
import Modelo.EstadoJuego;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

class TableroTest {
    private Tablero tablero;

    @BeforeEach
    void setUp() {
        tablero = new Tablero(10); // 10 minas
    }

    @Test
    void testInicializacionTablero() {
        assertEquals(8, tablero.getFilas());
        assertEquals(10, tablero.getColumnas());
        assertEquals(10, tablero.getMinas());
    }

    @Test
    void testColocacionMinas() {
        int minasContadas = 0;
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                if (tablero.getCeldas()[i][j].esMina()) {
                    minasContadas++;
                }
            }
        }
        assertEquals(10, minasContadas);
    }

    @Test
    void testDescubrirCelda() throws CeldaYaDescubiertaException, CeldaMarcadaException {
        // Encontrar una celda que no sea mina para descubrir
        boolean celdaNoMinaEncontrada = false;
        for (int i = 0; i < tablero.getFilas() && !celdaNoMinaEncontrada; i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                if (!tablero.getCeldas()[i][j].esMina()) {
                    assertFalse(tablero.descubrirCelda(i, j));
                    celdaNoMinaEncontrada = true;
                    break;
                }
            }
        }
    }

    @Test
    void testMarcarCelda() {
        assertDoesNotThrow(() -> tablero.marcarCelda(0, 0));
        assertTrue(tablero.getCeldas()[0][0].estaMarcada());
    }

    @Test
    void testCargarEstado() {
        EstadoJuego estado = new EstadoJuego(
            tablero.getCeldas(),
            tablero.getCeldasDescubiertas(),
            tablero.getCeldasMarcadas(),
            tablero.getMinas() - tablero.getCeldasMarcadas()
        );
        
        Tablero tableroCargado = new Tablero(estado);
        assertEquals(tablero.getCeldasDescubiertas(), tableroCargado.getCeldasDescubiertas());
        assertEquals(tablero.getCeldasMarcadas(), tableroCargado.getCeldasMarcadas());
    }
}