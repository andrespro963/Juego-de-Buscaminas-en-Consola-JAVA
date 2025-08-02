package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Modelo.Celda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CeldaTest {
    private Celda celda;

    @BeforeEach
    void setUp() {
        celda = new Celda();
    }

    @Test
    void testCeldaInicial() {
        assertFalse(celda.esMina());
        assertFalse(celda.estaDescubierta());
        assertFalse(celda.estaMarcada());
        assertEquals(0, celda.getMinasAlrededor());
    }

    @Test
    void testMarcarCelda() {
        celda.setMarcada(true);
        assertTrue(celda.estaMarcada());
        
        celda.setMarcada(false);
        assertFalse(celda.estaMarcada());
    }

    @Test
    void testDescubrirCelda() {
        celda.setDescubierta(true);
        assertTrue(celda.estaDescubierta());
    }

    @Test
    void testMina() {
        celda.setEsMina(true);
        assertTrue(celda.esMina());
    }

    @Test
    void testMinasAlrededor() {
        celda.setMinasAlrededor(3);
        assertEquals(3, celda.getMinasAlrededor());
    }
}