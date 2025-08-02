package Modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class EstadoJuego implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Celda[][] celdas;
    private int celdasDescubiertas;
    private int celdasMarcadas;
    private LocalDateTime fechaGuardado;
    private int minasRestantes;
    
    // Constructor
    public EstadoJuego(Celda[][] celdas, int descubiertas, int marcadas, int minasRestantes) {
        this.celdas = celdas;
        this.celdasDescubiertas = descubiertas;
        this.celdasMarcadas = marcadas;
        this.minasRestantes = minasRestantes;
        this.fechaGuardado = LocalDateTime.now();
    }
    
    // Getters
    public Celda[][] getCeldas() { return celdas; }
    public int getCeldasDescubiertas() { return celdasDescubiertas; }
    public int getCeldasMarcadas() { return celdasMarcadas; }
    public LocalDateTime getFechaGuardado() { return fechaGuardado; }
    public int getMinasRestantes() { return minasRestantes; }
}