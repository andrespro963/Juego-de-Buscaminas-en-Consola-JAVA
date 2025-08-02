package Modelo;

import java.io.Serializable;

public class Celda implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean esMina;
    private boolean descubierta;
    private boolean marcada;
    private int minasAlrededor;

    public Celda() {
        this.esMina = false;
        this.descubierta = false;
        this.marcada = false;
        this.minasAlrededor = 0;
    }
    
    public char getRepresentacion() {
        if (!descubierta) {
            return marcada ? '?' : '-';
        }
        return esMina ? 'X' : (minasAlrededor > 0 ? (char)(minasAlrededor + '0') : 'V');
    }    

    // Getters y setters
    public boolean esMina() {
        return esMina;
    }

    public void setEsMina(boolean esMina) {
        this.esMina = esMina;
    }

    public boolean estaDescubierta() {
        return descubierta;
    }

    public void setDescubierta(boolean descubierta) {
        this.descubierta = descubierta;
    }

    public boolean estaMarcada() {
        return marcada;
    }

    public void setMarcada(boolean marcada) {
        this.marcada = marcada;
    }

    public int getMinasAlrededor() {
        return minasAlrededor;
    }

    public void setMinasAlrededor(int minasAlrededor) {
        this.minasAlrededor = minasAlrededor;
    }

    public void incrementarMinasAlrededor() {
        this.minasAlrededor++;
    }
}