package Modelo;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import excepciones.CeldaMarcadaException;
import excepciones.CeldaYaDescubiertaException;

public class Tablero {
    private final int TAMANO_FILAS = 8; // A-H
    private final int TAMANO_COLUMNAS = 10; // 1-10
    private final int MINAS;
    private Celda[][] celdas;
    private int celdasDescubiertas;
    private int celdasMarcadas;
    
    public Tablero(int minas) throws IllegalArgumentException {
        if (minas <= 0 || minas >= TAMANO_FILAS * TAMANO_COLUMNAS) {
            throw new IllegalArgumentException("Número de minas inválido. Debe estar entre 1 y " + (TAMANO_FILAS * TAMANO_COLUMNAS - 1));
        }
        this.MINAS = minas;
        this.celdas = new Celda[TAMANO_FILAS][TAMANO_COLUMNAS];
        this.celdasDescubiertas = 0;
        this.celdasMarcadas = 0;
        inicializarTablero();
        colocarMinas();
        calcularMinasAlrededor();
    }

    private void inicializarTablero() {
        for (int i = 0; i < TAMANO_FILAS; i++) {
            for (int j = 0; j < TAMANO_COLUMNAS; j++) {
                celdas[i][j] = new Celda();
            }
        }
    }

    private void colocarMinas() {
        Random rand = new Random();
        int minasColocadas = 0;
        int totalCeldas = TAMANO_FILAS * TAMANO_COLUMNAS;

        while (minasColocadas < MINAS) {
            int pos = rand.nextInt(totalCeldas);
            int x = pos / TAMANO_COLUMNAS;
            int y = pos % TAMANO_COLUMNAS;

            if (!celdas[x][y].esMina()) {
                celdas[x][y].setEsMina(true);
                minasColocadas++;
            }
        }
    }

    private void calcularMinasAlrededor() {
        for (int i = 0; i < TAMANO_FILAS; i++) {
            for (int j = 0; j < TAMANO_COLUMNAS; j++) {
                if (!celdas[i][j].esMina()) {
                    celdas[i][j].setMinasAlrededor(contarMinasAlrededor(i, j));
                }
            }
        }
    }

    private int contarMinasAlrededor(int x, int y) {
        int count = 0;
        int inicioX = Math.max(0, x - 1);
        int finX = Math.min(TAMANO_FILAS - 1, x + 1);
        int inicioY = Math.max(0, y - 1);
        int finY = Math.min(TAMANO_COLUMNAS - 1, y + 1);

        for (int i = inicioX; i <= finX; i++) {
            for (int j = inicioY; j <= finY; j++) {
                if (celdas[i][j].esMina()) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean descubrirCelda(int x, int y) throws CeldaYaDescubiertaException, CeldaMarcadaException {
        validarCoordenadas(x, y);
        Celda celda = celdas[x][y];

        if (celda.estaDescubierta()) {
            throw new CeldaYaDescubiertaException("La celda " + convertirCoordenadas(x, y) + " ya está descubierta");
        }
        if (celda.estaMarcada()) {
            throw new CeldaMarcadaException("La celda " + convertirCoordenadas(x, y) + " está marcada");
        }

        celda.setDescubierta(true);
        celdasDescubiertas++;

        if (celda.esMina()) {
            return true; // Jugador pierde
        }

        if (celda.getMinasAlrededor() == 0) {
            descubrirAreaVacia(x, y);
        }

        return false;
    }

    private void descubrirAreaVacia(int x, int y) {
        Queue<int[]> cola = new LinkedList<>();
        cola.add(new int[]{x, y});

        while (!cola.isEmpty()) {
            int[] actual = cola.poll();
            int i = actual[0];
            int j = actual[1];

            // Explorar las 8 celdas adyacentes
            for (int di = -1; di <= 1; di++) {
                for (int dj = -1; dj <= 1; dj++) {
                    if (di == 0 && dj == 0) continue; // Saltar la celda actual
                    
                    int ni = i + di;
                    int nj = j + dj;
                    
                    if (esCoordenadaValida(ni, nj)) {
                        Celda vecina = celdas[ni][nj];
                        
                        if (!vecina.estaDescubierta() && !vecina.estaMarcada()) {
                            vecina.setDescubierta(true);
                            celdasDescubiertas++;
                            
                            if (vecina.getMinasAlrededor() == 0) {
                                cola.add(new int[]{ni, nj});
                            }
                        }
                    }
                }
            }
        }
    }

    public void marcarCelda(int x, int y) throws CeldaYaDescubiertaException {
        validarCoordenadas(x, y);
        Celda celda = celdas[x][y];

        if (celda.estaDescubierta()) {
            throw new CeldaYaDescubiertaException("No se puede marcar celda descubierta " + convertirCoordenadas(x, y));
        }

        if (celda.estaMarcada()) {
            celda.setMarcada(false);
            celdasMarcadas--;
        } else {
            celda.setMarcada(true);
            celdasMarcadas++;
        }
    }

    private boolean esCoordenadaValida(int x, int y) {
        return x >= 0 && x < TAMANO_FILAS && y >= 0 && y < TAMANO_COLUMNAS;
    }

    private void validarCoordenadas(int x, int y) {
        if (!esCoordenadaValida(x, y)) {
            throw new IllegalArgumentException("Coordenadas inválidas: " + convertirCoordenadas(x, y));
        }
    }

    private String convertirCoordenadas(int x, int y) {
        return "" + (char)('A' + x) + (y + 1);
    }

    public boolean verificarVictoria() {
        return celdasDescubiertas == (TAMANO_FILAS * TAMANO_COLUMNAS - MINAS);
    }
    
    public Tablero(EstadoJuego estado) {    //constructor cargar partida
        this.celdas = estado.getCeldas();
        this.celdasDescubiertas = estado.getCeldasDescubiertas();
        this.celdasMarcadas = estado.getCeldasMarcadas();
        this.MINAS = estado.getMinasRestantes() + estado.getCeldasMarcadas();
    }

    // Getters
    public Celda[][] getCeldas() { return celdas; }
    public int getFilas() { return TAMANO_FILAS; }
    public int getColumnas() { return TAMANO_COLUMNAS; }
    public int getMinas() { return MINAS; }
    public int getCeldasMarcadas() { return celdasMarcadas; }
    public int getCeldasDescubiertas() { return celdasDescubiertas; }
}